package com.fxcm.btutil.common;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.Map;

public class ProcessFactory {

    public static interface IProcessCallback {
        public void onProcessFinished(String id, Exception e);
    }

    public static interface IOutputCallback {
        public void processLine(String id, int n, String line);
    }



    class OutputStreamer extends LogOutputStream {
        String key;
        int N = 0;
        IOutputCallback out;
        OutputStreamer(String key, IOutputCallback out) {
            this.key = key;
            this.out = out;
        }
        protected void processLine(String line, int logLevel) {
            logger.debug("[" + key +"," + (N++) +  "]: " + line);
            if (out != null)
                out.processLine(key, N, line);
        }
    }

    class ProcessHandler implements ExecuteResultHandler {
        IProcessCallback mCallback;
        String key;
        ExecuteStreamHandler mIO;
        ProcessHandler(String key, IProcessCallback callback, ExecuteStreamHandler io) {
            this.key = key;
            mCallback = callback;
            mIO = io;            
        }
        public void onProcessComplete(int exitValue) {
            logger.info("Process for " + key + " exited with " + exitValue);
            mRunning.decrementAndGet();
            if (mCallback != null)
                mCallback.onProcessFinished(key, null);
            try {
                mIO.stop();
            } catch(Throwable t) {
                logger.error("Error on process io stop", t);
            }
        }
        public void onProcessFailed(ExecuteException e) {
            logger.error("Process for " + key + " failed", e);
            logger.fatal("Cannot create working process: " + e);
            mRunning.decrementAndGet();
            if (mCallback != null)
                mCallback.onProcessFinished(key, e);
            try {
                mIO.stop();
            } catch(Throwable t) {
                logger.error("Error on process io stop", t);
            }

        }
    }



    protected Log logger = LogFactory.getLog(getClass());

    long mLastRun;
    String mPath;
    int mPeriod = 0;

    AtomicInteger mRunning = new AtomicInteger(0);
    int mMax = 0;

    public void setPath(String path) {
        mPath = path;
    }

    public void setPeriod(int period) {
        mPeriod = period;
    }

    public void setMax(int m){
        mMax = m;
    }

    public boolean haveCapacity() {
        if (mMax < 1)
            return true;            
        return mRunning.get() < mMax;
    }

    public int getMax() {
        return mMax;
    }

    public int getRunning() {
        return mRunning.get();
    }

    public boolean createProcess(String key, String workdir, Map<String, String> env, IOutputCallback io_handler, IProcessCallback callback, String ... args ) throws Exception {
        long now = System.currentTimeMillis();
        if (mPeriod > 0 && now - mLastRun < mPeriod) {
            logger.warn("Process throttling in action: rejecting ");
            return false;
        }

        if (mMax > 0 && mRunning.intValue() >= mMax) {
            logger.warn("Maximum of " + mMax + " processes number reached");
            return false;
        }

        mLastRun = now;
        CommandLine cmdLine = new CommandLine(mPath);

        if (args != null) {
            for (String a : args) {
                cmdLine.addArgument(a);
            }
        }


        File f = new File(mPath);
        File fwork_dir = workdir == null ? f.getParentFile() : new File(workdir);

        Executor executor = new DefaultExecutor();
        executor.setWorkingDirectory(fwork_dir);

        PumpStreamHandler eio_handler = new PumpStreamHandler(new OutputStreamer(key, io_handler));

        executor.setStreamHandler(eio_handler);
        ProcessHandler ph = new ProcessHandler(key, callback, eio_handler);

        int PN  = mRunning.intValue();
        logger.error("Running process " + mPath + (fwork_dir != null ? (" in " + fwork_dir.getPath()) : "") + ", N " + PN);

        eio_handler.start();
        if (env == null)
            executor.execute(cmdLine, ph);
        else
            executor.execute(cmdLine, env, ph);
        mRunning.incrementAndGet();
        return true;
    }
}
