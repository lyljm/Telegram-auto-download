package org.downloader.utils;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.io.IOException;

public class Rwfile {
    public static String readAll(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        }catch (IORuntimeException e){
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            return "";
        }
        return fileReader.readString();
    }

    public static void write(String filePath, Long serial){
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(Long.toString(serial));
    }

    public static void write(String filePath, String content){
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(content);
    }
}
