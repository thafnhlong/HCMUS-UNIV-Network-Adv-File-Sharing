package hcmus.fileserver.entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileReader {
    public static final int BUFFER = 500; //52*1042

    public static byte[] readFileByIndex(long offset, String fileName) {
        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile("./SharedFile/"+fileName,"r");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        byte[] data = null;
        try {
            long fz = raf.length();
            long tmp = BUFFER;
            if(fz-offset< BUFFER){
                tmp = fz-offset;
            }
            if(tmp < 1){
                return null;
            }
            int newbuffer = (int)tmp;
            data = new byte[newbuffer];
            raf.seek(offset);
            raf.read(data, 0, newbuffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            try {
                raf.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return data;
    }
}
