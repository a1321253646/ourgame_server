package com.lemeng.ourgame;

import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemeng.ourgame.btgame.BtGamePayBean;
import com.lemeng.ourgame.btgame.PhonePayBean;
import com.lemeng.ourgame.configuration.HelloWorldConfiguration;

/**
 * Created by rmiao on 3/14/2017.
 */
@Path("/phonebtgame")
@Produces(MediaType.APPLICATION_JSON)
public class PhoneBtGameResource {
	public static String KEY = "3525D6EBDA3ABE7B2800F7E938E99842";
	public static int PID = 3154;
    private final AtomicLong counter;
    private final HelloWorldConfiguration configuration;
    
    public PhoneBtGameResource(HelloWorldConfiguration configuration) {
        this.configuration = configuration;
        this.counter = new AtomicLong();   
    }


    @POST
    public String sayHello(PhonePayBean person) {
    	MyDebug.log("sayHello");
    	ResponseDateStringBean bean = ResponseDateStringBean.getErrorDate("ssdad2ase");
    	bean.setVersion(configuration.getUpdateVersion());
    	if(person != null) {
        	ObjectMapper mapper = new ObjectMapper();
        	try {
        		String json = mapper.writeValueAsString(person);
        		MyDebug.log2("json="+json);
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        	long time =  System.currentTimeMillis();
        	int intTime = ((int)(time/1000));
        	SqlHelper.getIntance().saveBill(person.extendsInfo, false);
        	return "succ";
    	}else {
    		MyDebug.log("===person == null");
		}
    	
    	return "fail";      
    }
    
    
}
