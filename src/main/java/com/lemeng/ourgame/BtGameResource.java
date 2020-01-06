package com.lemeng.ourgame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemeng.ourgame.btgame.BtGamePayBean;
import com.lemeng.ourgame.btgame.PhonePayBean;
import com.lemeng.ourgame.configuration.HelloWorldConfiguration;

/**
 * Created by rmiao on 3/14/2017.
 */
@Path("/btgame")
@Produces(MediaType.APPLICATION_JSON)
public class BtGameResource {
	public static String KEY = "3525D6EBDA3ABE7B2800F7E938E99842";
	public static int PID = 3154;
    private final AtomicLong counter;
    private final HelloWorldConfiguration configuration;
    
    public BtGameResource(HelloWorldConfiguration configuration) {
        this.configuration = configuration;
        this.counter = new AtomicLong();   
    }
    @POST
    public String sayHello(@QueryParam("data") Optional<String> name) {
        String value = String.format(name.orElse("fuck"));
        value = getURLDecoderString(value);
        if( value == null || value.length() == 0) {
        	
        	return "decode"; 
        }
        MyDebug.log2("getURLDecoderString value="+value);
        ObjectMapper mapper = new ObjectMapper();
        try {
			BtGamePayBean person = mapper.readValue(value, BtGamePayBean.class);
			String back  =  deal(person);
			 MyDebug.log2("back="+back);
			return back;
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return "fail";
    }
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String deal(BtGamePayBean person) {
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
        	MyDebug.log2("intTime = "+intTime+"  person.time="+ person.time);
        	if(intTime >( person.time+300) || intTime <( person.time-300)) {
        		
        		return "timeFault";
        	}
        	
        	StringBuilder str= new StringBuilder();
        	str.append("amount=");
        	str.append(person.amount);
        	str.append("&extendsInfo=");
        	str.append(person.extendsInfo);
        	str.append("&gameid=");
        	str.append(person.gameid);
        	str.append("&orderid=");
        	str.append(person.orderid);
        	str.append("&serverid=");
        	str.append(person.serverid);
        	str.append("&time=");
        	str.append(person.time);
        	str.append("&username=");
        	str.append(person.username);
        	str.append(KEY);
        	MyDebug.log("StringBuilder = "+str.toString());
        	try {
				if(MD5.verify(str.toString(), person.sign)) {
					MyDebug.log2("succ");
					SqlHelper.getIntance().saveBill(person.extendsInfo, true);
					return "succ";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else {
    		MyDebug.log("===person == null");
		}
    	
    	return "fail";      
    }
        
}
