package com.fxcm.btutil;

import java.util.List;

public interface DataCtrl {
    public void addDataFile(String name, byte[] data) throws Exception;
    public List<String> getDataFiles() throws Exception;
    public void deleteDataFile(String id) throws Exception;
}