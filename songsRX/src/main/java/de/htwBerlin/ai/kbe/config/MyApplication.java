package de.htwBerlin.ai.kbe.config;

import org.glassfish.jersey.server.ResourceConfig;

import de.htwBerlin.ai.kbe.security.AuthenticationFilter;

public class MyApplication extends ResourceConfig
{
    public MyApplication()
    {
    	register(new DependencyBinder());
    	
        packages("de.htwBerlin.ai.kbe.services");
 
        //Register Auth Filter here
        register(AuthenticationFilter.class);
        
    }
}
