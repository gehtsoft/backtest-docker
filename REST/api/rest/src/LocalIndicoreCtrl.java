package com.fxcm.btutil;

import java.util.List;


import com.fxcm.btutil.common.LogHolder;
import com.fxcm.btutil.common.FileUtils;

public class LocalIndicoreCtrl extends LogHolder implements IndicoreCtrl {

    String mIndiPath;
    String mStgPath;

    public void setIndicatorsPath(String p){
        mIndiPath = p;
    }

    public void setStrategyPath(String p){
        mStgPath = p;
    }

 
    public void addIndicator(String name, byte[] data) throws Exception{
        String path = mIndiPath + "/" + name;
        FileUtils.writeFile(path, data);
    }

    public List<String> getIndicators() throws Exception{
        return FileUtils.listFiles(mIndiPath);
    }

    public void deleteIndicator(String id) throws Exception{
        String path = mIndiPath + "/" + id;
        FileUtils.deleteFile(path);
    }

    public void addStrategy(String name, byte[] data) throws Exception{
        String path = mStgPath + "/" + name;
        FileUtils.writeFile(path, data);
    }
    public List<String> getStrategies() throws Exception{
        return FileUtils.listFiles(mStgPath);
    }
    public void deleteStrategy(String id) throws Exception{
        String path = mStgPath + "/" + id;
        FileUtils.deleteFile(path);
    }
}