package org.downloader.Bean;

import cn.hutool.core.annotation.PropIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Channel {
    int channelId;
    /**
     * 多个聊天
     */
    List<Chat>chats;

    /**
     * 优先级
     */
    int priority;

    /**
     * 跳数
     */
    int pop;

    /**
     * 分类 Name
     */
    List<String> fileClassifyName;

    @PropIgnore
    List<FileClass>fileClassify=new LinkedList<>();

}
