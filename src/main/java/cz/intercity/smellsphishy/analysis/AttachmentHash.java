package cz.intercity.smellsphishy.analysis;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * public class AttachmentHash
 *
 * Contains an attachment hash. Please note that the attachment itself is never stored anywhere for safety reasons.
 */
public class AttachmentHash {

    private String fileName;
    private byte[] hash;

    //Not going to store the attachment for now

    public AttachmentHash(String filename, byte[] AttachmentObject) throws NoSuchAlgorithmException{
        Logger log = LoggerFactory.getLogger(AttachmentHash.class);
        log.info("Hashing requested for file " + filename);

        //Using the default java crypto libs
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(AttachmentObject);

            byte[] digest = md.digest();

            this.hash = digest;
            this.fileName = filename;

            log.info("Attachment hash result: " + this.toString());
        }
        catch(NoSuchAlgorithmException e){
            throw(e);
        }

    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getHash() {
        return hash;
    }

    @Override
    public String toString(){
        BigInteger hashInteger = new BigInteger(1, hash);
        String hashText = hashInteger.toString(16);
        while (hashText.length() < 32) {
            hashText = "0" + hashText;
        }
        return hashText;
    }
}
