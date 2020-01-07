package com.xmsy.server.zxyy.game.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xmsy.server.zxyy.game.cache.LocalContentCache;
import com.xmsy.server.zxyy.game.common.exception.RRException;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.param.GameParam;
import com.xmsy.server.zxyy.game.modules.manager.game.entity.result.GameResult;
import com.xmsy.server.zxyy.game.modules.manager.game.service.GameInfoService;
import com.xmsy.server.zxyy.game.modules.manager.hall.service.HallService;
import com.xmsy.server.zxyy.game.modules.web.hall.result.HallResult;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class ReloadInit {
	@Autowired
    private LocalContentCache localContentCache;
    @Autowired
    private GameInfoService infoService;
    @Autowired
    private HallService hallService;
	

    public void reloadInit() throws Exception {
        // 获取游戏列表
        List<GameResult> gameInfos = infoService.findGameInfos(new GameParam());
        if (null == gameInfos || gameInfos.size() == 0) {
            log.error("gameInfos 游戏列表返回结果 {}", gameInfos);
            throw new RRException("游戏列表获取失败！");
        }
        //读json（游戏默认房间场次信息）文件
        try {
        	InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("game-info-default.json");
            if(inputStream!=null) {
            	String s="";
            	try {
                    // 获取文件输入流
                    s = this.readFile( inputStream );
                } catch (Exception e) {
                    log.info("error {}",e.getMessage());
                }
            	if(s!=null) {
            		JSONObject jobj = JSON.parseObject(s);
                    JSONArray room1 = jobj.getJSONArray("1") ;
                    for (int i = 0 ; i < room1.size();i++){
                        JSONObject key1 = room1.getJSONObject(i);
                        Long gradeId = key1.getLong("gradeId");
                        localContentCache.putRoomGradeInfo(1l, gradeId, key1);
                    }
                    JSONArray room2 = jobj.getJSONArray("2") ;
                    for (int i = 0 ; i < room2.size();i++){
                        JSONObject key1 = room2.getJSONObject(i);
                        Long gradeId = key1.getLong("gradeId");
                        localContentCache.putRoomGradeInfo(2l, gradeId, key1);
                    }
                    JSONArray room3 = jobj.getJSONArray("3") ;
                    for (int i = 0 ; i < room3.size();i++){
                        JSONObject key1 = room3.getJSONObject(i);
                        Long gradeId = key1.getLong("gradeId");
                        localContentCache.putRoomGradeInfo(3l, gradeId, key1);
                    }
                    JSONArray room4 = jobj.getJSONArray("4") ;
                    for (int i = 0 ; i < room4.size();i++){
                        JSONObject key1 = room4.getJSONObject(i);
                        Long gradeId = key1.getLong("gradeId");
                        localContentCache.putRoomGradeInfo(4l, gradeId, key1);
                    }
            	}
                 
                 
            }
		} catch (Exception e) {
			 log.info("error {}",e.getMessage());
		}
        
        //读json（游戏默认游戏场次信息）文件
        try {
        	InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("game-default.json");
            if(inputStream!=null) {
            	String s="";
            	try {
                    // 获取文件输入流
            		 s = this.readFile( inputStream );
                } catch (Exception e) {
                    log.info("error {}",e.getMessage());
                }
            	if(s!=null) {
            		JSONObject jobj = JSON.parseObject(s);
                    JSONArray game8 = jobj.getJSONArray("8") ;
                    for (int i = 0 ; i < game8.size();i++){
                        JSONObject key = game8.getJSONObject(i);
                        Long gradeId = key.getLong("gradeId");
                        localContentCache.putGameGradeInfo(8l, gradeId, key);
                    }
                    JSONArray game23 = jobj.getJSONArray("23") ;
                    for (int i = 0 ; i < game23.size();i++){
                        JSONObject key = game23.getJSONObject(i);
                        Long gradeId = key.getLong("gradeId");
                        localContentCache.putGameGradeInfo(23l, gradeId, key);
                    }
                   
            	}
                 
                 
            }
		} catch (Exception e) {
			 log.info("error {}",e.getMessage());
		}
        
       
        Map<Long, String> gameRoomMap = new HashMap<>();
        Map<Long, String> gameMap = new HashMap<>();
        Map<Long, String> gradeMap = new HashMap<>();
        Map<Long, String> roomMap = new HashMap<>();
        for (GameResult gameResult : gameInfos) {
            gameMap.put(gameResult.getGameId(), gameResult.getGameName());
            gradeMap.put(gameResult.getGradeId(), gameResult.getGradeName());
            roomMap.put(gameResult.getRoomId(), gameResult.getRoomName());
            gameRoomMap.put(gameResult.getGameId(), gameResult.getRoomId().toString());
        }
        localContentCache.putGameMap(gameMap);
        localContentCache.putGradeMap(gradeMap);
        localContentCache.putRoomMap(roomMap);
        localContentCache.putGameRoomMap(gameRoomMap);

        // 获取大厅列表
        List<Long> hallIdList = new ArrayList<>();
        Collection<HallResult> hallArry = hallService.selectAll().values();
        if (CollectionUtils.isEmpty(hallArry)) {
            log.error("游戏大厅列表获取失败 hallArry {}", hallArry);
            throw new RRException("游戏大厅列表获取失败！");
        }
        Map<Long, String> hallMap = new HashMap<Long, String>();
        for (HallResult hallResult : hallArry) {
            JSONObject json = (JSONObject) JSON.toJSON(hallResult);
            hallIdList.add(hallResult.getId());
            hallMap.put(hallResult.getId(), json.toJSONString());
        }
        localContentCache.putHallMap(hallMap);
        // 获取大厅ip列表
//        Map<Long, String> hallAddressMap = new HashMap<Long, String>();
//        for (Long hallid : hallIdList) {
//            try {
//                String url = HallUrlConstant.getHALL_URL() + hallid;
//                String result = HttpRequest.get(url).timeout(5000).execute().body();
//                JSONObject robj = JSON.parseObject(result);
//                if (robj == null || robj.getInteger("Result") != 0 || StringUtils.isEmpty(robj.getString("HallIp"))) {
//                    log.error("获取大厅ip失败 hallid:{} result :{}", hallid, result);
//                    continue;
//                }
//                hallAddressMap.put(hallid, robj.getString("HallIp"));
//            } catch (Exception e) {
//                log.error("获取大厅ip失败 hallid:{} error {}", hallid, e.getMessage());
//            }
//        }
//        localContentCache.putHallAddressMap(hallAddressMap);
        infoService.deployRobotConfig();

    }
    
    
    /*
	 * 读取配置文件
	*/	
	private String readFile ( InputStream inputStream ) throws IOException {
		
		StringBuilder builder = new StringBuilder();
		try {
			InputStreamReader reader = new InputStreamReader(inputStream , "UTF-8" );
			BufferedReader bfReader = new BufferedReader( reader );
			String tmpContent = null;
			while ( ( tmpContent = bfReader.readLine() ) != null ) {
				builder.append( tmpContent );
			}
			bfReader.close();
		} catch ( UnsupportedEncodingException e ) {
			// 忽略
		}
		return this.filter( builder.toString() );
	}
	// 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
	private String filter ( String input ) {
		return input.replaceAll( "/\\*[\\s\\S]*?\\*/", "" );
	}

}
