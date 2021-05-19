package hcmus.fileserver.entity;

import java.util.Random;

public class Timer {
    public static long timenow() {
        return System.currentTimeMillis()/1000;
    }
    public static String getUniqueNumber(){
        return String.valueOf(new Random().nextInt(999)+111)+String.valueOf(timenow());
    }
}
