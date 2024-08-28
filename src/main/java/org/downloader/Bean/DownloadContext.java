package org.downloader.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DownloadContext {
    // 下载目录
    String parentPath;
    // tdl文件的目录
    String tdlFile;
    // 当前的对话
    Chat chat;
    // 当前的channel
    Channel channel;
    // tdl.json对象
    MessageGroup messageGroup;

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
        setTdlFile(parentPath + "tdl/tdl.json");
    }

    private void setTdlFile(String tdlFile) {
        this.tdlFile = tdlFile;
    }
}
