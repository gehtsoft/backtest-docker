package com.fxcm.btutil.rest;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class BtUtilRest extends ResourceConfig {


    public BtUtilRest() {
        packages("com.fxcm.btutil.rest.rs");

        register(MultiPartFeature.class);

        //Jackson
        packages("org.glassfish.jersey.jackson");
        packages("org.codehaus.jackson.jaxrs");
    }

}