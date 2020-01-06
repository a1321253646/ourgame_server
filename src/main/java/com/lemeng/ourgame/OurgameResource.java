package com.lemeng.ourgame;

import java.util.concurrent.atomic.AtomicLong;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemeng.ourgame.configuration.HelloWorldConfiguration;

/**
 * Created by rmiao on 3/14/2017.
 */
@Path("/ourgame")
@Produces(MediaType.APPLICATION_JSON)
public class OurgameResource {
    private final AtomicLong counter;
    private final HelloWorldConfiguration configuration;
    
    public OurgameResource(HelloWorldConfiguration configuration) {
        this.configuration = configuration;
        this.counter = new AtomicLong();   
    }

    @POST
    public String sayHello(RequiteBean person) {
    	MyDebug.log("sayHello");
    	ResponseDateStringBean bean = ResponseDateStringBean.getErrorDate("ssdad2ase");
    	bean.setVersion(configuration.getUpdateVersion());
    	
    	if(person != null ) {
    		
    		if(person.channel != null && person.channel.equals("btgame") && configuration.getmIsBtgamePlay() == -1) {
    			bean.setStatus(-8866L);
    	    	ObjectMapper mapper = new ObjectMapper();
    	    	try {
    	    		String json = mapper.writeValueAsString(bean);
    	    		MyDebug.log2("back json="+json);
    	    		return json;
    	    	}catch(Exception e) {
    	    		e.printStackTrace();
    	    	}
    	    	return "";
    		}
    		
        	ObjectMapper mapper = new ObjectMapper();
        	try {
        		String json = mapper.writeValueAsString(person);
        		MyDebug.log2("json="+json);
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        	try {
        		
        		String back =  SqlManager.getIntance().dealMessage(configuration,person);
        		if(back != null ) {
        			if(!back.equals("error")) {
        				MyDebug.log2("back back="+back);
        				return back;
        			}
        		}
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
    	}else {
    		MyDebug.log("===person == null");
		}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		String json = mapper.writeValueAsString(bean);
    		MyDebug.log2("back json="+json);
    		return json;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return "";
        
    }
    
    
    


}
