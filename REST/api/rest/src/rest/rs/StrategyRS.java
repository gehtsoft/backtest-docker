package com.fxcm.btutil.rest.rs;

import com.fxcm.btutil.IndicoreCtrl;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Path("/strategy")
@Produces(MediaType.APPLICATION_JSON)
public class StrategyRS extends BaseRS {

    @Autowired
    @Qualifier("indicore_ctrl")
    IndicoreCtrl mCtrl;


    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({ MediaType.APPLICATION_OCTET_STREAM})
    public Response addStrategy(
            @FormDataParam("src") InputStream src_data,
            @FormDataParam("src") FormDataContentDisposition src_info) {

        String fname =  null;
        try {
            UploadedFile uf = readFile(src_data, src_info);
            fname = uf.name;
            logger.debug("Adding strategy " + fname);
            mCtrl.addStrategy(uf.name, uf.data);
        } catch(ResponseException re){
            return re.resp;
        } catch(Exception e){
            logger.error("Cannot add strategy " + fname, e);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
        }

        return Response.ok().build();
    }


    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    @Path("list")
    public Response getList() {
        try {
            List<String> l = mCtrl.getStrategies();
            return Response.ok().entity(l).build();
        } catch (Throwable t){
            logger.error("Cannot get strategies list", t);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(t.getMessage()).build();
        }
    }

    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTask(Map<String, Object> params) {
        Object id = params.get("name");
        if (id == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("name is empty").build();

        logger.debug("Deleting strategy " + id);
        try {
            mCtrl.deleteStrategy((String)id);
        } catch (Throwable t){
            logger.error("Cannot delete strategy " + id, t);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(t.getMessage()).build();
        }

        return Response.ok().build();
    }
}


