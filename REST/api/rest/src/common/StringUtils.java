package com.fxcm.btutil.common;

import org.apache.commons.codec.binary.Hex;

import java.util.Map;
import java.util.HashMap;

public class StringUtils {

    public static boolean nullOrEmpty(String str) {
        return str == null || str.length() < 1; 
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch(Throwable t) {
            return 0;
        }
    }

    public static String bin2Hex(byte[] bt) {
        return Hex.encodeHexString(bt);
    }

    public static byte[] hex2Bin(String hex) throws Exception {
        return Hex.decodeHex(hex.toCharArray());
    }

    public static Map<String, Object> createMap(Object ...args) {
        int idx = 0;
        String key = null;
        Map<String, Object> ret = new HashMap<String, Object>();
        for (Object o : args) {
            if (idx % 2 == 0) {
                if (!(o instanceof String))
                    return null;
                key = (String)o;
            } else {
                ret.put(key, o);
            }
            idx++;
        } 
        return ret;
    }

    public static String buildString(Object ...args) {
        if (args == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (Object a : args) {
            sb.append(a).append(" ");
        }
        return sb.toString();
    } 

   public static String buildStringCompact(Object ...args) {
        if (args == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (Object a : args) {
            sb.append(a);
        }
        return sb.toString();
    } 

    public static double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch(Throwable t) {
            return 0;
        }
    }

    public static boolean parseBool(String str) {
        if (nullOrEmpty(str))
            return false;
        return str.equalsIgnoreCase("y") || str.equals("1");
    }

}