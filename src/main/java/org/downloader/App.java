package org.downloader;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.downloader.Bean.Group;
import org.downloader.Bean.Message;
import org.downloader.utils.Config;
import org.downloader.utils.Executer;
import org.downloader.utils.Rwfile;
import org.downloader.utils.FileDispatcher;

import java.io.File;
import java.util.ArrayList;


@Slf4j
public class App {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("APP start download");
        while (true) {
            String serialString = Rwfile.readAll(Config.serialFile);
            long serial = Long.parseLong(serialString.length() > 0 ? serialString : "0");
            Executer.exeCmd("cmd /c tdl chat export -c "+Config.url +" -T id -i " + (serial + 1) + "," + (serial + 1 + Config.pop) + " -o " + Config.jsonFile, true);
            String json = Rwfile.readAll(Config.jsonFile);
            Group group = JSONUtil.toBean(json, Group.class);
            long maxSerial = serial + Config.pop;
            ArrayList<Message> messages = new ArrayList<>(group.getMessages().length);
            for (Message mes : group.getMessages()) {
                if (mes.getId() > serial && mes.getId() <= maxSerial) {
                    messages.add(mes);
                }
            }
            group.setMessages(messages.toArray(new Message[0]));
            int pop = group.getMessages().length;
            if (!messages.isEmpty()) {
                Rwfile.write(Config.jsonFile, JSONUtil.toJsonStr(JSONUtil.parse(group)));
                Executer.exeCmd(Config.downCmd, true);
                log.info("currentSerial: " + serial + "  pop: " + pop + "  newMaxSerial: " + maxSerial);
                FileDispatcher.walkFile(new File(Config.downFilePath));
                Rwfile.write(Config.serialFile, (serial + pop));
            }
            System.out.print("\rcurrentSerial: " + serial + "  pop: " + pop + "  newMaxSerial: " + (maxSerial + 1));
            Thread.sleep(5000);
        }
    }

}
