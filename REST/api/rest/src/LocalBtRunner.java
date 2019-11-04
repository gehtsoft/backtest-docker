package com.fxcm.btutil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import com.fxcm.btutil.common.ProcessFactory;
import com.fxcm.btutil.common.LogHolder;

import java.util.stream.Collectors;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class LocalBtRunner extends LocalRunner implements IBtRunner {

    ProcessFactory mProcessFactory;
    String mOutFile;
    String mInFile;
    String mLogFile;
    String mStatFile;


    public void setOutFile(String f) {
        mOutFile = f;
    }

    public void setInFile(String f) {
        mInFile = f;
    }

    public void setLogFile(String f) {
        mLogFile = f;
    }

    public void setStatFile(String f){
        mStatFile = f;
    }
/*
    protected String[] getProcessArgs(){
        return new String[]{mInFile, "/v", "/o", mOutFile, "/d", mLogFile, "/so", mStatFile, "/filte};
    }
*/

    void writeInput(String name, byte[] data) throws Exception {
        _writeInput(name, mInFile, data);
    }

    public void runTask(String name, byte[] data) throws Exception {
        String uname = name.toUpperCase();
        writeInput(uname, data);
        _runTask(uname);
    }


    public List<String> getList() throws Exception {
        return _getList();
    }

    public void deleteTask(String id) throws Exception {
        _deleteFolder(id);
    }


    public byte[] getLog(String id) throws Exception {
        return _getFile(id, mLogFile);
    }

    public byte[] getInput(String id) throws Exception {
        return _getFile(id, mInFile);
    }

    public byte[] getOutput(String id) throws Exception {
        if (mZip != null) {
            logger.debug("Attempt to zip output for " +id);
            String zpath = mOutFile + ".zip";
            if (!_checkFile(id, zpath)) {
                logger.debug("Zip " + id + " does not exist");
                _zipFile(id, mOutFile, zpath);
            }
            if (_checkFile(id, zpath)) {
                return _getFile(id, zpath);
            }
            logger.debug("Unable to make zip for outupt of " + id);
        }
        return _getFile(id, mOutFile);
    }

    public byte[] getStat(String id) throws Exception {
        return _getFile(id, mStatFile);
    }



}