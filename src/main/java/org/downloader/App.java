package org.downloader;

import cn.hutool.core.io.FileUtil;
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
    public static void main(String[] args) throws InterruptedException {
        System.out.println("App start download");
        //初始化检查

        //初始化
        Map<String, FileClass> fileClassMap = Config.fileClassMap;
        List<Channel> channels = Config.channels;
        for (Channel channel : channels) {
            List<String> fileClassifyName = channel.getFileClassifyName();
            for (String className : fileClassifyName) {
                FileClass fileClass = fileClassMap.get(className);
                if (null == fileClass)
                    throw new RuntimeException("文件配置错误： ChannelId为" + channel.getChannelId() + "的channel中fileClassifyName[" + className + "]不存在");
                channel.getFileClassify().add(fileClass);
            }
        }

        //Channel配置完成
        while (true) {
            boolean downloaded=false;
            for (Channel channel : channels) {
                List<Chat> chats = channel.getChats();
                for (Chat chat : chats) {
                    String parentPath=Config.tmpPath+"/"+chat.getChatId()+"/";
                    String tdlPath = parentPath+"tdl/tdl.json";
                    FileUtil.mkParentDirs(tdlPath);
//                    导出tdl json
                    Executer.exeCmd("cmd /c tdl chat export -c " + chat.getChatId() + " -T id -i "
                            + chat.getCurIndex() + "," + (chat.getCurIndex()+channel.getPop()-1) + " -o " + tdlPath, true);
                    JSON json = JSONUtil.readJSON(new File(tdlPath), Charset.defaultCharset());
                    MessageGroup messageGroup = JSONUtil.toBean(json, MessageGroup.class, false);
                    Message[] messages = messageGroup.getMessages();
                    if(messages!=null&&messages.length>0){
                        downloaded=true;
//                        下载
                        Executer.exeCmd("cmd /c tdl dl --skip-same -f "+tdlPath+" -d "+parentPath+" --template \"{{ .MessageDate }}_{{ .MessageID }}_{{ replace .FileName `/` `_` `\\\\` `_` `:` `_` `*` `_` `?` `_` `<` `_` `>` `_` `|` `_` ` ` `_`  }}\" --continue", true);
//                        文件分类
                        FileDispatcher.dispathFile(parentPath,channel,messageGroup);
                        chat.setCurIndex(chat.getCurIndex()+channel.getPop());
                        Config.writeConfig();
                    }
                }
            }
            if(!downloaded){
                Thread.yield();
                Thread.sleep(5000);
            }
        }
    }



}
