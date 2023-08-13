package it.marcodemartino.mdma.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DiskFileReaderTest {

    @Test
    void getFilesFromFolder() {
        FileReader fileReader = new DiskFileReader();
        System.out.println(fileReader.getFilesFromWorkingFolder());
        assertFalse(fileReader.getFilesFromWorkingFolder().isEmpty());
    }
}