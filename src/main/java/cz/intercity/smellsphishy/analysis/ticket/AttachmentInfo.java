package cz.intercity.smellsphishy.analysis.ticket;

import cz.intercity.smellsphishy.analysis.AttachmentHash;

public class AttachmentInfo {

    private String name;
    private String hash;
    private boolean opened;
    private int detectionCount;

    public AttachmentInfo(){
        this.name = "";
        this.hash = "";
    }

    public AttachmentInfo(AttachmentHash attachment){
        this.name = attachment.getFileName();
        this.hash = attachment.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getDetectionCount() {
        return detectionCount;
    }

    public void setDetectionCount(int detectionCount) {
        this.detectionCount = detectionCount;
    }

    @Override
    public String toString(){
        return "AttachmentInfo{\n"
                + "\t\tname: " + name + "\n"
                + "\t\thash: " + hash + "\n"
                + "\t\topened" + opened + "\n"
                + "\t\tdetectionCount" + detectionCount + "\n"
                +"\t\t}";
    }
}
