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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class SObject {
    public static <T> Byte[] getBytes(T obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            return convertbyteToByte(bos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }
        return null;
    }

    public static <T> T parseByte(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return (T) o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return null;
    }

    public static Byte[] getBytesFromLong(long obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(bos);
            out.writeLong(obj);
            out.flush();
            return convertbyteToByte(bos.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }
        return null;
    }

    public static Long getLongFromBytes(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream in = null;
        try {
            in = new DataInputStream(bis);
            return in.readLong();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        return null;
    }

    public static Byte[] convertbyteToByte(byte[] yourBytes) {
        Byte[] newBytes = new Byte[yourBytes.length];
        for (int i = 0; i < yourBytes.length; i++) {
            newBytes[i] = yourBytes[i];
        }
        return newBytes;
    }

    public static byte[] convertByteTobyte(Byte[] yourBytes) {
        byte[] newBytes = new byte[yourBytes.length];
        for (int i = 0; i < yourBytes.length; i++) {
            newBytes[i] = yourBytes[i];
        }
        return newBytes;
    }

    public static Long getLong(byte[] data, int index) {
        byte[] out = new byte[8];
        int i = 0;
        for (int j = 0; j < 8; j++) {
            out[i++] = data[index++];
        }
        return getLongFromBytes(out);
    }

    public static List<Byte> getSubBytes(byte[] data, int start, int end) {
        List<Byte> ret = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            ret.add(data[i]);
        }
        return ret;
    }

}
