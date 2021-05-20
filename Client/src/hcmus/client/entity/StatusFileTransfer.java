package hcmus.client.entity;

public class StatusFileTransfer {
    private String ip;
    private int port;
    private String fileName;
    private String download;
    public StatusFileTransfer(String ip, int port, String fileName, String download) {
        this.ip = ip;
        this.port = port;
        this.fileName = fileName;
        this.download = download;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getDownload() {
        return download;
    }
    public void setDownload(String download) {
        this.download = download;
    }
    @Override
    public String toString() {
        return "StatusFileTransfer [download=" + download + ", fileName=" + fileName + ", ip=" + ip + ", port="
                + port + "]";
    }
    
}