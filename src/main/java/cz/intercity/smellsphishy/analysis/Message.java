package cz.intercity.smellsphishy.analysis;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hsmf.*;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <strong>class Message</strong>
 * <small>cz.intercity.smellsphishy.analysis</small>
 * <p>
 * A representation of an e-mail message in a more structured, operation-friendly form.
 *
 * @author Intercity (Jakub Tetera)
 * @version 1.0.0
 */
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
    private List<AttachmentHash> attachmentHashes;

    /**
     * Message Constructor
     *
     * @param in an InputStream representation of the input file. The file should be in the .msg format. An IOException
     *           will be thrown if the message is not a valid .msg or if another error occurs during loading basic
     *           information.
     * @throws IOException
     */
    public Message(InputStream in) throws Exception {

        Logger log = LoggerFactory.getLogger(Message.class);

        this.links = new ArrayList<>();
        this.attachmentHashes = new ArrayList<>();

        //Load message from InputStream
        MAPIMessage mapiMessage;
        try {
            mapiMessage = new MAPIMessage(in);

        } catch (IOException e) {
            //Fatal, wrong format
            throw (e);
        }


        //Loading information
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


        //Load HTML or RTF body
        String body = "";
        try {


            //Attempt to load the HTML contents
            try {
                body = mapiMessage.getHtmlBody();
            } catch (ChunkNotFoundException htmlNotFound) {
                log.info("No valid HTML content found, attempting to load RTF body...");
                try {
                    body = mapiMessage.getRtfBody();
                    log.info("Successfully loaded RTF Body");
                }
                catch(ChunkNotFoundException e){
                    throw(e);
                }
            }

        } catch (ChunkNotFoundException cnfe) {
            //This is getting old
            log.error("Exception while loading link information: No valid HTML content found within e-mail: " + cnfe.getMessage());
        } finally {
            this.textBody = body;
        }

        try {
            this.msgDate = mapiMessage.getMessageDate();
        } catch (ChunkNotFoundException cnfe) {
            //Stahp
            this.msgDate = null;
        }

        /*
         *  Loading Headers
         *
         *  The way Apache POI (HSMF) handles headers is wonky, as it takes them by row. It is best to reassemble the
         *  header before performing analysis.
         */
        try {
            String[] headers = mapiMessage.getHeaders();

            StringBuilder sb = new StringBuilder();
            for (String row : headers) {
                sb.append(row).append("\n");
            }

            this.headerPlaintext = sb.toString();

            //Attempt header analysis immediately, discard empty headers
            if (!StringUtils.isBlank(headerPlaintext)) {
                this.header = new Header(headerPlaintext);
            }
        } catch (Exception e) {
            //Finally
            this.headerPlaintext = "";
            throw(e);
        }

        //Get links from e-mail body (Please work on .rtf...)
        Pattern linkPattern = Pattern.compile("(?<=(<a))(.)*?(href=['\"])([^'\"]+)(['\"])", Pattern.CASE_INSENSITIVE);
        Matcher linkMatcher = linkPattern.matcher(textBody);
        while (linkMatcher.find()) {
            Link newLink = new Link(linkMatcher.group(4));
            this.links.add(newLink);
        }

        //Load attachments & get their hashes. Attachments themselves are not stored anywhere.
        for (AttachmentChunks attachment : mapiMessage.getAttachmentFiles()) {

            String attachmentName = attachment.getAttachFileName().getValue();
            byte[] attachmentBytes = attachment.getEmbeddedAttachmentObject();

            /*
               getEmbeddedAttachmentObject() returns null if the attachment is a valid .msg file.
               In those cases, the attachment will be skipped and an INFO message will be logged.
               See https://poi.apache.org/apidocs/dev/org/apache/poi/hsmf/datatypes/AttachmentChunks.html for details
            */
            if (attachmentBytes != null) {
                try {
                    AttachmentHash hash = new AttachmentHash(attachmentName, attachmentBytes);
                    attachmentHashes.add(hash);
                } catch (NoSuchAlgorithmException e) {
                    log.warn("Exception while generating hash for attachment " + attachmentName + ": " + e.getMessage());
                }
            }
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

    public List<AttachmentHash> getAttachmentHashes() {
        return attachmentHashes;
    }

    //Debug printout - DO NOT ACTUALLY USE
    public void debug() {

        System.out.println(" - - - BEGIN MESSAGE DEBUG INFORMATION - - - ");
        System.out.println("To:" + this.to);
        System.out.println("From:" + this.from);
        System.out.println("Body:");
        System.out.println(this.textBody);

        System.out.println("--------------------------");
        System.out.println("Headers:");
        System.out.println(headerPlaintext);
        System.out.println(" - - - END MESSAGE DEBUG INFORMATION - - - ");

    }


}