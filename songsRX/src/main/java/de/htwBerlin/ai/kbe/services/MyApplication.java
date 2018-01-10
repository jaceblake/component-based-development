package de.htwBerlin.ai.kbe.services;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig
{
    public MyApplication()
    {
        packages("de.htwBerlin.ai.kbe.services");
 
        //Register Auth Filter here
        register(AuthenticationFilter.class);
    }
}
