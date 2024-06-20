package org.downloader.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Date;

public class FileDispatcher {
    public static void walkFile(File file) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory())
//                walkFile(f);
                continue;
            if (f.isFile()) {
                String oldName = FileUtil.getName(f);
                int i = 0;
                for (; i < oldName.length(); i++) {
                    char ch = oldName.charAt(i);
                    if (!Character.isDigit(ch))
                        break;
                }
                String prefix = oldName.substring(0, i);
                if (prefix.isEmpty()) continue;
                String time = DateUtil.format(new Date(Long.parseLong(prefix) * 1000), Config.dataFormat);
                String newPath = f.getParent() + "\\" + time + "\\" + oldName;
                File newFile = new File(newPath);
                try {
                    FileUtil.mkParentDirs(newFile);
                    Files.move(f.toPath(), newFile.toPath());
                } catch (FileAlreadyExistsException e) {
                    try {
                        if (newFile.delete()) {
                            Files.move(f.toPath(), newFile.toPath());
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.exit(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
