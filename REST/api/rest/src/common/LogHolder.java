package com.fxcm.btutil.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogHolder {
    protected Log logger = LogFactory.getLog(getClass());

    public static String log(Object ...args) {
        return StringUtils.buildString(args);
    } 

}
