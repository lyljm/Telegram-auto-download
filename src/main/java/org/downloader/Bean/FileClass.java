package org.downloader.Bean;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileClass {

    /**
     * 文件父级路径
     */
    private String path;

    /**
     * 文件命名规则
     */
    private String nameRule;
    /**
     * 文件类型
     */
    private Set<String>fileSuffix;

    public boolean isContains(String suffix){
        if(null==fileSuffix)
            throw new RuntimeException("没有添加suffix");
        return fileSuffix.contains(suffix);
    }

}
