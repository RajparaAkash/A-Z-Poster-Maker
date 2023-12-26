package com.letsmake.atoz.design.app_utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOOperations {

    public static void removeFile(File file) {
        if (file != null && file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static void removeFilesInDir(File file) {
        String[] list = file.list();
        if (list != null) {
            for (String file2 : list) {
                removeFile(new File(file, file2));
            }
        }
    }


    public static String removeExtension(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    public static void fastCopyFile(String str, String str2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(str);
        FileOutputStream fileOutputStream = new FileOutputStream(str2);
        AppStream.copy(fileInputStream, fileOutputStream);
        fileInputStream.close();
        fileOutputStream.close();
    }

}
