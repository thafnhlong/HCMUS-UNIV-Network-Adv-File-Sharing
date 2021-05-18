package hcmus.fileserver.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    public static Byte[] getBytesFromInt(int obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(bos);
            out.writeInt(obj);
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

    public static Integer getIntFromBytes(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream in = null;
        try {
            in = new DataInputStream(bis);
            return in.readInt();

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
}
