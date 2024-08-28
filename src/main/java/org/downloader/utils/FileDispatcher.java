package org.downloader.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.downloader.Bean.Channel;
import org.downloader.Bean.FileClass;
import org.downloader.Bean.MessageGroup;

import java.io.File;
import java.util.Date;
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
                if (fileSuffix.contains(FileUtil.getSuffix(file).toLowerCase())) {
                    String dataRule = StrUtil.isEmpty(fileClass.getNameRule())?DatePattern.NORM_DATE_PATTERN:fileClass.getNameRule();
                    String filePath = fileClass.getPath() + DateUtil.format(new Date(), dataRule) + "/";
                    if (!FileUtil.exist(filePath))
                        FileUtil.mkdir(filePath);
                    FileUtil.move(file, new File(filePath + file.getName()), true);
                    return;
                }
            }
            FileClass fileClass = fileClassify.get(i);
            String dataRule = StrUtil.isEmpty(fileClass.getNameRule())?DatePattern.NORM_DATE_PATTERN:fileClass.getNameRule();
            String filePath = fileClass.getPath() + DateUtil.format(new Date(), dataRule) + "/";
            if (!FileUtil.exist(fileClass.getPath()))
                FileUtil.mkdir(fileClass.getPath());
            FileUtil.move(file, new File(filePath + file.getName()), true);
        });
    }
}
