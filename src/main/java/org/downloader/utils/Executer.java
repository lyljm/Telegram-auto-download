package org.downloader.utils;

import lombok.extern.slf4j.Slf4j;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@Slf4j
public class Executer {
    public static void exeCmd(String cmd, boolean isLog) {
        log.info("当前执行命令：" + cmd);
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (isLog) {
                    log.info(line);
                }
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            System.exit(1);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

    }
}
