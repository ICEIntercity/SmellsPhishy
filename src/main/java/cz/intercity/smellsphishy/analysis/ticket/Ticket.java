package cz.intercity.smellsphishy.analysis.ticket;

import cz.intercity.smellsphishy.analysis.AttachmentHash;
import cz.intercity.smellsphishy.analysis.Link;
import cz.intercity.smellsphishy.analysis.Message;
import cz.intercity.smellsphishy.analysis.ReceivedEntry;
import cz.intercity.smellsphishy.analysis.ticket.ipinfo.IPInfo;
import cz.intercity.smellsphishy.analysis.ticket.ipinfo.IPInfoFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ticket {

    //Disabling default constructor to prevent unintended behaviour
    private Ticket(){}

    public Ticket(Message message) {
        this.subject = message.getSubject();
        this.receivedAt = message.getMsgDate();
        this.recipients = message.getTo();

        this.links = new ArrayList<>();
        for(Link l : message.getLinks()){

            this.links.add(new LinkInfo(l));
        }

        this.attachments = new ArrayList<>();
        for(AttachmentHash hash : message.getAttachmentHashes()){
            this.attachments.add(new AttachmentInfo(hash));
        }

        IPInfoFactory ipInfoFactory = new IPInfoFactory();
        this.IPTrace = new ArrayList<>();

        int index = 0;
        for(ReceivedEntry e : message.getHeader().getReceived()){
            IPInfo entryIPInfo = ipInfoFactory.getIPInfo(e, index);
            if(entryIPInfo != null){
                IPTrace.add(entryIPInfo);
                index++;
            }
        }

        this.srcIP = IPTrace.get(0);
    }

    /**
     * NOT IMPLEMENTED
     */
    public enum Severity {
        SEVERITY_SL1,
        SEVERITY_SL2,
        SEVERITY_SL3,
        SEVERITY_SL4
    }

    private Severity ticketSeverity;

    private String subject;

    private String summary;

    private Calendar receivedAt;

    private Date reportedAt;

    private boolean linkClicked;
    private Date linkClickedAt;

    private boolean attachmentOpened;
    private Date attachmentOpenedAt;

    private boolean credentialsEntered;
    private Date credentialsEnteredAt;

    private String recipients;

    private List<LinkInfo> links;

    private List<AttachmentInfo> attachments;

    private List<IPInfo> IPTrace;
    private IPInfo srcIP;
    private boolean ipSAPConfigured;
    private String arinLocation;
    private boolean ipSpoofed;

    private boolean pwdResetRequested;
    private boolean senderBlockRequested;
    private boolean ironportRequested;
    private boolean sapSpamRequested;

    public boolean isIronportRequested() {
        return ironportRequested;
    }

    public void setIronportRequested(boolean ironportRequested) {
        this.ironportRequested = ironportRequested;
    }

    public Severity getTicketSeverity() {
        return ticketSeverity;
    }

    public void setTicketSeverity(Severity ticketSeverity) {
        this.ticketSeverity = ticketSeverity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Calendar getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Calendar receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Date getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(Date reportedAt) {
        this.reportedAt = reportedAt;
    }

    public boolean isLinkClicked() {
        return linkClicked;
    }

    public void setLinkClicked(boolean linkClicked) {
        this.linkClicked = linkClicked;
    }

    public Date getLinkClickedAt() {
        return linkClickedAt;
    }

    public void setLinkClickedAt(Date linkClickedAt) {
        this.linkClickedAt = linkClickedAt;
    }

    public boolean isAttachmentOpened() {
        return attachmentOpened;
    }

    public void setAttachmentOpened(boolean attachmentOpened) {
        this.attachmentOpened = attachmentOpened;
    }

    public Date getAttachmentOpenedAt() {
        return attachmentOpenedAt;
    }

    public void setAttachmentOpenedAt(Date attachmentOpenedAt) {
        this.attachmentOpenedAt = attachmentOpenedAt;
    }

    public boolean isCredentialsEntered() {
        return credentialsEntered;
    }

    public void setCredentialsEntered(boolean credentialsEntered) {
        this.credentialsEntered = credentialsEntered;
    }

    public Date getCredentialsEnteredAt() {
        return credentialsEnteredAt;
    }

    public void setCredentialsEnteredAt(Date credentialsEnteredAt) {
        this.credentialsEnteredAt = credentialsEnteredAt;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public List<LinkInfo> getLinks() {
        return links;
    }

    public void setLinks(List<LinkInfo> links) {
        this.links = links;
    }

    public List<AttachmentInfo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentInfo> attachments) {
        this.attachments = attachments;
    }

    public List<IPInfo> getIPTrace() {
        return IPTrace;
    }

    public IPInfo getSrcIP() {
        return srcIP;
    }

    public boolean isIpSAPConfigured() {
        return ipSAPConfigured;
    }

    public void setIpSAPConfigured(boolean ipSAPConfigured) {
        this.ipSAPConfigured = ipSAPConfigured;
    }

    public void setSrcIP(IPInfo srcIP) {
        this.srcIP = srcIP;
    }

    public String getArinLocation() {
        return arinLocation;
    }

    public void setArinLocation(String arinLocation) {
        this.arinLocation = arinLocation;
    }

    public boolean isIpSpoofed() {
        return ipSpoofed;
    }

    public void setIpSpoofed(boolean ipSpoofed) {
        this.ipSpoofed = ipSpoofed;
    }

    public boolean isPwdResetRequested() {
        return pwdResetRequested;
    }

    public void setPwdResetRequested(boolean pwdResetRequested) {
        this.pwdResetRequested = pwdResetRequested;
    }

    public boolean isSenderBlockRequested() {
        return senderBlockRequested;
    }

    public void setSenderBlockRequested(boolean senderBlockRequested) {
        this.senderBlockRequested = senderBlockRequested;
    }

    public boolean isSapSpamRequested() {
        return sapSpamRequested;
    }

    public void setSapSpamRequested(boolean sapSpamRequested) {
        this.sapSpamRequested = sapSpamRequested;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        sdf.setTimeZone(timeZone);

        sb.append("Ticket{\n" + "\t Subject: ").append(subject).append("\n")
                .append("\t Summary: ").append(summary).append("\n")
                .append("\t ReceivedAt: ").append(sdf.format(receivedAt.getTime())).append("\n")
                .append("\t ReportedAt: ").append(sdf.format(reportedAt.getTime())).append("\n")
                .append("\t Recipients: ").append(recipients).append("\n");

        sb.append("\tLinks: \n");
        for(LinkInfo l : links){
            sb.append("\t\t").append(l.toString()).append("\n");
        }

        sb.append("\tAttachments: \n");
        for(AttachmentInfo ati : attachments){
            sb.append("\t\t").append(ati.toString());
        }

        return sb.toString();
    }

    /**
     * Small utility function to properly display receivedAt as String
     */
    public String displayReceivedAt(){
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz", Locale.US);
        sdf.setTimeZone(timeZone);

        return sdf.format(receivedAt.getTime());
    }

    public String displayDateTime(Date dt){
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm zzz", Locale.US);
        sdf.setTimeZone(timeZone);

        return sdf.format(dt.getTime());
    }

    public String displayDate(Date dt){


        if(dt != null) {
            TimeZone timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            sdf.setTimeZone(timeZone);

            return sdf.format(dt);
        }
        return "";
    }

    public String displayTime(Date dt){

        if(dt != null) {
            TimeZone timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
            sdf.setTimeZone(timeZone);

            return sdf.format(dt);
        }
        return "";
    }

    public void updateFromForm(TicketForm form) throws Exception {

        this.summary = form.getSummary();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm", Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());

        try{
            this.reportedAt = sdf.parse(form.getReportedAtDate() + " " + form.getReportedAtTime());
        }
        catch(Exception e){
            throw new Exception("Exception while parsing ticket date(s): Unable to parse " + form.getReportedAtDate() + " " + form.getReportedAtTime());
        }


        this.attachmentOpened = form.isAttachmentOpened();
        if(attachmentOpened) {
            try {
                this.attachmentOpenedAt = sdf.parse(form.getAttachmentOpenedAtDate() + " " + form.getAttachmentOpenedAtTime());
            } catch (Exception e) {
                throw new Exception("Exception while parsing ticket date(s): Unable to parse " + form.getAttachmentOpenedAtDate() + " " + form.getAttachmentOpenedAtTime());
            }
        }

        this.credentialsEntered = form.isCredentialsEntered();

        if(credentialsEntered) {
            try {
                this.credentialsEnteredAt = sdf.parse(form.getCredentialsEnteredAtDate() + " " + form.getCredentialsEnteredAtTime());
            } catch (Exception e) {
                throw new Exception("Exception while parsing ticket date(s): Unable to parse " + form.getCredentialsEnteredAtDate() + " " + form.getCredentialsEnteredAtTime());
            }
        }

        this.linkClicked = form.isLinkClicked();
        if(linkClicked) {
            try {
                this.linkClickedAt = sdf.parse(form.getLinkClickedAtDate() + " " + form.getLinkClickedAtTime());
            } catch (Exception e) {
                throw new Exception("Exception while parsing ticket date(s): Unable to parse " + form.getLinkClickedAtDate() + " " + form.getLinkClickedAtTime());
            }
        }

        links = form.getLinks();

        attachments = form.getAttachments();

        srcIP = IPTrace.get(form.getSrcID());

        this.ipSAPConfigured = form.isSAPDefined();
        this.arinLocation = form.getArin();
        this.ipSpoofed = form.isSpoofed();

        this.pwdResetRequested = form.isPwdResetRequested();
        this.senderBlockRequested = form.isSenderBlockRequested();
        this.ironportRequested = form.isSenderBlockRequested();
        this.sapSpamRequested = form.isSenderBlockRequested();
    }
}
