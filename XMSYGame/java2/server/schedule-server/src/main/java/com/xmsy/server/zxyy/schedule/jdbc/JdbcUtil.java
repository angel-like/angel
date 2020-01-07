package com.xmsy.server.zxyy.schedule.jdbc;

import org.springframework.stereotype.Component;


/**
 * JdbcUtil
 * 
 * @author Administrator
 *
 */
//@Slf4j
@Component
public class JdbcUtil {
	
//	private  Connection getConnection (){
//		Connection conn = null;
//	    try {
//	    	Class.forName(SysConstant.getDATASOURCE_DRIVER());
//            conn = DriverManager.getConnection(SysConstant.getDATASOURCE_URL(), 
//           		 SysConstant.getDATASOURCE_USER(), SysConstant.getDATASOURCE_PASSWORD()); 
//	    } catch (ClassNotFoundException e) {
//	        e.printStackTrace();
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return conn;
//	}
//	
//    
//    public JSONArray selectByParamReturnJsonArray(String sql,Object... params) throws Exception {
//    	JSONArray dataArray =null;
//    	ResultSet rstSet = null;
//        Connection conn = getConnection();
//        PreparedStatement pstmt = null;
//        try {
//            if (null == conn)
//                throw new Exception("Database not connected!");
//            pstmt = conn.prepareStatement(sql);
//	        if(null != params){  
//	        	for (int i = 0; i < params.length; i++) {  
//	        		pstmt.setObject(i + 1, params[i]);  
//	        	}
//        	}
//            rstSet = pstmt.executeQuery();
//            dataArray = ResultSetTool.resultSetToJsonArry(rstSet);
//            if(SysConstant.getJDBC_SHOWSQL()) {
//            	log.info("select sql:{}",pstmt.toString());
//            	log.info("data array:{}",dataArray);
//            	log.info("data total:{}",dataArray!=null && !dataArray.isEmpty()?dataArray.size():0);
//            }
//        } catch (SQLException e) {
//        	e.printStackTrace();
//        }finally{
//        	colseResultSet(rstSet);
//       	 	colsePstm(pstmt);
//       	 	colseConn(conn);
//        }
//    	return dataArray;
//    }
//    
//    public JSONObject selectByParamReturnJsonObject(String sql,Object... params) throws Exception {
//    	JSONObject data =null;
//    	ResultSet rstSet = null;
//        Connection conn = getConnection();
//        PreparedStatement pstmt = null;
//        try {
//            if (null == conn)
//                throw new Exception("Database not connected!");
//            pstmt = conn.prepareStatement(sql);
//	        if(null != params){  
//	        	for (int i = 0; i < params.length; i++) {  
//	        		pstmt.setObject(i + 1, params[i]);  
//	        	}
//        	}
//            rstSet = pstmt.executeQuery();
//            data = ResultSetTool.resultSetToJsonObject(rstSet);
//            if(SysConstant.getJDBC_SHOWSQL()) {
//            	log.info("select sql:{}",pstmt.toString());
//            	log.info("data Object:{}",data);
//            	log.info("data total:{}",data!=null && !data.isEmpty()?1:0);
//            }
//        } catch (SQLException e) {
//        	e.printStackTrace();
//        }finally{
//        	colseResultSet(rstSet);
//       	 	colsePstm(pstmt);
//       	 	colseConn(conn);
//        }
//    	return data;
//    }
//    
//	
//	public  int insert(String insertSql, Object... params) {
//		int insertNum=0;
//	    Connection conn = getConnection();
//	    PreparedStatement pstmt = null;
//	    try {
//	        pstmt = conn.prepareStatement(insertSql);
//	        if(null != params){  
//	        	for (int i = 0; i < params.length; i++) {  
//	        		pstmt.setObject(i + 1, params[i]);  
//	        	}
//        	}
//	        insertNum = pstmt.executeUpdate();
//	        if(SysConstant.getJDBC_SHOWSQL()) {
//            	log.info("insert sql:{}",pstmt.toString());
//            	log.info("insert num:{}",insertNum);
//            }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }finally{
//       	 	colsePstm(pstmt);
//       	 	colseConn(conn);
//        }
//	    return insertNum;
//	    
//	}
//	
//	public  void transactionExecuteUpdate(String[] sqlArray, Object[]... params) {
//	    Connection conn = getConnection();
// 	    PreparedStatement pstmt = null;
//	    try {
//	    	conn.setAutoCommit(false);
//	    	int index=0;
//	    	for(String sql:sqlArray) {
//	    		 pstmt = conn.prepareStatement(sql);
//		        if(null != params){  
//		        	Object[] param = params[index];
//		        	if(null != param){
//		        		for (int i = 0; i < param.length; i++) {  
//			        		pstmt.setObject(i + 1, param[i]);
//			        	}
//		        	}
//	        	}
//		        pstmt.executeUpdate();
//		        index++;
//	    	}
//	    	conn.commit();
//	    } catch (SQLException e) {
//	    	try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}//回滚事务
//	        e.printStackTrace();
//	    }finally{
//       	 	colsePstm(pstmt);
//       	 	colseConn(conn);
//        }
//	    
//	}
//	
//	public  int update(String updateSql, Object... params) {
//		int changeNum=0;
//	    Connection conn = getConnection();
//	    PreparedStatement pstmt = null;
//	    try {
//	        pstmt = conn.prepareStatement(updateSql);
//	        if(null != params){  
//	        	for (int i = 0; i < params.length; i++) {  
//	        		pstmt.setObject(i + 1, params[i]);  
//	        	}
//        	}
//	        changeNum = pstmt.executeUpdate();
//	        if(SysConstant.getJDBC_SHOWSQL()) {
//            	log.info("update sql:{}",pstmt.toString());
//            	log.info("update num:{}",changeNum);
//            }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }finally{
//       	 	colsePstm(pstmt);
//       	 	colseConn(conn);
//        }
//	    return changeNum;
//	    
//	}
//	/**
//	 * 批量新增
//	 * 
//	 * @return
//	 */
//	public void insertBatch(String sql,List<Object[]> paramsList){
//		  Connection conn = getConnection();
//          PreparedStatement pstm =null;
//          try {
//             pstm = conn.prepareStatement(sql);
//             conn.setAutoCommit(false);
//             for (Object[] params:paramsList) {
//            	 for (int i = 0; i < params.length; i++) {
//            		 pstm.setObject(i + 1, params[i]);  
//                  }
//        	 	pstm.addBatch();
//             }
//             pstm.executeBatch();
//             conn.commit();
//          } catch (BatchUpdateException e) {
//             System.out.println("数据重复，重新执行");
//             e.printStackTrace();
//         }catch (Exception e) {
//             e.printStackTrace();
//             throw new RuntimeException(e);
//         }finally{
//        	 colsePstm(pstm);
//        	 colseConn(conn);
//         }
//	}
//	/**
//	 * 批量更新
//	 * 
//	 * @return
//	 */
//	public void updateBatch(String sql,List<Object[]> paramsList){
//		Connection conn = getConnection();
//		PreparedStatement pstm =null;
//		try {
//			pstm = conn.prepareStatement(sql);
//			conn.setAutoCommit(false);
//			for (Object[] params:paramsList) {
//				for (int i = 0; i < params.length; i++) {
//					pstm.setObject(i + 1, params[i]);  
//				}
//				pstm.addBatch();
//			}
//			pstm.executeBatch();
//			conn.commit();
//		} catch (BatchUpdateException e) {
//			System.out.println("数据重复，重新执行");
//		}catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			colsePstm(pstm);
//			colseConn(conn);
//		}
//	}
//	
//	private void colseConn(Connection conn) {
//		if(conn!=null){
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//	}
//	private void colseResultSet(ResultSet rstSet) {
//		if(rstSet!=null){
//            try {
//            	rstSet.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//	}
//	private void colsePstm(PreparedStatement pstm) {
//		if(pstm!=null){
//            try {
//                pstm.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//	}
//	
//	
	
}