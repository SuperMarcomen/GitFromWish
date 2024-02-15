package it.marcodemartino.mdma.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReferencesTest {

    static References references;

    @BeforeAll
    static void init() {
        references = new References();
        references.addObject("ujdseb8767h", "a/path");
        references.addObject("kjhndjhsb876bhj", "another/path");
        references.addObject("ngulasort", "yet_another/path");
    }

    @Test
    void testToString() {
        String expected = """
                ujdseb8767h:a/path
                kjhndjhsb876bhj:another/path
                ngulasort:yet_another/path""";
        assertEquals(normalizeLineEnds(expected), normalizeLineEnds(references.print()));
        System.out.println(references);
    }

    private String normalizeLineEnds(String string) {
        return string.replace("\r\n", "\n").replace('\r', '\n');
    }
}