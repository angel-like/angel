package com.itheima.springboot.controller;

import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StreamController {

	/**
	 * 流式输出
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stream")//路径直接http://localhost:8080/stream   不要写项目名
    @ResponseBody
    public String stream(HttpServletRequest req, HttpServletResponse response) throws Exception {
		PrintWriter pw=null;
		try {
			response.setContentType("text/event-stream"); //必须
			response.setCharacterEncoding("UTF-8"); //非必须
			response.setHeader("Cache-Control","no-cache"); //必须
			response.setHeader("Connection","keep-alive"); //必须
			pw=response.getWriter();
			for(int i =0;i<10;i++){
				pw.write("data:"+i+"\n\n");
				pw.flush();
				Thread.sleep(1000);
			}
		}catch (Exception e){
			throw new RuntimeException(e);
		}finally {
			if (pw!=null){
				pw.close();
			}
		}
		//用了流式输出，不能有返回值，不然报以下错误
		// java.lang.Illeg alStateException: getWriter() has already been called for this response
        return null;
    }
}
