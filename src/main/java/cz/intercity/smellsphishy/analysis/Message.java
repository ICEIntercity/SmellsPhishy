package cz.intercity.smellsphishy.analysis;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hsmf.*;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    private Header header;
    private String headerPlaintext;

    private String from;


    private String to;
    private String cc;
    private String subject;
    private String textBody;

    private Calendar msgDate;

    private List<Link> links;

    public Message(InputStream in) throws IOException {

        this.links = new ArrayList<>();

        //Load message from InputStream
        MAPIMessage mapiMessage;
        try {
            mapiMessage = new MAPIMessage(in);

        } catch (IOException e) {
            //Fatal
            throw (e);
        }

        try {
            this.from = mapiMessage.getDisplayFrom();
        } catch (ChunkNotFoundException cnfe) {
            //Not fatal, swallow
            this.from = "";
        }

        try {
            this.to = mapiMessage.getRecipientEmailAddress();
        } catch (ChunkNotFoundException cnfe) {
            //Ditto
            this.to = "";
        }

        try {
            this.cc = mapiMessage.getDisplayCC();
        } catch (ChunkNotFoundException cnfe) {
            //Again
            this.cc = "";
        }
        try {
            this.subject = mapiMessage.getSubject();
        } catch (ChunkNotFoundException cnfe) {
            //Encore une fois
            this.subject = "";
        }

        try {
            this.textBody = mapiMessage.getRtfBody();
        } catch (ChunkNotFoundException cnfe) {
            //This is getting old
            this.textBody = "";
            System.err.println(cnfe.getMessage());
        }

        try {
            this.msgDate = mapiMessage.getMessageDate();
        } catch (ChunkNotFoundException cnfe) {
            //Stahp
            this.msgDate = null;
        }

        /*
         *  The way Apache POI (HSMF) handles headers is wonky, as it takes them by row. It is best to reassemble the
         *  header before performing analysis.
         */
        try {
            String [] headers = mapiMessage.getHeaders();

            StringBuilder sb = new StringBuilder();
            for(String row : headers){
                sb.append(row).append("\n");
            }

            this.headerPlaintext = sb.toString();

            //Attempt header analysis immediately, discard empty headers
            if(!StringUtils.isBlank(headerPlaintext)) {
                this.header = new Header(headerPlaintext);
            }
        } catch (Exception e) {
            //Finally
            this.headerPlaintext = "";
            e.printStackTrace();
        }

        //Get links from e-mail body (Please work on .rtf...)
        Pattern linkPattern = Pattern.compile("(?<=(<a))(.)*?(href=['\"])([^'\"]+)(['\"])", Pattern.CASE_INSENSITIVE);
        Matcher linkMatcher = linkPattern.matcher(textBody);
        while(linkMatcher.find()){
            Link newLink = new Link(linkMatcher.group(4));
            this.links.add(newLink);
        }
        //this.debug();
    }

    public String getTxtHeaders() {
        return headerPlaintext;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getSubject() {
        return subject;
    }

    public String getTextBody() {
        return textBody;
    }

    public Calendar getMsgDate() {
        return msgDate;
    }

    public Header getHeader() {
        return header;
    }

    public String getHeaderPlaintext() {
        return headerPlaintext;
    }

    public List<Link> getLinks() {
        return links;
    }

    //Debug printout - DO NOT ACTUALLY USE
    private void debug() {

        System.out.println("To:" + this.to);
        System.out.println("From:" + this.from);
        System.out.println("Body:");
        System.out.println(this.textBody);

        System.out.println("--------------------------");
        System.out.println("Headers:");
        System.out.println(headerPlaintext);

    }



}