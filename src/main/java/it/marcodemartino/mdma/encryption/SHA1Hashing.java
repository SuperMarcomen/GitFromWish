package it.marcodemartino.mdma.encryption;

import it.marcodemartino.mdma.logger.Logger;
import it.marcodemartino.mdma.utils.HexConverter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class SHA1Hashing implements Hashing {

    @Override
    public String hash(InputStream content) {
        String checksum = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[8192];
            int numOfBytesRead;
            while( (numOfBytesRead = content.read(buffer)) > 0){
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checksum = HexConverter.encodeHexString(hash);
        } catch (Exception e) {
            Logger.error("Hashing algorithm SHA-1 not found!");
        }
        return checksum;
    }



    @Override
    public String hash(byte[] content) {
        return hash(new ByteArrayInputStream(content));
    }


}
