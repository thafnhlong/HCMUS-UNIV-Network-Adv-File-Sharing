/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcmus.client.entity;

/**
 *
 * @author long
 */
import java.util.Random;

public class Timer {
    public static long timenow() {
        return System.currentTimeMillis()/1000;
    }
    public static String getUniqueNumber(){
        return String.valueOf(new Random().nextInt(999)+111)+String.valueOf(timenow());
    }
}
