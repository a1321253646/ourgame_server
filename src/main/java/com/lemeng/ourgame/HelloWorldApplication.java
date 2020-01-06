package com.lemeng.ourgame;

import com.lemeng.ourgame.configuration.HelloWorldConfiguration;

import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.lemeng.ourgame.TemplateHealthCheck;
import io.dropwizard.Application;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


/**
 * Created by Ryan Miao on 3/14/2017.
 */
public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }


    
    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
		 try {
	            // The newInstance() call is a work around for some
	            // broken Java implementations

	            //Class.forName("com.mysql.cj.jdbc.Driver");com.mysql.jdbc.
	            Class.forName("com.mysql.jdbc.Driver");
	            System.out.println("开始");
	        } catch (Exception ex) {
	        	System.out.println("拉起服务失败");
	            // handle the error
	        }
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) throws Exception {

        final OurgameResource resource = new OurgameResource(configuration);
        final BtGameResource btgame = new BtGameResource(configuration);
        final PhoneBtGameResource phonegame = new PhoneBtGameResource(configuration);
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
        environment.jersey().register(btgame);
        environment.jersey().register(phonegame);
        environment.jersey().register(healthCheck);


    }
}
