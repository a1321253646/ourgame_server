package com.lemeng.ourgame;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import com.lemeng.ourgame.configuration.HelloWorldConfiguration;

public class SqlHelper {
	
	//static final String TABLE_DATE= "_date";
	static  String TABLE_NET= "_net";
	//static  int CURRENT_VERSION = 5600;
	//static  long CURRENT_APK_VERSION = 57;
	//static  int CURRENT_APK_UPDATE_MUST = 1;
	//static  long CURRENT_RANG_TIME = 1559375972715L;
	String STR_RAMDOM="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	//static  String CURRENT_APK_UPDATE_STR = "亲爱的勇士您好，最新版本已经发布，为了您的游戏体验，请尽快前往TAPTAP进行更新。";
    static String CREATE_SQL = "("+
           "TYPE          INT ," +
           "ID            INT ," +
           "EXTAN         TEXT ,"+
           "GOODID        INT ," +
           "GOODTYPE      INT ," +
           "ISCLENAN      INT "+
           ")charset=utf8;";

	Connection conn = null;
	Statement stmt = null;
	private SqlHelper() {
	    try {
			conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/ourgame?serverTimezone=GMT%2B8&characterEncoding=utf-8","root","zsbin149");
			stmt = conn.createStatement();
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库连接失败");
			e.printStackTrace();
		}	
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	
	 private String getRandomString(int length){
	     
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(62);
	       sb.append(STR_RAMDOM.charAt(number));
	     }
	     return sb.toString();
	 }
	 
	private String getToken(String mac,String channel) {
		
		while(true) {
			String token = getRandomString(5);
			if(token.equals("clear")) {
				continue;
			}
			String commPath = "select * from user_mac WHERE  user='" +token+"'";
			ResultSet  rs = null;
		    try {
		    	int count = 0;
		    	rs = stmt.executeQuery(commPath);
	            while(rs.next()){
	            	count++;                                     
	            }     
	            rs.close();
	            rs = null;
	            if(count > 0) {
	            	continue;
	            }
	            count = 0;
	            long time = System.currentTimeMillis();
	            long register = System.currentTimeMillis();
	            commPath = "select * from user_tb2 WHERE  user='" +mac+"'";
	            rs = stmt.executeQuery(commPath);
	            while(rs.next()){
	            	register = rs.getLong("register");  
	            	count ++;
	            }   
	            rs.close();
	            rs = null;
	            if(count > 0) {
	            	 commPath = "update user_tb2 set user='"+mac+"_fault' WHERE  user='" +mac+"'";
	            	 stmt.execute(commPath);
	            }
	            commPath = "INSERT INTO user_mac (user,mac_id) VALUES (\""+token+"\",\""+mac+"\");";
	            stmt.execute(commPath);
            	commPath = "INSERT INTO user_tb3 (user,name,level,time,register,luihui,lasttime,version,channel) VALUES (\""+token+"\",\"***\",-100,-1,"+ register+",0,"+time+",-1,'"+channel+"');";;
            	stmt.execute(commPath);
            	return token;
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	        	if(rs != null) {
	        		try {
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
	        	System.out.println("执行："+commPath +"失败");
	            e.printStackTrace();
	            return null;
	        }
		}
	}
	
	public String getMacByToken(String token) {
		String commPath = "select * from user_mac WHERE  user='" +token+"'";
		String mac = null;
		ResultSet  rs = null;
	    try {
	    	rs = stmt.executeQuery(commPath);
            while(rs.next()){
            	mac = rs.getString("mac_id");                                      
            }     
            rs.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
        	if(rs != null) {
        		try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	System.out.println("执行 getMacByToken："+commPath +"失败");
            e.printStackTrace();
            return "error_query";
        } 
	    return mac;
	}
	
	public void findUserToken(String user,SqlManager.UserToken tokenBean,String channel) {
		
		String commPath = "select * from user_mac WHERE  mac_id='" +user+"'";
		ResultSet  rs = null;
	    try {
	    	rs = stmt.executeQuery(commPath);
            while(rs.next()){
            	tokenBean.token = rs.getString("user");
            	tokenBean.isNew = false;
            }     
            rs.close();
            rs = null;
            if(tokenBean.token != null && tokenBean.token.length() > 0) {
            	return;
            }else {
            	tokenBean.token =  getToken(user,channel);
            	tokenBean.isNew = true;
            	return;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
        	if(rs != null) {
        		try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	System.out.println("执行："+commPath +"失败");
            e.printStackTrace();
        } 
	}
	
	public void saveMaxLeve(String user,int level) {
	//	MyDebug.log("saveMaxLeve ：user="+user+" level="+level);
		String commPath = "select * from user_tb3 WHERE  user='" +user+"'";
		try {
		//	if(level > currentLevel) {
				long time = System.currentTimeMillis();
	            commPath = "UPDATE user_tb3 SET level=" +level+",time="+time+" WHERE user='"+user+"'";
	            stmt.execute(commPath);
		//	}
		} catch (SQLException e) {
			MyDebug.log("执行："+commPath+"出错");
			e.printStackTrace();
		}	
	}
	
	public void updateVersion(String user,int version) {
	//	MyDebug.log("updateVersion ：user="+user+" version="+version);
		String commPath = null;
		try {
			commPath = "update user_tb3 set version="+version+" WHERE user='"+user+"'";
			stmt.execute(commPath);
			commPath = "update user_tb3 set level=-100 WHERE user='"+user+"'";
			stmt.execute(commPath);
		} catch (SQLException e) {
			
			MyDebug.log("执行："+commPath+"出错");
			e.printStackTrace();
		}	
		
	}
	
	public void addLuihui(String user) {
		String commPath = "update user_tb3 set luihui=luihui+1 WHERE user='"+user+"'";
		try {
			stmt.execute(commPath);
		} catch (SQLException e) {
			
			MyDebug.log("执行："+commPath+"出错");
			e.printStackTrace();
		}	
	}
	public void updateLastTime(String user,String channel) {
		
		String commPath = "update user_tb3 set lasttime="+System.currentTimeMillis()+",channel='"+channel+"' WHERE user='"+user+"'";
		try {
			stmt.execute(commPath);
		} catch (SQLException e) {
			
			MyDebug.log("执行："+commPath+"出错");
			e.printStackTrace();
		}	
	}
	
	
	public void saveUserName(String user,String  name) {
		
		String commPath = "select * from user_tb3 WHERE  user='" +user+"'";
		try {
			commPath = "UPDATE user_tb3 SET name='" +name+"' WHERE user='"+user+"'";
		//	MyDebug.log("修改名字:执行命令commPath ="+commPath);
            stmt.execute(commPath);
		} catch (SQLException e) {
			
			MyDebug.log("执行："+commPath+"出错"  );
			e.printStackTrace();
		}

		
	}
	
    private String getInsertComm(String user,SqlDateBean data)
    {
    	String commandString = "INSERT INTO user_detail3 VALUES (";
        commandString +=  data.type;
        commandString += "," + data.id;
        commandString += "," + "\"" + data.extra + "\"";
        commandString += "," + data.goodId;
        commandString += "," + data.goodtype;
        commandString += "," + data.isclean;
        commandString += "," + "\"" + user + "\""+")";
        return commandString;
    }
    
    
    public boolean testTransaction(String user,ArrayList<SqlDateBean> list) {
    //	MyDebug.log(">>>>>>>>>>>>>>>>>>>>>>>>>事务执行:开始执行");
    	if(list == null || list.size() == 0) {
    		return false;    		
    	}
    	ArrayList<String> strList = new ArrayList<String>();
    	for(int i = 0 ;i< list.size(); i++) {
    		SqlDateBean bean = list.get(i);
    		String str = null;
			if(bean.action == 1) {
				str = SqlHelper.getIntance().updateDateInfo(user, bean);
			}else if(bean.action == 2) {
				str = SqlHelper.getIntance().deleteGood(user, bean);
			}else if(bean.action == 3) {
				str = SqlHelper.getIntance().deleteLuihui(user);
			}else if(bean.action == 4) {
				strList.add(SqlHelper.getIntance().clearTableAllDetail(user));
				strList.add(SqlHelper.getIntance().clearTableAllMac(user));
				strList.add(SqlHelper.getIntance().clearTableAllTbale(user));
				continue;
			}
			if(str == null || str.length() == 0) {
				return false;
			}
			strList.add(str);
    	}
    	Statement statement = null;
         try
         {
        	 
             conn.setAutoCommit(false); //开启事务，禁止自动提交

             statement = conn.createStatement();
             for(String str : strList) {           	 
            	 statement.addBatch(str);
             }
            
             statement.executeBatch();
             
             conn.commit(); //执行成功，提交事务
             statement.close();
             statement = null;
      //       MyDebug.log("事务执行:执行成功<<<<<<<<<<<<<<<<<<<<<<<");
         }
         catch (Exception e)
         {
        	 MyDebug.log("事务执行:执行失败<<<<<<<<<<<<<<<<<<<<<<<");
             try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			//	
				e1.printStackTrace();
			} //发生异常，事务回滚
            if(statement != null) {
            	try {
					statement.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
             
             return false;
         }
    	
    	
    	return true;
    }
    
    public String getLocalByUser(HelloWorldConfiguration configuration,String user,String token,String oldToken, boolean isNew,boolean isChange) {
    	ResultSet  rs = null;
    	String sql = null;
	    try {
	    	
	    	ArrayList<SqlDateBean> list = new ArrayList<SqlDateBean>();
	    	if(!isNew) {	
	    		sql =  "select * from user_detail3 where USER=\"" + token + "\"";
	    	}else {
	    		sql =  "select * from user_detail where USER=\"" + user + "\"";
	    		
	    	}
	    	//MyDebug.log("执行:"+ sql);
	    	rs = stmt.executeQuery(sql);
	    	long id = -1;
        	long type = -1;
            while(rs.next()){
            	SqlDateBean bean = new SqlDateBean();
            	id = rs.getLong("ID");
            	type = rs.getLong("TYPE");
            	if(id==SqlDateBean.GAME_ID_MAX_TIME   && type ==SqlDateBean.TYPE_GAME ) {
            		continue;
            	}
               // SqlDateBean date = new SqlDateBean();
            	bean.type= type;
            	bean.id = id;
            	bean.extra = rs.getString("EXTAN");
            	bean.goodId =rs.getLong("GOODID");
            	bean.goodtype =rs.getLong("GOODTYPE");
            	bean.isclean =rs.getLong("ISCLENAN");
            	
               // date.type =  rs.getLong("TYPE");//reader.GetInt64(reader.GetOrdinal("TYPE"));
               // date.id = rs.getLong("ID");//reader.GetInt64(reader.GetOrdinal("ID"));
               // date.extan =rs.getString("EXTAN");// reader.GetString(reader.GetOrdinal("EXTAN"));
               // date.goodId = rs.getLong("GOODID");//reader.GetInt64(reader.GetOrdinal("GOODID"));
               // date.goodType = rs.getLong("GOODTYPE");//eader.GetInt64(reader.GetOrdinal("GOODTYPE"));
               // date.isClean = rs.getLong("ISCLENAN");//reader.GetInt64(reader.GetOrdinal("ISCLENAN"));

            	list.add(bean);                                       
            } 
            rs.close();
            rs = null;
            if(isNew) {
            	sql =  "update user_detail set user=\""+user+"_fault\" where USER=\"" + user + "\"";
            	stmt.execute(sql);
            }
            if(isChange) {
            	
            	if(list.size() <1) {
            		
            		ResponseDateStringBean back = ResponseDateStringBean.getErrorDate("无效token");
            		back.setStatus(-1002L);
            		return back.toString();
           
            	}
            	
            	sql =  "update user_mac set mac_id=\"empty\" where mac_id=\"" + user + "\"";
            	stmt.execute(sql);
            	sql =  "update user_mac set mac_id=\""+user+"\" where user=\"" + token + "\"";
            	stmt.execute(sql);
            }
            ResponseDateSqlBeanBean back = new ResponseDateSqlBeanBean();
            back.setStatus(0L);
            back.setToken(token);
            back.setTime(System.currentTimeMillis());
            back.setVersion(configuration.getUpdateVersion());
            back.setIsNew(isNew);
            if(list.size()> 0) {
            	back.setDate(list);
            }

        	return back.toString();
            
        } catch (SQLException e) {
        	System.out.println("执行："+sql +"失败");
            // TODO Auto-generated catch block
        	if(rs != null) {
        		try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
            e.printStackTrace();
        } 
    	return "error";
    }
    
    public String getRankingListLevel(HelloWorldConfiguration configuration,String user) {
    	ResultSet  rs  = null;
    	String sql =  "select * from user_tb3  where user='"+user+"'";
	    try {
	    	ArrayList<RanKingListBean> list = new ArrayList<RanKingListBean>();
	    	
	    	rs= stmt.executeQuery(sql);
	    	long register = -1;
	    	String channel = "";
	    	while(rs.next()){
	    		register = rs.getLong("register");
	    		channel = rs.getString("channel");
	    	}
	    	MyDebug.log("getRankingListLevel register"+register );
	    	boolean isUpdate = true;
	    	if(register == -1 || register > configuration.getRangeTime()) {
	    		isUpdate = true;
	    	}else {
	    		isUpdate = false;
	    	}
	    	if(channel != null && channel.length() > 0 && channel.equals("taptap")) {
	    		sql =  "select * from user_tb3  where version>="+ configuration.getDateVersion()+" and register"+ (isUpdate?">=":"<=")+ configuration.getRangeTime() +" and channel='taptap'"+" order by level desc,time asc  limit 0,1000";
	    	}else {
	    		sql =  "select * from user_tb3  where version>=4900"+" and register"+ (isUpdate?">=":"<=")+configuration.getRangeTime() +" order by level desc,time asc  limit 0,1000";
	    	}
	    	
	    	MyDebug.log("getRankingListLevel register"+sql );
	    	rs= stmt.executeQuery(sql);
	    	int count = 0;
	    	boolean findUser = false;
	    	RanKingListBean userJs = null;
            while(rs.next()){
            	RanKingListBean jb = new RanKingListBean();
               // SqlDateBean date = new SqlDateBean();
            	String user2 = rs.getString("user");      
            	int level = rs.getInt("level");
            	if(level == -100) {
            		level = -10;
            	}
            	count++;
            	
            	if(user.equals(user2)) {
            		findUser = true;
            		userJs =  new RanKingListBean();
            		userJs.setUser(user2);
            		userJs.setName(rs.getString("name"));
            		userJs.setLevel(level);
            		userJs.setTime(rs.getLong("time"));
            		userJs.setIndex(count);
            	}
            	if(count <= 100) {
            		
            		
            		jb.setUser(user2);
            		jb.setName(rs.getString("name"));
            		jb.setLevel(level);
            		jb.setTime(rs.getLong("time"));
            		jb.setIndex(count);
            		list.add(jb);             		
            	}
            	
            	if(count > 100 &&  findUser) {
            		break;
            	}       	
            }      
            rs.close();
            rs = null;
            if(userJs != null) {
            	list.add(userJs);        	
            }else {
            	sql =  "select * from user_tb3 where USER=\""+user + "\"";
            	rs = stmt.executeQuery(sql);
            	while(rs.next()){
                	int level = rs.getInt("level");
                	if(level == -100) {
                		level = -10;
                	}
                	
                	userJs =  new RanKingListBean();
            		userJs.setUser(user);
            		userJs.setName(rs.getString("name"));
            		userJs.setLevel(level);
            		userJs.setTime(rs.getLong("time"));
            		userJs.setIndex(-1);
            		break;
            	}
            	rs.close();
            	rs = null;
            	if(userJs != null) {
            		list.add(userJs); 
            	}
            	        	
            }
            ResponseDateRangeBean js = new ResponseDateRangeBean();
            js.setStatus(0);
            js.setToken(user);
            js.setTime( System.currentTimeMillis());
            js.setVersion(configuration.getUpdateVersion());
            if(list.size() > 0) {
            	
            	js.setDate(list);
            }
        	return js.toString();
            
        } catch (SQLException e) {
        	System.out.println("执行："+sql +"失败");
        	if(rs != null) {
        		try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    	return "error";
    }
    
    
	public String updateDateInfo(String user,SqlDateBean data) {
		String commPath  = null;
        if (data.type != SqlDateBean.TYPE_GOOD)
        {

            commPath = "select * from  user_detail3 WHERE  ID=" + data.id + " AND TYPE="+ data.type+" AND USER=\"" + user + "\""; //SELECT* FROM Persons WHERE firstname = 'Thomas' OR lastname = 'Carter'
            
            ResultSet rs = null;
			try {
				rs = stmt.executeQuery(commPath);
	            ResultSetMetaData rsmd;
	            int count =0;
	            while(rs.next()) { 
	            	count++; 
	            }
	            rs.close();
	            rs = null;
	       //     System.out.println("查询："+commPath +" 数目"+count );
	            if (count < 1)
	            {
	                commPath = getInsertComm(user,data);
	            }
	            else
	            {
	                commPath = "UPDATE user_detail3 SET EXTAN=\"" + data.extra+"\"";
	                commPath += " WHERE TYPE=" + data.type + " AND ID=" + data.id+" AND USER=\"" + user + "\"";
	            }
	           // boolean success =  stmt.execute(commPath);
	         //   System.out.println("插入："+commPath  );	            
	            //return success;
			} catch (SQLException e) {
	        	if(rs != null) {
	        		try {
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
				MyDebug.log("执行"+commPath +" 失败" );
				e.printStackTrace();
				
				return null;
			}

        }
        else {

        	commPath = "select * from  user_detail3 WHERE  GOODID=" + data.goodId+" AND USER=\"" + user + "\"";
            ResultSet rs = null;
			try {
				rs = stmt.executeQuery(commPath);
	            ResultSetMetaData rsmd;
	            int count =0;
	            while(rs.next()) { 
	            	count++; 
	            }
	            rs.close();
	            rs = null;
	    //        System.out.println("查询："+commPath +" 数目"+count );
	            if (count < 1)
	            {
	                commPath = getInsertComm(user,data);
	            }
	            else
	            {
	                commPath = "UPDATE user_detail3 SET EXTAN=\"" + data.extra + "\""+", GOODTYPE="+data.goodtype;
	                commPath += " WHERE GOODID=" + data.goodId+" AND USER=\"" + user + "\"";
	            }
	          //  return stmt.execute(commPath);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
	        	if(rs != null) {
	        		try {
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        	}
				MyDebug.log("执行"+commPath +" 失败" );
				e.printStackTrace();
				return null;
			}
        }
        return commPath;
	}

	
	public String clearTableAllDetail(String user) {
		String commPath =   "DELETE FROM user_detail3 WHERE USER=\"" + user + "\""; 
	//	MyDebug.log("事务执行:"+commPath);
    	return commPath;
		
	}
	public String clearTableAllMac(String user) {
		String commPath =   "DELETE FROM user_mac WHERE USER=\"" + user + "\""; 
//		MyDebug.log("事务执行:"+commPath);
    	return commPath;
		
	}
	public String clearTableAllTbale(String user) {
		String commPath =   "DELETE FROM user_tb3 WHERE USER=\"" + user + "\""; 
	//	MyDebug.log("事务执行:"+commPath);
    	return commPath;
		
	}
	public String deleteLuihui(String user) {
		addLuihui(user);
		String commPath =  "DELETE FROM user_detail3 WHERE ISCLENAN =" + 1+" AND USER=\"" + user + "\"";
//		MyDebug.log("事务执行:"+commPath);
    	return commPath;

		
	}
	
    public String  deleteGood(String user,SqlDateBean data)
    {
    	 String commPath = "";
    	 if (data.type != SqlDateBean.TYPE_GOOD && data.type != -1) {
    		 commPath =  "DELETE FROM user_detail3 WHERE TYPE="+data.type +" AND ID="+data.id +" AND USER=\"" + user + "\"";
    		 
    	 }else {
    		 commPath =  "DELETE FROM user_detail3 WHERE GOODID=" + data.goodId+" AND USER=\"" + user + "\"";    		 
    	 }
		
		//MyDebug.log("事务执行:"+commPath);
    	return commPath;
    }
	
	private void creatTabel(String table) {
		try {
		//	stmt = conn.createStatement();
			if(0 == stmt.executeLargeUpdate("CREATE TABLE "+table+CREATE_SQL)){
				System.out.println("成功创建表！");
			}
			else
			{
				System.out.println("创建表失败！");
		    }
			//stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void saveBill(String bill ,boolean isBtMsg) {
    	String commPath = "select * from  bt_bill WHERE  bill='" + bill+"'";
        ResultSet rs = null;
		try {
			rs = stmt.executeQuery(commPath);
            ResultSetMetaData rsmd;
            int count =0;
            while(rs.next()) { 
            	count++; 
            }
            rs.close();
            rs = null;
    //        System.out.println("查询："+commPath +" 数目"+count );
            if (count < 1)
            {
            	commPath = "INSERT INTO bt_bill VALUES (\""+bill+"\","+(isBtMsg?1:0)+","+(isBtMsg?0:1)+")";            	
            	stmt.execute(commPath);
            }
            else
            {
            	commPath = "UPDATE bt_bill SET "+(isBtMsg?"btmes=1":"gamemsg=1")+" WHERE bill='"+bill+"'";
            	stmt.execute(commPath);
            }
          //  return stmt.execute(commPath);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
        	if(rs != null) {
        		try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
			MyDebug.log("执行"+commPath +" 失败" );
			e.printStackTrace();
		}
		
	}
	
	
	private static SqlHelper mIntance= new SqlHelper();
	
	public static SqlHelper getIntance() {
		return mIntance;
	}
}
