package cz.intercity.smellsphishy.analysis.ticket.ipinfo;

import cz.intercity.smellsphishy.analysis.ReceivedEntry;
import cz.intercity.smellsphishy.analysis.remote.IPLocation;

public class IPInfo {
    private String ip;
    private String locationInfo;
    private String isp;


    private IPInfo(){}

    protected IPInfo(ReceivedEntry source){
        this.ip = source.getSourceIP();
        IPLocation location = source.getSourceLocation();
        this.locationInfo = location.getCity() + ", " + location.getRegionName() + ", " + location.getCountry();
        this.isp = location.getIsp();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }
}
