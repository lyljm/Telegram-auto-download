package org.downloader.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息组
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageGroup {
    Long id;

    Message[]messages;
}
