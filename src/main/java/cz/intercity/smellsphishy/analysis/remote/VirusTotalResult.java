package cz.intercity.smellsphishy.analysis.remote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true )
public class VirusTotalResult {
    private String permalink;
    private int positives;
    private int total;

    public VirusTotalResult(){
        permalink = "";
        positives = 0;
        total = 0;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getPositives() {
        return positives;
    }

    public void setPositives(int positives) {
        this.positives = positives;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



    @Override
    public String toString(){
        return "Detection{" +
                "permalink='" + permalink + "'" +
                " positives=" + positives + "'" +
                " total=" + total + "'" +
                '}';
    }
}
