package org.downloader.utils;

import cn.hutool.core.io.FileUtil;
import org.downloader.Bean.Channel;
import org.downloader.Bean.FileClass;
import org.downloader.Bean.MessageGroup;

import java.io.File;
import java.util.List;
import java.util.Set;

public class FileDispatcher {

    public static void dispathFile(String path, Channel channel, MessageGroup messageGroup) {
        // TODO: 2024/6/22 添加匹配格式 添加按关键词分类
        FileUtil.walkFiles(new File(path), file -> {
            if (FileUtil.isDirectory(file)) return;
            List<FileClass> fileClassify = channel.getFileClassify();
            int i = 0;
            for (; i < fileClassify.size() - 1; i++) {
                FileClass fileClass = fileClassify.get(i);
                Set<String> fileSuffix = fileClass.getFileSuffix();
                if (fileSuffix.contains(FileUtil.getSuffix(file))) {
                    if (!FileUtil.exist(fileClass.getPath()))
                        FileUtil.mkdir(fileClass.getPath());
                    FileUtil.move(file, new File(fileClass.getPath() + file.getName()), true);
                    return;
                }
            }
            FileClass fileClass = fileClassify.get(i);
            if (!FileUtil.exist(fileClass.getPath()))
                FileUtil.mkdir(fileClass.getPath());
            FileUtil.move(file, new File(fileClass.getPath() + file.getName()), true);
        });
    }
}
