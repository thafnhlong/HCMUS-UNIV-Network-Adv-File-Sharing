package service;

import entity.Communication;

import java.io.IOException;
import java.util.HashMap;

public class FileServerService extends ClientService {

    public static HashMap<String, String> content = new HashMap<>();
    private String key;

    public FileServerService(Communication communication) {
        super(communication);
    }

    public void Accept() throws IOException, InterruptedException {
        if (!communication.IsConnected()) {
           ClearRepo();
           return;
        }
        
        var msg = communication.receive();
        if (null != msg) {
            var values = msg.split("`");
            key = values[0] +":"+ values[1];
            content.put(key, msg);
        }
    }

    public void ClearRepo() {
        content.remove(key);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Accept();
            } catch (IOException | InterruptedException e) {
                ClearRepo();
                break;
            }
        }
    }

    public static String GetContent() {
        return String.join(">", content.values());
    }
}
