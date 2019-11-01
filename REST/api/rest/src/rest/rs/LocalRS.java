package com.fxcm.btutil.rest.rs;


import com.fxcm.btutil.StatusLock;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/local")
@Produces(MediaType.APPLICATION_JSON)
public class LocalRS extends BaseRS {

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    @Path("status")
    public String getStatus() throws Exception {
        String ret = StatusLock.getInst().getTask();
        return ret != null ? ret : "0";
    }

}


