package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.encryption.SHA1Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.DiskFileWriter;
import it.marcodemartino.mdma.io.FileWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class SaveEntitiesVisitorTest {

    static SaveEntitiesVisitor saveEntitiesVisitor;
    static Blob item;
    static Blob subitem;
    static Tree folder;
    static Tree subfolder;
    static Commit commit;

    @BeforeAll
    static void init() throws IOException {
        Files.walk(Paths.get("MDMA"))
                .map(Path::toFile)
                .forEach(File::delete);

        Hashing hashing = new SHA1Hashing();
        String content1 = "mammt, fratt e sort";
        String content2 = "ich mehr";
        item = new Blob(hashing.hash(content1.getBytes()), content1.getBytes());
        subitem = new Blob(hashing.hash(content2.getBytes()), content2.getBytes());
        // I'm sorry... This is a war crime, but it's late and I need to test this
        subfolder = new Tree(
                Tree.generateHash(hashing, List.of(subitem), Collections.emptyList()),
                List.of(subitem),
                Collections.emptyList()
        );
        folder = new Tree(
                Tree.generateHash(hashing, List.of(item), List.of(subfolder)),
                List.of(item),
                List.of(subfolder)
        );
        commit = new Commit(
                Commit.generateHash(hashing, "Marco", LocalDateTime.now(), folder),
                folder,
                "Marco",
                LocalDateTime.now()
        );
        FileWriter fileWriter = new DiskFileWriter();
        saveEntitiesVisitor = new SaveEntitiesVisitor(fileWriter);

    }


    @Test
    void visitCommit() {
        // The visit of blobs and trees is automatically tested when visiting commits
        saveEntitiesVisitor.visit(commit);
    }
}