package com.code;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) {
        File outfile = new File("test.txt");
        try {
            outfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
