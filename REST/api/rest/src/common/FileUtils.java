package com.fxcm.btutil.common;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Collectors;
import java.util.Arrays;


public class FileUtils {

   public static void writeFile(String path, byte[] data) throws Exception {
        Path fpath = Paths.get(path);
        Files.write(fpath, data);
    }

    public static List<String> listFiles(String path) throws Exception {
        File fpath = new File(path);
        if (!fpath.exists()){
            return new ArrayList<String>();
        }
        File[] dirs = fpath.listFiles();
        if (dirs == null){
            return new ArrayList<String>();
        }

        return Arrays.stream(dirs).map(f -> f.getName()).collect(Collectors.toList());
    }

    public static void deleteFile(String path) throws Exception {
        File file = new File(path); 
        boolean rc = file.delete();
        //logger.debug("Delete file " + path + ": " + rc);
    }

}