package cz.intercity.smellsphishy.analysis.ticket;

import cz.intercity.smellsphishy.analysis.ticket.ipinfo.IPInfo;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketForm {

    public TicketForm() {
        links = new ArrayList<>();
        attachments = new ArrayList<>();
    }

    public TicketForm(Ticket ticket) {
        this.subject = ticket.getSubject();
        this.recipients = ticket.getRecipients();
        this.summary = ticket.getSummary();
        this.mailReceivedAt = ticket.displayReceivedAt();

        this.reportedAtDate = ticket.displayDate(ticket.getReportedAt());
        this.reportedAtTime = ticket.displayTime(ticket.getReportedAt());


        this.linkClicked = ticket.isLinkClicked();
        this.linkClickedAtDate = ticket.displayDate(ticket.getLinkClickedAt());
        this.linkClickedAtTime = ticket.displayTime(ticket.getLinkClickedAt());

        this.attachmentOpened = ticket.isAttachmentOpened();
        this.attachmentOpenedAtDate = ticket.displayDate(ticket.getAttachmentOpenedAt());
        this.attachmentOpenedAtTime = ticket.displayTime(ticket.getAttachmentOpenedAt());

        this.credentialsEntered = ticket.isCredentialsEntered();
        this.credentialsEnteredAtDate = ticket.displayDate(ticket.getCredentialsEnteredAt());
        this.credentialsEnteredAtTime = ticket.displayTime(ticket.getCredentialsEnteredAt());

        this.links = ticket.getLinks();
        this.attachments = ticket.getAttachments();

        this.ipTrace = ticket.getIPTrace();
        this.srcID = ipTrace.get(0).getId();

        this.SAPDefined = ticket.isIpSAPConfigured();
        this.spoofed = ticket.isIpSpoofed();

        this.arin = ticket.getArinLocation();

        this.pwdResetRequested = ticket.isPwdResetRequested();
        this.senderBlockRequested = ticket.isSenderBlockRequested();
    }

    private String subject;
    private String recipients;

    @NotNull(message = "Field is required")
    private String summary;
    private String mailReceivedAt;

    @NotNull(message = "Field is required")
    @Pattern(regexp = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$|", message = "Reported At: Invalid date format")
    private String reportedAtDate;

    @NotNull(message = "Field is required")
    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]$|", message = "Reported At: Invalid time format")
    private String reportedAtTime;

    private boolean linkClicked;

    @Pattern(regexp = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$|", message = "Link Clicked: Invalid date format")
    private String linkClickedAtDate;

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]$|", message = "Link clicked: Invalid time format")
    private String linkClickedAtTime;

    private boolean attachmentOpened;

    @Pattern(regexp = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$|", message = "Attachment opened: Invalid date format")
    private String attachmentOpenedAtDate;

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]$|", message = "Attachment opened: Invalid time format")
    private String attachmentOpenedAtTime;

    private boolean credentialsEntered;

    @Pattern(regexp = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$|", message = "Credentials entered: Invalid date format")
    private String credentialsEnteredAtDate;

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]$|", message = "Credentials entered: Invalid time format")
    private String credentialsEnteredAtTime;

    private List<LinkInfo> links;
    private List<AttachmentInfo> attachments;

    private List<IPInfo> ipTrace;

    private int srcID;
    private boolean SAPDefined;
    private boolean spoofed;
    private String arin;

    private boolean pwdResetRequested;
    private boolean senderBlockRequested;

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

    public String getMailReceivedAt() {
        return mailReceivedAt;
    }

    public void setMailReceivedAt(String mailReceivedAt) {
        this.mailReceivedAt = mailReceivedAt;
    }

    public String getReportedAtDate() {
        return reportedAtDate;
    }

    public void setReportedAtDate(String reportedAtDate) {
        this.reportedAtDate = reportedAtDate;
    }

    public String getReportedAtTime() {
        return reportedAtTime;
    }

    public void setReportedAtTime(String reportedAtTime) {
        this.reportedAtTime = reportedAtTime;
    }

    public String getLinkClickedAtDate() {
        return linkClickedAtDate;
    }

    public void setLinkClickedAtDate(String linkClickedAtDate) {
        this.linkClickedAtDate = linkClickedAtDate;
    }

    public String getLinkClickedAtTime() {
        return linkClickedAtTime;
    }

    public void setLinkClickedAtTime(String linkClickedAtTime) {
        this.linkClickedAtTime = linkClickedAtTime;
    }

    public String getAttachmentOpenedAtDate() {
        return attachmentOpenedAtDate;
    }

    public void setAttachmentOpenedAtDate(String attachmentOpenedAtDate) {
        this.attachmentOpenedAtDate = attachmentOpenedAtDate;
    }

    public String getAttachmentOpenedAtTime() {
        return attachmentOpenedAtTime;
    }

    public void setAttachmentOpenedAtTime(String attachmentOpenedAtTime) {
        this.attachmentOpenedAtTime = attachmentOpenedAtTime;
    }

    public String getCredentialsEnteredAtDate() {
        return credentialsEnteredAtDate;
    }

    public void setCredentialsEnteredAtDate(String credentialsEnteredAtDate) {
        this.credentialsEnteredAtDate = credentialsEnteredAtDate;
    }

    public String getCredentialsEnteredAtTime() {
        return credentialsEnteredAtTime;
    }

    public void setCredentialsEnteredAtTime(String credentialsEnteredAtTime) {
        this.credentialsEnteredAtTime = credentialsEnteredAtTime;
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

    public int getSrcID() {
        return srcID;
    }

    public void setSrcID(int srcID) {
        this.srcID = srcID;
    }

    public boolean isSAPDefined() {
        return SAPDefined;
    }

    public void setSAPDefined(boolean SAPDefined) {
        this.SAPDefined = SAPDefined;
    }

    public boolean isSpoofed() {
        return spoofed;
    }

    public void setSpoofed(boolean spoofed) {
        this.spoofed = spoofed;
    }

    public String getArin() {
        return arin;
    }

    public void setArin(String arin) {
        this.arin = arin;
    }

    public boolean ispwdResetRequested() {
        return pwdResetRequested;
    }

    public void setpwdResetRequested(boolean pwdResetRequested) {
        this.pwdResetRequested = pwdResetRequested;
    }

    public boolean isSenderBlockRequested() {
        return senderBlockRequested;
    }

    public void setSenderBlockRequested(boolean senderBlockRequested) {
        this.senderBlockRequested = senderBlockRequested;
    }

    public List<IPInfo> getIpTrace() {
        return ipTrace;
    }

    public void setIpTrace(List<IPInfo> ipTrace) {
        this.ipTrace = ipTrace;
    }

    public boolean isPwdResetRequested() {
        return pwdResetRequested;
    }

    public void setPwdResetRequested(boolean pwdResetRequested) {
        this.pwdResetRequested = pwdResetRequested;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public boolean isLinkClicked() {
        return linkClicked;
    }

    public void setLinkClicked(boolean linkClicked) {
        this.linkClicked = linkClicked;
    }

    public boolean isAttachmentOpened() {
        return attachmentOpened;
    }

    public void setAttachmentOpened(boolean attachmentOpened) {
        this.attachmentOpened = attachmentOpened;
    }

    public boolean isCredentialsEntered() {
        return credentialsEntered;
    }

    public void setCredentialsEntered(boolean credentialsEntered) {
        this.credentialsEntered = credentialsEntered;
    }

}

