package cz.intercity.smellsphishy.analysis.remote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPLocation {
    private String query;
    private String status;

    private String country;
    private String regionName;
    private String city;
    private String isp;
    private String org;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String region) {
        this.regionName = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    @Override
    public String toString(){
        return "IPLocation{" +
            " query=" + this.query +
            " country=" + this.country +
            " region=" + this.regionName +
        '}';
    }
}
