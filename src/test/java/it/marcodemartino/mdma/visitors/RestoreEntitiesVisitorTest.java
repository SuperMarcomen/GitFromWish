package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.io.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RestoreEntitiesVisitorTest {

    static EntityVisitor restoreEntitiesVisitor;

    @BeforeAll
    static void init() {
        FileWriter fileWriter = new DiskFileWriter(true);
        FileReader fileReader = new DiskFileReader();
        restoreEntitiesVisitor = new RestoreEntitiesVisitor(fileWriter, fileReader);
    }

    @Test
    void restore() {

    }

}