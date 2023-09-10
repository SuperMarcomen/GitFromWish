package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.io.DiskFileWriter;
import it.marcodemartino.mdma.io.FileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RestoreEntitiesVisitorTest {

    static EntityVisitor restoreEntitiesVisitor;

    @BeforeAll
    static void init() {
        FileWriter fileWriter = new DiskFileWriter();
        restoreEntitiesVisitor = new RestoreEntitiesVisitor(fileWriter);
    }

    @Test
    void restore() {

    }

}