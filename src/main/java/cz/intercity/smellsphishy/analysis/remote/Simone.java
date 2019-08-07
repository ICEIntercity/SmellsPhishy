package cz.intercity.smellsphishy.analysis.remote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Simone {

    private String classification;
    private String numeric;
    private String vt_link;
    private String vt_positives;
    private String vt_total;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getNumeric() {
        return numeric;
    }

    public void setNumeric(String numeric) {
        this.numeric = numeric;
    }

    public String getVt_link() {
        return vt_link;
    }

    public void setVt_link(String vt_link) {
        this.vt_link = vt_link;
    }

    public String getVt_positives() {
        return vt_positives;
    }

    public void setVt_positives(String vt_positives) {
        this.vt_positives = vt_positives;
    }

    public String getVt_total() {
        return vt_total;
    }

    public void setVt_total(String vt_total) {
        this.vt_total = vt_total;
    }
}
