package org.downloader.config;

import org.downloader.Bean.Channel;
import org.downloader.Bean.Chat;
import org.downloader.Bean.FileClass;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ConfigTest {

//    @Test
//    public void TestConfigRead() {
//        List<Channel> channels = Config.channels;
//        System.out.println(channels);
//    }

    @Test
    public void creatInitTemplate() {
        String s = "aaa,bbb,ccc";
        String suffix = "mp3,flac,wav";
        ArrayList<FileClass> fileClasses = new ArrayList<>();
        HashSet<String> fileSuffix = new HashSet<>(Arrays.asList(suffix.split(",")));
        fileClasses.add(new FileClass( "C:/aaa/bbb/ccc", "", fileSuffix));
        fileClasses.add(new FileClass( "C:/bbb/bbb/ccc", "", fileSuffix));
        fileClasses.add(new FileClass( "C:/aaa/bbb/ccc", "", fileSuffix));
        fileClasses.add(new FileClass( "C:/aaa/bbb/ccc", "", fileSuffix));
        LinkedList<Chat> chats = new LinkedList<>();
        chats.add(new Chat("12323", 0));
        chats.add(new Chat("12334", 0));
        chats.add(new Chat("12334", 0));
        Channel channel = new Channel(1,chats, 10, 5, Arrays.asList(s.split(",")), fileClasses);

        Config.channels.add(channel);
        Config.fileClassMap.put("aaa",new FileClass( "C:/aaa/bbb/ccc", "", fileSuffix));
        Config.fileClassMap.put("bbb",new FileClass( "C:/bbb/bbb/ccc", "", fileSuffix));
        Config.fileClassMap.put("ccc",new FileClass( "C:/aaa/bbb/ccc", "", fileSuffix));
        Config.fileClassMap.put("ddd",new FileClass( "C:/aaa/bbb/ccc", "", fileSuffix));

        Config.writeConfig();
    }
}
