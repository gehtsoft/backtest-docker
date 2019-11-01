package com.fxcm.btutil.rest.rs;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.io.FilenameUtils;

import javax.ws.rs.core.Response;


import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.google.common.io.ByteStreams;

public class BaseRS
{
    protected final Log logger = LogFactory.getLog(getClass());

    protected Map<String, Object> createMap() {
        return new HashMap<String, Object>();
    }

    protected List createList() {
        return new ArrayList();
    }

    protected long getLong(Map<String, Object> params, String name) {
        Object o = params.get(name);
        if (o == null)
            return 0;
        if (o instanceof Long) {
            return (Long)o;
        } else if (o instanceof Integer) {
            int i = (Integer)o;
            return (long)i;
        }
        return 0;
    }

    protected int getInt(Map<String, Object> params, String name) {
        Object o = params.get(name);
        if (o == null)
            return 0;
        return o instanceof Integer ? (Integer)o : 0;
    }

    protected int parseInt(String s, int def) {
        if (s == null)
            return def;
        try {
            return Integer.parseInt(s);
        } catch(Throwable t) {
        }
        return def;
    }

    protected class UploadedFile {
        public String name;
        public byte[] data;
        public long size;

        public UploadedFile(String n, byte[] d, long l){
            name = n;
            data = d;
            size = l;
        }
    }

    class ResponseException extends Exception {
        Response resp;

        ResponseException(Response r){
            resp = r;
        }
    }

    protected UploadedFile readFile(InputStream src_data, FormDataContentDisposition src_info) throws ResponseException {
        byte[] src = null;
        String fname =  null;
        long size = 0;
        if (src_info != null) {
            String raw_name =  src_info.getFileName();
            fname = FilenameUtils.getName(raw_name);
            logger.debug("Uploaded file name reduced to " + fname + " from " + raw_name);
            size = src_info.getSize();

            try {
                if (src_data != null) {
                    src = ByteStreams.toByteArray(src_data);
                }
            } catch (IOException e) {
                logger.error("Cannot get src", e);
                throw new ResponseException(Response.status(Response.Status.BAD_REQUEST).entity("Cannot get input data: " + e.getMessage()).build());
            }

            size = src == null ? 0 : src.length;

            logger.debug("src " + fname + " " + size);
        }

        if (src == null) {
            throw new ResponseException(Response.status(Response.Status.BAD_REQUEST).entity("No input data").build());
        }
        return new UploadedFile(fname, src, size);
    }

}