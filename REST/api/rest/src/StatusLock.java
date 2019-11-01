package com.fxcm.btutil;

public class StatusLock {
    static StatusLock cInst = new StatusLock();

    public static StatusLock getInst() {
        return cInst;
    }

    String mCurrentTask;

    public synchronized boolean startTask(String t){
        if (mCurrentTask != null)
            return false;
        mCurrentTask = t;
        return true;
    }

    public synchronized boolean stopTask(String t){
        if (mCurrentTask == null)
            return true;
        if (!mCurrentTask.equals(t))
            return false;
        mCurrentTask = null;
        return true;
    }

    public synchronized String getTask(){
        return mCurrentTask;
    }
}