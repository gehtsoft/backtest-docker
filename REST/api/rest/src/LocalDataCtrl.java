package com.fxcm.btutil;

import com.fxcm.btutil.common.FileUtils;
import com.fxcm.btutil.common.LogHolder;

import java.io.File;
import java.util.List;

public class LocalDataCtrl extends LogHolder implements DataCtrl {

    String mPath;

    public void setPath(String p){
        mPath = p;
    }

    public void addDataFile(String name, byte[] data) throws Exception{
        File f = new File(mPath);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                throw new Exception("Cannot create folder " + mPath);
            }
            logger.debug("Created data folder " + mPath);
        }

        String path = mPath + "/" + name;
        FileUtils.writeFile(path, data);
        logger.debug("Added data file " + path);
    }

    public List<String> getDataFiles() throws Exception{
        return FileUtils.listFiles(mPath);
    }

    public void deleteDataFile(String id) throws Exception{
        String path = mPath + "/" + id;
        FileUtils.deleteFile(path);
        logger.debug("Deleted data file " + path);
    }


}