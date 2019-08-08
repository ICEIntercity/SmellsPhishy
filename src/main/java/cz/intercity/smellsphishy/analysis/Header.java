package cz.intercity.smellsphishy.analysis;

import cz.intercity.smellsphishy.common.exception.HeaderAnalysisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.*;

/**
 * public class Header
 * Contains the intenal representation of e-mail headers and parsing methods for plaintext headers
 *
 * @author Intercity
 */
public class Header {

    private Logger log = LoggerFactory.getLogger(Header.class);

    private String sender;
    private String recipient;
    private String replyTo;
    private String cc;

    private String subject;
    private String messageID;
    private Date dateTime;

    private List<ReceivedEntry> received;
    private ReceivedEntry messageSource = null;

    private String plaintext;

    /**
     * public Header
     *
     * Default constructor of the cz.intercity.smellsphishy.analysis.Header class
     */
    public Header(){
        this.received = new ArrayList<>();
    }

    /**
     * public Header
     *
     * Alternate constructor for the header class, automatically performing the analysis upon receiving the plaintext
     * header.
     *
     * @param headerPlaintext - Plaintext representation of the header
     */
    public Header(String headerPlaintext){
        this.received = new ArrayList<>();
        this.plaintext = headerPlaintext;

        try{
            this.analyze();
        }
        catch(HeaderAnalysisException e){
            System.err.println(e.getMessage());
        }
    }
    /**
     * private enum ParserRegex
     *
     * Contains a collection of regular expressions used to parse the e-mail headers
     */
    private enum ParserRegex {
        SENDER("(?<=(From:\\s)).+"),
        RECIPIENT("(?<=(To:\\s)).+"),
        CC("(?<=(Cc:\\s)).+"),
        SUBJECT("(?<=(Subject:\\s)).+"),
        MSGID("(?<=(Message-ID:\\s)).+"),
        DATETIME("(?<=(Date:\\s)).+"),
        RECEIVED("(?<=(Received:\\s))(.|\\n(\\s)+)*"),
        REPLY_TO("(?<=(Reply-To:\\s)).+");

        private final String regex;

        ParserRegex(final String regex) {
            this.regex = regex;
        }

        public String getRegex(){
            return regex;
        }
    }

    /**
     * public void analyze()
     *
     * Analyze header using existing plaintext representation.
     *
     */
    //TODO: Error Handling
    public void analyze() throws HeaderAnalysisException{

        /*
        If parsing the header fails for some reason (regular expressions are not known for their reliability),
        then we want to warn the user that the analysis is not to be trusted (and will probably only contain
        a bunch of NULLs)

        The flag will be checked at the end of this method, and an exception will be thrown if none of the fields were
        successfully parsed. Furthermore, the exception will also be thrown if the sender chain could not be found.
        */
        boolean senderChainIntegrityFlag = true;
        boolean fieldsIntegrityFlag = false;

        /*
        Header fields are parsed using RegEx. There is probably a better way, but it eludes me at the moment...
         */
        //Sender parsing
        Pattern senderPattern = Pattern.compile(ParserRegex.SENDER.getRegex(), Pattern.CASE_INSENSITIVE);
        Matcher senderMatcher = senderPattern.matcher(plaintext);
        if(senderMatcher.find()){
            this.sender = senderMatcher.group(0);
            fieldsIntegrityFlag = true;
        }

        //Recipient parsing
        Pattern recipientPattern = Pattern.compile(ParserRegex.RECIPIENT.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher recipientMatcher = recipientPattern.matcher(plaintext);
        if(recipientMatcher.find()){
            this.recipient = recipientMatcher.group(0);
            fieldsIntegrityFlag = true;
        }

        //Reply-To parsing
        Pattern replyPattern = Pattern.compile(ParserRegex.REPLY_TO.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher replyMatcher = replyPattern.matcher(plaintext);
        if(replyMatcher.find()){
            this.replyTo = replyMatcher.group(0);
        }
        else
            this.replyTo = null;

        //CC Parsing
        Pattern ccPattern = Pattern.compile(ParserRegex.CC.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher ccMatcher = ccPattern.matcher(plaintext);
        if(ccMatcher.find()){
            this.cc = ccMatcher.group(0);
            fieldsIntegrityFlag = true;
        }

        //Subject parsing
        Pattern subjectPattern = Pattern.compile(ParserRegex.SUBJECT.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher subjectMatcher = subjectPattern.matcher(plaintext);
        if(subjectMatcher.find()){
            this.subject = subjectMatcher.group(0);
            fieldsIntegrityFlag = true;
        }

        //Message ID Parsing
        Pattern msgIdPattern = Pattern.compile(ParserRegex.MSGID.getRegex(), Pattern.CASE_INSENSITIVE);
        Matcher msgIdMatcher = msgIdPattern.matcher(plaintext);
        if(msgIdMatcher.find()){
            this.messageID = msgIdMatcher.group(0);
            fieldsIntegrityFlag = true;
        }

        //Date & Time received parsing
        Pattern dateTimePattern = Pattern.compile(ParserRegex.DATETIME.getRegex(), Pattern.CASE_INSENSITIVE);
        Matcher dateTimeMatcher = dateTimePattern.matcher(plaintext);
        if(dateTimeMatcher.find()){

            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
            try {
                this.dateTime = format.parse(dateTimeMatcher.group(0));
                fieldsIntegrityFlag = true;
            }
            catch(Exception e){
                log.debug("Exception while parsing date: " + e.getMessage());
            }
        }

        //Received chain parsing
        Pattern receivedPattern = Pattern.compile(ParserRegex.RECEIVED.getRegex(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNIX_LINES);
        Matcher receivedMatcher = receivedPattern.matcher(plaintext);

        while(receivedMatcher.find()){
            ReceivedEntry newEntry = new ReceivedEntry(receivedMatcher.group());
            received.add(0, newEntry);
        }

        /*
         *  Try to determine the source IP
         *  TODO: Put a big scary warning on the analysis page to not actually trust this result
         */
        for(ReceivedEntry entry : received){
            if(entry.getSourceIP() != null && this.messageSource == null){

                log.info("E-mail source IP determined as " + entry.getSourceIP());
                this.setMessageSource(entry);

                break;
            }
        }

        if(received.isEmpty())
            senderChainIntegrityFlag = false;

        if(!fieldsIntegrityFlag || !senderChainIntegrityFlag){
            throw new HeaderAnalysisException("Common header data or sender chain information invalid.");
        }

        log.info("Successfully loaded header information.");
    }

    /*Getters/Setters*/
    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getCc() {
        return cc;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getMessageID() {
        return messageID;
    }

    public List<ReceivedEntry> getReceived() {
        return received;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public String getIp(){
        if(messageSource != null)
            return messageSource.getSourceIP();
        else
            return "Unknown";
    }

    public ReceivedEntry getMessageSource() {
        return messageSource;
    }

    private void setMessageSource(ReceivedEntry messageSource) {
        this.messageSource = messageSource;
    }

    @Deprecated
    private void setMessageSourceByIP(String sourceIP){

        boolean sourceIsSet = false;
        for(ReceivedEntry e : received){
            if(e.getSourceIP() != null && e.getSourceIP().equals(sourceIP)){
                this.messageSource = e;
                sourceIsSet = true;
                break;
            }
        }

        if(!sourceIsSet){
            log.warn("Requested source IP '" + sourceIP + "' not found. Message source remains unchanged.");
        }
    }
}
