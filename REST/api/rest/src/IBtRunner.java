package com.fxcm.btutil;

import java.util.List;

public interface IBtRunner {
    public static final String BT_TYPE = "bt";
    public static final String OPT_TYPE = "opt";

    public void runTask(String name, byte[] data) throws Exception;

    public List<String> getList() throws Exception;

    public void deleteTask(String id) throws Exception;

    public byte[] getLog(String id) throws Exception;

    public byte[] getInput(String id) throws Exception;

    public byte[] getOutput(String id) throws Exception;

    public byte[] getStat(String id) throws Exception;

}