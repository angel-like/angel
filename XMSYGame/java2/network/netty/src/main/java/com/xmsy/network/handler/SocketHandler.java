package com.xmsy.network.handler;

import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.xmsy.network.util.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * WebSocket 处理
 * 
 * @author Administrator
 */
@Slf4j
public class SocketHandler extends SimpleChannelInboundHandler<Object> {
	private WebSocketServerHandshaker handshaker;
	private final KeyPair keyPair = utils.getKeyPair();
	private final String pubKey = utils.getPublicKey(keyPair);
	protected ArrayList<String> TYPE = new ArrayList<>();
	protected static final String OPTKEY = "OPTION";
	protected static final String DATAKEY = "DATA";
	/**
	 * 存储socket 连接客户端IP
	 */
	protected static final AttributeKey<String> NETTY_CLIENT_IP = AttributeKey.valueOf("netty.channel.client.ip");
	protected static final AttributeKey<Long> NETTY_CLIENT_PORT = AttributeKey.valueOf("netty.channel.client.port");
	protected static final AttributeKey<String> NETTY_CLIENT_TYPE = AttributeKey.valueOf("netty.channel.client.type");
	protected static final AttributeKey<Long> GAME_ID = AttributeKey.valueOf("netty.channel.client.game");
	/**
	 * 存储socket 连接用户Id
	 */
	protected static final AttributeKey<String> NETTY_USER_TOKEN = AttributeKey
			.valueOf("netty.channel.client.usertoken");
	/**
	 * 存储socket 连接通信密钥
	 */
	protected static final AttributeKey<String> NETTY_SYMMETRIC_KEY = AttributeKey
			.valueOf("netty.channel.Symmetric.key");
	/**
	 * 获取通信密钥操作
	 */
	private static final String optGetSymmetricKey = "GetSymmetricKey";
	/**
	 * 随机数数据
	 */
	private final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*()+=-/,.;'[]\\<>:{}|";

	protected SocketHandler() {
		this.init();
	}

	protected void init() {
		/**
		 * Web 第三方获取登录标记
		 */
		TYPE.add("/webAgentToken");
	}

	protected String GetRandom(int length, String charArr) {
		StringBuffer random = new StringBuffer();
		/**
		 * 随机数函数
		 */
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			/**
			 * 生成charArr length以内的随机数，不含charArr length，并转换为对应字符
			 */
			random.append(charArr.charAt(r.nextInt(charArr.length())));
		}
		return random.toString();
	}

	/**
	 * channel 通道 action 活跃的 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {

	}

	/**
	 * channel 通道 Inactive 不活跃的
	 * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		log.info("[SocketHandler] channelInactive 客户端主动退出 ctx {}", ctx);
		doOpt("exit", "{}", ctx);
		ctx.close();
	}

	/**
	 * channel 通道 Read 读取 Complete 完成 在通道读取完成后会在这个方法里通知，刷新通道
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		/**
		 * 传统 HTTP接入，websocket连接时触发
		 */
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, ((FullHttpRequest) msg));
		}
		if (msg instanceof WebSocketFrame) {
			WebSocketFrame frame = (WebSocketFrame) msg;
			/**
			 * 判断是否关闭链路的指令
			 */
			if (frame instanceof CloseWebSocketFrame) {
				handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
				return;
			}
			/**
			 * 判断是否ping消息
			 */
			if (frame instanceof PingWebSocketFrame) {
				ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
				return;
			}
			/**
			 * 判断是否pong消息，心跳
			 */
			if (frame instanceof PongWebSocketFrame) {
				ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
				return;
			}
			/**
			 * 本例程仅支持文本消息，不支持二进制消息
			 */
			if (!(frame instanceof TextWebSocketFrame)) {
				System.out.println("本例程仅支持文本消息，不支持二进制消息");
				throw new UnsupportedOperationException(
						String.format("%s frame types not supported", frame.getClass().getName()));
			}
			handlerWebSocketFrame(ctx, frame);
		}
	}

	/**
	 * 握手，记录连接信息
	 * 
	 * @param ctx
	 *            socket连接信息
	 * @param req
	 *            连接消息
	 * @throws Exception
	 *             抛出异常
	 */
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		/**
		 * 如果HTTP解码失败，返回HHTP异常，并且关闭连接
		 */
		if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req,
					new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			ctx.close();
			return;
		}
		/**
		 * 获取url后置参数
		 */
		String uri = req.uri();
		/**
		 * 获取客户端IP
		 */
		String clientIP = req.headers().get("X-Forwarded-For");
		if (clientIP == null) {
			InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
			clientIP = insocket.getAddress().getHostAddress();
			log.info("[handleHttpRequest] insocket.getAddress() {}", clientIP);
		}
		if (null != clientIP) {
			clientIP = clientIP.split(":")[0];
		}
		/**
		 * 记录客户端IP
		 */
		ctx.channel().attr(NETTY_CLIENT_IP).set(clientIP);
		if (TYPE.contains(uri)) {
			ctx.channel().attr(NETTY_CLIENT_TYPE).set(uri);
		} else {
			ctx.close();
			return;
		}
		/**
		 * 构造握手响应返回
		 */
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://" + req.headers().get(HttpHeaderNames.HOST) + uri, null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
		/**
		 * 握手成功，发送公钥信息
		 */
		JSONObject responseObject = new JSONObject();
		responseObject.put("pubKey", pubKey);
		TextWebSocketFrame tws = new TextWebSocketFrame(responseObject.toString());
		ctx.channel().writeAndFlush(tws);
		System.out.println(responseObject);
	}

	private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		/**
		 * 获取客户端请求信息
		 */
		String request = ((TextWebSocketFrame) frame).text();
		/**
		 * 数据均为加密后结果 如果，ctx的会话密钥不存在，则数据为RSA加密 如果数据存在则数据为AES ECB PKCS5PADDING 128 加密
		 * 数据均为BASE64格式
		 */
		if (null == ctx.channel().attr(NETTY_SYMMETRIC_KEY).get()) {
			request = utils.privateDecrypt(request, keyPair.getPrivate());
		} else {
			request = utils.AESDecode(ctx.channel().attr(NETTY_SYMMETRIC_KEY).get(), request);
		}
		/**
		 * request 已解密，数据格式为 {"OPTION": opt, "data": "{JSOBObject}"}
		 */
		JSONObject jObject = JSONObject.parseObject(request);
		String opt = jObject.getString(OPTKEY);
		String data = jObject.getString(DATAKEY);
		JSONObject responseObject = new JSONObject();
		boolean isCloseCtx = true;
		String AESKEY = null;
		/**
		 * 协商密钥
		 */
		if (optGetSymmetricKey.equals(opt)) {
			/**
			 * 协商密钥，RSA私钥解密16位随机密钥
			 */
			JSONObject jRandom = JSONObject.parseObject(data);
			String random = jRandom.getString("Random");
			if (null != random && 16 == random.length()) {
				String symmetricKey = GetRandom(16, ALLCHAR);
				ctx.channel().attr(NETTY_SYMMETRIC_KEY).set(symmetricKey);
				responseObject.put("SymmetricKey", symmetricKey);
				AESKEY = random;
				isCloseCtx = false;
			}
		} else {
			/**
			 * 通信密钥就是AES密钥
			 */
			AESKEY = ctx.channel().attr(NETTY_SYMMETRIC_KEY).get();
			/**
			 * 执行方法，返回执行结果，空表示需要关闭连接
			 */
			Object obj = this.doOpt(opt, data, ctx);
			if (null != obj) {
				isCloseCtx = false;
			}
			responseObject.put(DATAKEY, obj);
		}
		/**
		 * 返回结果数据（AES加密 BASE64结果）
		 */
		String msg = responseObject.toString();
		log.debug("[SocketHandler]->handlerWebSocketFrame opt {} data {} msg {} ", opt, data, msg);
		String encodeMsg = utils.AESEncode(AESKEY, msg);
		TextWebSocketFrame tws = new TextWebSocketFrame(encodeMsg);
		log.debug("[SocketHandler]->handlerWebSocketFrame encodeMsg {} ", encodeMsg);
		ctx.channel().writeAndFlush(tws);
		if (isCloseCtx) {
			throw new Exception();
		}
	}

	/**
	 * 推送消息给客户端
	 * 
	 * @param ctx
	 * @param frame
	 * @throws Exception
	 */
	public static final void sentWebSocketFrame(ChannelHandlerContext ctx, Object obiect) throws Exception {

		JSONObject responseObject = new JSONObject();
		String AESKEY = ctx.channel().attr(NETTY_SYMMETRIC_KEY).get();
		responseObject.put(DATAKEY, obiect);
		/**
		 * 返回结果数据（AES加密 BASE64结果）
		 */
		String msg = responseObject.toString();
		String encodeMsg = utils.AESEncode(AESKEY, msg);
		TextWebSocketFrame tws = new TextWebSocketFrame(encodeMsg);
		log.debug("[SocketHandler]->handlerWebSocketFrame encodeMsg {} ", encodeMsg);
		ctx.channel().writeAndFlush(tws);
	}

	protected Object doOpt(String option, String jsonStr, ChannelHandlerContext ctx) {
		return null;
	}

	/**
	 * 返回HTTP应答消息
	 */
	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
		// 返回应答给客户端
		if (res.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * exception 异常 Caught 抓住 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.info("[SocketHandler] exceptionCaught 客户异常退出 ctx {} cause {}", ctx, cause);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		messageReceived(ctx, msg);
	}
}
