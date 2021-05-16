package service;

import entity.Communication;

import java.io.IOException;
import java.util.HashMap;

public class FileServerService extends ClientService {

    public static HashMap<String, String> content = new HashMap<>();
    private static int num = 0;

    public FileServerService(Communication communication) {
        super(communication);
    }

    public void Accept() throws IOException, InterruptedException {
        content.put(communication.GetIPClient(), communication.receive());
    }

    public void ClearRepo() {
        content.remove(communication.GetIPClient());
    }

    @Override
    public void run() {
        HealthCheck();

        while (true) {
            try {
                Accept();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                ClearRepo();
            }
        }
    }

    private void HealthCheck() {
        var checkerThread = new Thread(new HealthCheck(communication));
        checkerThread.start();
    }

    public static String GetContent() {
        return String.join(">", content.values());
    }
}
