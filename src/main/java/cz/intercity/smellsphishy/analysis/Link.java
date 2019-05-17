package cz.intercity.smellsphishy.analysis;

import cz.intercity.smellsphishy.analysis.remote.VirusTotalResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Link {
    private String target;
    private String domain;
    private VirusTotalResult scanResults;

    public Link(String target){
        this.target = target;

        //^(?:https?:\/\/)?(?:[^@\/\n]+@)?(?:www\.)?([^:\/\n]+)
        Pattern linkPattern = Pattern.compile("^(?:https?://)?(?:[^@/\\n]+@)?(?:www\\.)?([^:/\\n]+)", Pattern.CASE_INSENSITIVE);
        Matcher linkMatcher = linkPattern.matcher(this.target);
        if(linkMatcher.find()){
            this.domain = linkMatcher.group(1);
        }
        this.scanResults = new VirusTotalResult();
    }

    public String getTarget() {
        return target;
    }

    public String getDomain() {
        return domain;
    }

    public VirusTotalResult getScanResults() {
        return scanResults;
    }

    public void setScanResults(VirusTotalResult scanResults) {
        this.scanResults = scanResults;
    }
}
