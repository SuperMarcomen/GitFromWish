package it.marcodemartino.mdma.encryption;

import java.io.InputStream;

public interface Hashing {

    String hash(InputStream content);
    String hash(byte[] content);
}
