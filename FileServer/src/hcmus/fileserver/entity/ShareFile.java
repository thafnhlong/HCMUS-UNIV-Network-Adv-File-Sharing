package hcmus.fileserver.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ShareFile {

    public ShareFile(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    private String fileName;
    private long fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    // just level 1
    public static List<ShareFile> listFilesForFolder(String path) {
        final File folder = new File(path);
        List<ShareFile> temp = new LinkedList<ShareFile>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory())
                continue;
            temp.add(new ShareFile(fileEntry.getName(), fileEntry.length()));
        }
        return temp;
    }

    // public static void writeFileByBytes(String fileName, List<Byte> data) {
    //     try (FileOutputStream fos = new FileOutputStream("./Tmp/" + fileName)) {
    //         Byte[] yourBytes = data.toArray(new Byte[0]);
    //         fos.write(SObject.convertByteTobyte(yourBytes));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public static void mergeFile(String fileName, TreeMap<Long,String> tempFiles) {
    //     OutputStream os = null;
    //     InputStream is = null;

    //     try {
    //         os = new FileOutputStream("./Download/"+fileName);

    //         for (Entry<Long,String> entrylist : tempFiles.entrySet()) {
    //             String fname = "./Tmp/"+entrylist.getValue();
    //             try {
    //                 is = new FileInputStream(fname);
    //                 byte[] buffer = new byte[1024];
    //                 int length;
    //                 while ((length = is.read(buffer)) > 0) {
    //                     os.write(buffer, 0, length);
    //                 }
    //             } catch (IOException e) {
    //                 // TODO Auto-generated catch block
    //                 e.printStackTrace();
    //             } finally {
    //                 try {
    //                     is.close();
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 }
    //             }
    //             new File(fname).delete();
    //         }
    //     } catch (FileNotFoundException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             os.close();
    //         } catch (IOException e) {
    //             // TODO Auto-generated catch block
    //             e.printStackTrace();
    //         }
    //     }

    // }

}
