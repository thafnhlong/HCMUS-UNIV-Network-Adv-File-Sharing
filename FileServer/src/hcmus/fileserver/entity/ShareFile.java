package hcmus.fileserver.entity;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

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

    //just level 1
    public static List<ShareFile> listFilesForFolder(String path) {
        final File folder = new File(path);
        List<ShareFile> temp = new LinkedList<ShareFile>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) continue;
            temp.add(new ShareFile(fileEntry.getName(),fileEntry.length()));
        }
        return temp;
    }
}
