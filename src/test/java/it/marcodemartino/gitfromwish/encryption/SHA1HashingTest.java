package it.marcodemartino.gitfromwish.encryption;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA1HashingTest {

    @Test
    void hash() {
        Hashing hashing = new SHA1Hashing();
        assertEquals("40bd001563085fc35165329ea1ff5c5ecbdbbeef", hashing.hash("123".getBytes()));
    }
}