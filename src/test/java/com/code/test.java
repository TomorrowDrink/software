package com.code;

import java.io.File;
import java.util.ArrayList;

public class test {

    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {

            if (tempList[i].isDirectory()) {
              System.out.println("文件夹：" + tempList[i]);
                files.add(tempList[i].getName());

            }
        }
        return files;
    }

    public static void main(String[] args) {
        String path = "/home/alison/Documents/allgit/"+ "1512190232";

        System.out.println(getFiles(path));
    }

}
