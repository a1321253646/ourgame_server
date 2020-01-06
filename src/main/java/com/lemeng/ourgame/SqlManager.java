package com.lemeng.ourgame;

import java.util.ArrayList;

import com.lemeng.ourgame.configuration.HelloWorldConfiguration;

public class SqlManager {
	
	private SqlManager() {
		
		SqlHelper.getIntance();
		
	}
	
	public static class UserToken{
		String token = null;
		boolean isNew = false;
	}
	
	public String dealMessage(HelloWorldConfiguration configuration,RequiteBean person) {
		UserToken tokenBean = new UserToken();
		boolean isSkip = false;
		if(person == null || person.getUser() == null || person.getUser().isEmpty()) {
			ResponseDateStringBean bean =  ResponseDateStringBean.getErrorDate("user is Empty");	
			bean.setVersion(configuration.getUpdateVersion());
			return bean.toString();
		}
		
		if(person.getUser().startsWith("skip_")) {
			isSkip = true;
			person.setUser( person.getUser().replace("skip_", ""));
		}
		
		if(person.getChannel() == null || person.getChannel().isEmpty()) {
			person.setChannel( "base");
		}

		
		
		if(person.getToken() != null && person.getToken().length() > 0) {
			String mac = SqlHelper.getIntance().getMacByToken(person.getToken());
			if(mac != null && mac.equals("error_query")) {
				ResponseDateStringBean bean =  ResponseDateStringBean.getErrorDate("查询mac失败");	
				bean.setVersion(configuration.getUpdateVersion());
				return bean.toString();
			}
			if(!isSkip&&!person.getUser().equals(mac)) {
				ResponseDateStringBean bean =  ResponseDateStringBean.getErrorDate("token is fault");	
				bean.setStatus(-1001L);
				bean.setVersion(configuration.getUpdateVersion());
				return bean.toString();
			}
		}else {
			SqlHelper.getIntance().findUserToken(person.getUser(),tokenBean,person.getChannel());
			person.setToken(tokenBean.token);
		}	
		
		if(person.getDate() == null || person.getDate().length < 1) {
			ResponseDateStringBean bean = ResponseDateStringBean.getErrorDate("date is Empty");
			bean.setVersion(configuration.getUpdateVersion());
			return bean.toString();
		}
	//	MyDebug.log("ar.length() = "+ar.length());
		ArrayList<SqlDateBean> list = new ArrayList<SqlDateBean>();
		int level = -1;
		boolean isSuccess = true;
		for(int i = 0 ; i< person.getDate().length ;i++) {
			
			SqlDateBean bean = person.getDate()[i];

			if(bean.action == -1) {
				ResponseDateStringBean bean2 =  ResponseDateStringBean.getErrorDate("actionType is -1");	
				bean2.setVersion(configuration.getUpdateVersion());
				return bean2.toString();
			}			

	//		MyDebug.log("bean.type ="+bean.type+" bean.id= "+bean.id);
			if(bean.action == 5) {
				boolean isChange = false;
				String newToken = person.getToken();
				if( tokenBean.isNew) {
					return  SqlHelper.getIntance().getLocalByUser(configuration,person.getUser(),newToken,person.getToken(),true,isChange);
				}else if( bean.extra != null &&  bean.extra.length() >0 && ! bean.extra.equals("-1")) {
					newToken = bean.extra;		
					isChange = true;
				}
				return  SqlHelper.getIntance().getLocalByUser(configuration,person.getUser(),newToken,person.getToken(),false,isChange);
				
			}else if (bean.action == 6){
				return  SqlHelper.getIntance().getRankingListLevel(configuration,person.getToken());
			}else if(bean.action == 7) {
				ResponseDateStringBean bean2 = ResponseDateStringBean.getUpdateDate(person.getToken());
				bean2.setVersion(configuration.getUpdateVersion());
				bean2.setIsmust(configuration.getUpdateMust());
				bean2.setDate(configuration.getUpdateDec());
				return bean2.toString();
			}
			if(bean.type == SqlDateBean.TYPE_GAME && bean.id == SqlDateBean.GAME_ID_NET_LEVEL) {
				level = Integer.parseInt(bean.extra);
				SqlHelper.getIntance().saveMaxLeve(person.getToken(), level);
			}else if(bean.type == SqlDateBean.TYPE_GAME && bean.id == SqlDateBean.GAME_ID_PLAYER_NAME) {
				SqlHelper.getIntance().saveUserName(person.getToken(), bean.extra);
			}else if(bean.type == SqlDateBean.TYPE_GAME && bean.id == SqlDateBean.GAME_ID_VERSION_CODE) {
				int version = Integer.parseInt(bean.extra);
				SqlHelper.getIntance().updateVersion(person.getToken(),version);
			}else  if(bean.type == SqlDateBean.TYPE_GAME && bean.id == SqlDateBean.GAME_ID_MAX_TIME){
				long maxTime = Long.parseLong(bean.extra);
				if(maxTime >=  System.currentTimeMillis() + 86400000) {
					return ResponseDateStringBean.getErrorDate("time is error").toString();
				}
			}
			list.add(bean);
		}
		SqlHelper.getIntance().updateLastTime(person.getToken(),person.getChannel());
	//	MyDebug.log("list.length() = "+list.size());
		isSuccess = SqlHelper.getIntance().testTransaction(person.getToken(), list);
		ResponseDateStringBean  back = ResponseDateStringBean.getErrorDate(isSuccess?"success":"fault");
		back.setStatus(isSuccess?0L:1L);
		back.setToken(person.getToken());
		back.setVersion(configuration.getUpdateVersion());
		return back.toString();
	}
	
	
	private static SqlManager mIntance= new SqlManager();
	
	public static SqlManager getIntance() {
		return mIntance;
	}
}
