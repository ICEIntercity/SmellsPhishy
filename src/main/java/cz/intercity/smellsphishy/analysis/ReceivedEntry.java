package cz.intercity.smellsphishy.analysis;

import cz.intercity.smellsphishy.analysis.remote.IPLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Appropriated from Secoority
public class ReceivedEntry {

    Logger log = LoggerFactory.getLogger(ReceivedEntry.class);

    private String source;
    private String target;
    private String method;
    private Date dateTime;
    private String sourceIP;

    private IPLocation sourceLocation;

    private String dateTimePlaintext;

    private int score; // Not implemented

    private String info = "";

    private String plaintext;

    private enum ParserRegex {
        TARGET("(?<=(by\\s))(.|\\s)*?(?=( with| id| via|;))"),
        SOURCE("(?<=(from\\s))(.|\\s)*?(?=( by| with| id| via|;))"),
        METHOD("(?<=(with\\s))(.|\\s)*?(?=( id| via|;))"),
        DATETIME("(?<=(;\\s))(\\s)*.+"),
        IP("\\b(?!127\\.0\\.0\\.1|(10)|192\\.168|172\\.(2[0-9]|1[6-9]|3[0-2]))[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");

        private final String regex;

        ParserRegex(final String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }
    }

    //default constructor, not sure if necessary
    public ReceivedEntry(){}

    public ReceivedEntry(String entryPlaintext) {

        this.plaintext = entryPlaintext;

        //Regex parse headers to something readable and/or sortable
        Pattern targetPattern = Pattern.compile(ParserRegex.TARGET.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNIX_LINES);
        Matcher targetMatcher = targetPattern.matcher(entryPlaintext);
        if (targetMatcher.find()) {
            this.target = targetMatcher.group(0);
            entryPlaintext = targetMatcher.replaceAll("");
        }

        Pattern sourcePattern = Pattern.compile(ParserRegex.SOURCE.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNIX_LINES);
        Matcher sourceMatcher = sourcePattern.matcher(entryPlaintext);
        if (sourceMatcher.find()) {
            this.source = sourceMatcher.group(0);
            entryPlaintext = sourceMatcher.replaceAll("");

            //Get source IP
            Pattern ipPattern = Pattern.compile(ParserRegex.IP.getRegex(), Pattern.CASE_INSENSITIVE);
            Matcher ipMatcher = ipPattern.matcher(this.source);
            if (ipMatcher.find()) {
                this.sourceIP = ipMatcher.group(0);
            }
        }

        Pattern methodPattern = Pattern.compile(ParserRegex.METHOD.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNIX_LINES);
        Matcher methodMatcher = methodPattern.matcher(entryPlaintext);
        if (methodMatcher.find()) {
            this.method = methodMatcher.group(0);
            entryPlaintext = methodMatcher.replaceAll("");
        }

        Pattern dateTimePattern = Pattern.compile(ParserRegex.DATETIME.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNIX_LINES | Pattern.DOTALL);
        Matcher dateTimeMatcher = dateTimePattern.matcher(entryPlaintext);
        if (dateTimeMatcher.find()) {


            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

            String dateString = dateTimeMatcher.group().replace("\r", "").replace("\n", "");

            try {

                dateString = dateString.trim();

                //System.out.println(dateString);

                this.dateTime = format.parse(dateString);

            } catch (Exception e) {
                log.debug("Exception while parsing received entry date: " + e.getMessage());
            }

            this.dateTimePlaintext = dateString;
        }
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getMethod() {
        return method;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public String getDateTimePlaintext() {
        return dateTimePlaintext;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public IPLocation getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(IPLocation sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

}
