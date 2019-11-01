package com.fxcm.btutil.rest.rs;

import com.fxcm.btutil.IBtRunner;
import com.fxcm.btutil.StatusLock;
import com.fxcm.btutil.common.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class BtCommonRS extends BaseRS {


    protected abstract IBtRunner getRunner();

    Pattern pattern;

    static final String NAME_PATTERN = "^[_A-Z0-9-]+$";


    public BtCommonRS() {
        pattern = Pattern.compile(NAME_PATTERN);
    }


    public Response _runTask(String nameid, InputStream src_data, FormDataContentDisposition src_info) {
        
        logger.debug("Run task " + nameid);

        StatusLock status_lock = StatusLock.getInst();

        String current_task = status_lock.getTask();
        if (current_task != null) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Resource is busy").build();
        }

        if (StringUtils.nullOrEmpty(nameid)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Task id is empty").build();
        }

        String uname = nameid.toUpperCase();
        Matcher matcher = pattern.matcher(uname);
        if (!matcher.matches())
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid id format: must be alphanumeric_-").build();



        byte[] src = null;
        String fname =  null;
        long size = 0;
        try {
            UploadedFile uf = readFile(src_data, src_info);
            fname = uf.name;
            src = uf.data;
            size = uf.size;
        } catch(ResponseException re){
            return re.resp;
        }

        synchronized(status_lock) {
            logger.debug("Locked by " + nameid);
            try {
                status_lock.startTask(nameid);
                getRunner().runTask(nameid, src);
                status_lock.stopTask(nameid);
                logger.debug("Released by " + nameid);
                return Response.ok().build();

            } catch(Exception e) {
                logger.error("Cannot run task " + nameid, e);
                status_lock.stopTask(nameid);
                logger.debug("Released by " + nameid);
                return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
            }
        }
    }


    public List<String> _getList() throws Exception {
        return getRunner().getList();
    }

    public void _deleteTask(String id) throws Exception {
        id = id.toUpperCase();
        logger.debug("Deleting task " + id);
        getRunner().deleteTask(id);
    }


    public Response _getLog(String id) {
        id = id.toUpperCase();        
        try {
            byte[] ret = getRunner().getLog(id);
            if (ret == null)
                return Response.status(Response.Status.NO_CONTENT).build();
            ByteArrayInputStream out = new ByteArrayInputStream(ret);
            return Response.ok(out).build();

        } catch(Exception e) {
            logger.error("Cannot get log for  " + id, e);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
        }
    }


    public Response _getInput(String id) {
        id = id.toUpperCase();        
        try {
            byte[] ret = getRunner().getInput(id);
            if (ret == null)
                return Response.status(Response.Status.NO_CONTENT).build();
            ByteArrayInputStream out = new ByteArrayInputStream(ret);
            return Response.ok(out).build();

        } catch(Exception e) {
            logger.error("Cannot get input for  " + id, e);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
        }
    }

    public Response _getOutput(String id) {
        id = id.toUpperCase();
        try {
            byte[] ret = getRunner().getOutput(id);
            if (ret == null)
                return Response.status(Response.Status.NO_CONTENT).build();
            ByteArrayInputStream out = new ByteArrayInputStream(ret);
            return Response.ok(out).build();

        } catch(Exception e) {
            logger.error("Cannot get output for  " + id, e);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
        }
    }

    public Response _getStat(String id) {
        id = id.toUpperCase();
        try {
            byte[] ret = getRunner().getStat(id);
            if (ret == null)
                return Response.status(Response.Status.NO_CONTENT).build();
            ByteArrayInputStream out = new ByteArrayInputStream(ret);
            return Response.ok(out).build();

        } catch(Exception e) {
            logger.error("Cannot get stat for  " + id, e);
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
        }
    }

}


