package hcmus.fileserver.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Console {

    private static Console sing;
    private BufferedReader br;
    private BufferedWriter bw;

    private Console() {
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public static Console getInstace() {
        if (sing == null) {
            sing = new Console();
        }
        return sing;
    }

    public String readString(){
        try {
            return br.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    public<T> void write(T obj){
        try {
            bw.write(String.valueOf(obj));
            bw.flush();
        } catch (IOException e) {
        }
    }

    public void clean() {
        try {
            if (br != null)
                br.close();
            if (bw != null)
                bw.close();
        } catch (IOException e) {
        }
    }
}
