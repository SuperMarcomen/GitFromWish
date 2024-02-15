package it.marcodemartino.mdma.encryption;

import it.marcodemartino.mdma.io.DiskFileReader;
import it.marcodemartino.mdma.io.FileReader;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SHA1HashingTest {

    @Test
    void hash() {
        Hashing hashing = new SHA1Hashing();
        assertEquals("40bd001563085fc35165329ea1ff5c5ecbdbbeef", hashing.hash(new ByteArrayInputStream("123".getBytes())));
    }

    @Test
    void bigFileHash() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory: " + usedMemoryBefore/1000000 + "MB");

        FileReader fileReader = new DiskFileReader();
        Hashing hashing = new SHA1Hashing();
        Files.walk(Paths.get("F:\\Film\\Fast and Furious\\")).forEach(path -> {
            if (Files.isDirectory(path)) return;
            System.out.println("Reading and hashing: " + path.getFileName());
            hashing.hash(fileReader.readFile(path));
            long usedMemoryBefore1 = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used Memory: " + usedMemoryBefore1/1000000 + "MB");
        });
    }
}