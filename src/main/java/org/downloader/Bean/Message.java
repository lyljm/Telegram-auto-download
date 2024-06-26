package org.downloader.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    Long id;

    String type;

    String file;
}
