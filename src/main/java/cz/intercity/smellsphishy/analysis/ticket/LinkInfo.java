package cz.intercity.smellsphishy.analysis.ticket;

import cz.intercity.smellsphishy.analysis.Link;
import cz.intercity.smellsphishy.analysis.remote.VirusTotalResult;

public class LinkInfo {

    public LinkInfo(Link link){
        this.link = link.getTarget().replace(".", "[DOT]");
        this.detectionCount = link.getScanResults().getPositives();
    }

    private String link;
    private boolean clicked;
    private int detectionCount;

    public String getLink() {
        return link;
    }

    public int getDetectionCount() {
        return detectionCount;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setDetectionCount(int detectionCount) {
        this.detectionCount = detectionCount;
    }

    @Override
    public String toString(){
        return "LinkInfo{\n"
                + "\t\tlink: " + link + "\n"
                + "\t\tclicked: " + clicked + "\n"
                + "\t\tdetectionCount:" + detectionCount + "\n"
            +"\t\t}";
    }
}
