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

public class LocalRunner extends LogHolder  {

    protected ProcessFactory mProcessFactory;
    protected ProcessFactory mZip;
    protected String mWorkDir;
    protected String[] mArgs;

    public void setProcessFactory(ProcessFactory pf) {
        mProcessFactory = pf;
    }

    public void setZip(ProcessFactory pf) {
        mZip = pf;
    }


    public void setWorkDir(String path){
        mWorkDir =  path;
    }

    public void setArgs(List<String> l){
        mArgs = l.stream().toArray(String[]::new);
    }

    void _writeInput(String name, String fname, byte[] data) throws Exception {
        File w = new File(mWorkDir);
        if (!w.exists()){
            boolean rc = w.mkdirs();
            logger.debug("Inited workdir " + mWorkDir + ": " + rc);
        }
        String path = mWorkDir + "/" + name;
        File f = new File(path);
        if (f.exists())
            throw new Exception("Folder " + name + " already exists, remove the task first");
        if (!f.mkdirs()) {
            throw new Exception("Cannot create folder " + path);
        }

        String fpath_str = path + "/" + fname;
        Path fpath = Paths.get(fpath_str);
        Files.write(fpath, data);
        logger.debug("Saved file"  + fpath_str);
    }

    protected String[] getProcessArgs(){
        return mArgs;
    }

    protected void _runTask(String name) throws Exception {
        ProcessFactory.IOutputCallback out = null;
        ProcessCallback pc = new ProcessCallback();
        String[] args = getProcessArgs();
        String workdir = mWorkDir + "/" + name;
        synchronized(pc) {
            boolean  rc = mProcessFactory.createProcess(name, workdir, null, out, pc, args);
            pc.wait();
        }
        if (pc.err != null)
            throw pc.err;
    }

    protected boolean _checkFile(String name, String file){
        String path_str = mWorkDir + "/" + name + "/" +  file;
        File f = new File(path_str);
        return f.exists();
    }

    protected void _zipFile(String name, String file, String out) throws Exception {
        String path = mWorkDir + "/" + name + "/" +  file;
        String zpath = mWorkDir + "/" + name + "/" +  out;
        String[] args = new String[]{zpath, path, "-j"};
        logger.debug("Zip " + path + " to  "+ zpath);
        ProcessCallback pc = new ProcessCallback();
        synchronized(pc) {
            mZip.createProcess("Zip-output-name", null, null, null, pc, args);
            pc.wait();
        }
    }

    protected byte[] _getFile(String name, String file) throws Exception {
        String path_str = mWorkDir + "/" + name + "/" +  file;
        Path out_path = Paths.get(path_str);
        return Files.readAllBytes(out_path);
    }

    class ProcessCallback implements ProcessFactory.IProcessCallback {

        public Exception err;

        public void onProcessFinished(String key, Exception e) {
            logger.debug("Finished process for " + key + ", error " + e);
            err = e;
            synchronized(this) {
                this.notify();        
            }
        }
    }

    protected List<String> _getList() throws Exception {
        logger.debug("Getting list of " + mWorkDir);
        File[] dirs = new File(mWorkDir).listFiles();
        List<String> ret = Arrays.stream(dirs).map(f -> f.getName()).collect(Collectors.toList());
        return ret;
    }

    protected void _deleteFolder(String id) throws Exception {
        String dir = mWorkDir + "/" + id;
        File fdir = new File(dir);
        if (!fdir.exists())
            return;
        FileUtils.deleteDirectory(fdir);
    }
}