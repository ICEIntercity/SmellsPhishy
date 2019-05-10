package cz.intercity.smellsphishy.analysis.ticket.ipinfo;

import cz.intercity.smellsphishy.analysis.ReceivedEntry;

public class IPInfoFactory {

    public IPInfo getIPInfo(ReceivedEntry entry, int id){
        if(entry.getSourceIP() != null){
            return new IPInfo(entry, id);
        }

        else return null;
    }

    public IPInfo getIPInfo(ReceivedEntry entry){
        if(entry.getSourceIP() != null){
            return new IPInfo(entry, 0);
        }

        else return null;
    }
}
