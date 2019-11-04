package com.fxcm.btutil.rest.rs;

import com.fxcm.btutil.IBtRunner;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;


@Path("/bt")
@Produces(MediaType.APPLICATION_JSON)
public class BacktestRS extends BtCommonRS {

    @Autowired
    @Qualifier("bt_runner")
    IBtRunner mRunner;

    protected IBtRunner getRunner() {
        return mRunner;
    }

    @POST
    @Path("run")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({ MediaType.APPLICATION_OCTET_STREAM})
    public Response runBt(
            @FormDataParam("name") String nameid,
            @FormDataParam("src") InputStream src_data,
            @FormDataParam("src") FormDataContentDisposition src_info) {
        return _runTask(nameid, src_data, src_info);
    }


    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    @Path("list")
    public List<String> getList() throws Exception {
        return _getList();
    }

    @DELETE
    @Path("{id}/delete")
    public void deleteTask(@PathParam("id") String id) throws Exception {
        _deleteTask(id);
    }


    @GET
    @Produces({ MediaType.APPLICATION_OCTET_STREAM})
    @Path("{id}/log")
    public Response getLog(@PathParam("id") String id) {
        return _getLog(id);
    }


    @GET
    @Produces({ MediaType.APPLICATION_OCTET_STREAM})
    @Path("{id}/input")
    public Response getInput(@PathParam("id") String id) {
        return _getInput(id);
    }

    @GET
    @Produces({ MediaType.APPLICATION_OCTET_STREAM})
    @Path("{id}/output")
    public Response getOutput(@PathParam("id") String id) {
        return _getOutput(id);
    }

    @GET
    @Produces({ MediaType.APPLICATION_OCTET_STREAM})
    @Path("{id}/stat")
    public Response getStat(@PathParam("id") String id) {
        return _getStat(id);
    }

}


