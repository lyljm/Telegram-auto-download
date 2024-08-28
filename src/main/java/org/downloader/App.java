package org.downloader;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.downloader.Bean.*;
import org.downloader.config.Config;
import org.downloader.utils.Executer;
import org.downloader.utils.FileDispatcher;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


@Slf4j
public class App {

    private static String DOWNLOAD_TDL_CMD = "cmd /c tdl chat export -c {} -T id -i {} , {} -o {}";

    private static String DOWNLOAD_CMD = "cmd /c tdl dl --skip-same -f {} -d {} --template \"{{ .MessageID }}_{{ replace .FileName `/` `_` `\\\\` `_` `:` `_` `*` `_` `?` `_` `<` `_` `>` `_` `|` `_` ` ` `_`  }}\" --continue";

    public static void main(String[] args) throws InterruptedException {
        System.out.println("App start download");
        // 初始化检查
        List<Channel> channels = init();
        // Channel配置完成
        while (true) {
            boolean isDownloaded = false;
            for (Channel channel : channels) {
                List<Chat> chats = channel.getChats();
                for (Chat chat : chats) {
                    // 获取下载上下文
                    DownloadContext downloadContext = getDownloadContext(channel, chat);
                    // 获取所有的消息
                    Message[] messages = downloadContext.getMessageGroup().getMessages();
                    if (ArrayUtil.isNotEmpty(messages)) {
                        isDownloaded = true;
                        // 下载
                        download(downloadContext);
                        // 文件分类
                        dispatchFile(downloadContext);
                        // 修改配置
                        updateConfig(downloadContext);
                        // 释放资源
                        // release(downloadContext);
                    }
                }
            }
            if (!isDownloaded) {
                Thread.yield();
                Thread.sleep(5000);
            }
        }
    }

    private static void release(DownloadContext downloadContext) {
        FileUtil.del(new File(downloadContext.getParentPath()));
    }

    // 初始化
    private static List<Channel> init() {
        Map<String, FileClass> fileClassMap = Config.fileClassMap;
        List<Channel> channels = Config.channels;
        for (Channel channel : channels) {
            List<String> fileClassifyName = channel.getFileClassifyName();
            for (String className : fileClassifyName) {
                FileClass fileClass = fileClassMap.get(className);
                if (null == fileClass)
                    throw new RuntimeException("文件配置错误： ChannelId为" + channel.getChannelId() + "的channel中fileClassifyName[" + className + "]不存在");
                String filePath = fileClass.getPath();
                if (!filePath.endsWith("/"))
                    fileClass.setPath(filePath + "/");
                channel.getFileClassify().add(fileClass);
            }
        }
        return channels;
    }

    private static void updateConfig(DownloadContext downloadContext) {
        Message[] messages = downloadContext.getMessageGroup().getMessages();
        long next = 0;
        for (Message message : messages) {
            next = message.getId() > next ? message.getId() : next;
        }
        downloadContext.getChat().setCurIndex(next + 1);
        Config.writeConfig();
    }

    public static void dispatchFile(DownloadContext downloadContext) {
        FileDispatcher.dispathFile(downloadContext.getParentPath(), downloadContext.getChannel(), downloadContext.getMessageGroup());
    }


    public static DownloadContext getDownloadContext(Channel channel, Chat chat) {
        DownloadContext downLoadContext = new DownloadContext();
        downLoadContext.setChat(chat);
        downLoadContext.setChannel(channel);
        downLoadContext.setParentPath(Config.tmpPath + "/" + chat.getChatId() + "/");
        FileUtil.mkParentDirs(downLoadContext.getTdlFile());
        // 导出tdl.json
        Executer.exeCmd(StrUtil.format(DOWNLOAD_TDL_CMD, chat.getChatId(), chat.getCurIndex(), chat.getCurIndex() + channel.getPop() - 1, downLoadContext.getTdlFile()), true);
        JSON json = JSONUtil.readJSON(new File(downLoadContext.getTdlFile()), Charset.defaultCharset());
        MessageGroup messageGroup = JSONUtil.toBean(json, MessageGroup.class, false);
        downLoadContext.setMessageGroup(messageGroup);
        return downLoadContext;
    }

    public static void download(DownloadContext downloadContext) {
        Executer.exeCmd(StrUtil.format(DOWNLOAD_CMD, downloadContext.getTdlFile(), downloadContext.getParentPath()), true);
    }
}
