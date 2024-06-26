package org.downloader.Bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    /**
     * chat Id
     */
    String chatId;

    /**
     * 当前序号
     */
    long curIndex;
}
