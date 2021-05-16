package service;

import entity.Communication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HealthCheck implements Runnable {

    private Communication communication;

    public HealthCheck(Communication communication) {
        this.communication = communication;
    }

    @Override
    public void run() {
        while (true) {
            final int DELAY = 20;
            try {
                TimeUnit.SECONDS.sleep(DELAY);
                if (!communication.IsLive()) {
                    break;
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
