package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.builders.BlobBuilder;
import it.marcodemartino.mdma.builders.Builder;
import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.builders.TreeBuilder;
import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.encryption.SHA1Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.DiskFileReader;
import it.marcodemartino.mdma.io.DiskFileWriter;
import it.marcodemartino.mdma.io.FileReader;
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
    static CommitBuilder commitBuilder;
    static ReferenceTracker referenceTracker;

    @BeforeAll
    static void init() throws IOException {
        Files.walk(Paths.get("MDMA"))
                .map(Path::toFile)
                .forEach(File::delete);

        FileReader fileReader = new DiskFileReader();
        Hashing hashing = new SHA1Hashing();
        Builder<Blob> blobBuilder = new BlobBuilder(fileReader, hashing);
        Builder<Tree> treeBuilder = new TreeBuilder(fileReader, hashing, blobBuilder);
        commitBuilder = new CommitBuilder(fileReader, hashing, treeBuilder);

        String content1 = "mammt, fratt e sort";
        String content2 = "ich mehr";
        item = new Blob(hashing.hash(content1.getBytes()), content1.getBytes(), "folder1/file1.txt");
        subitem = new Blob(hashing.hash(content2.getBytes()), content2.getBytes(), "folder2/file2.txt");
        // I'm sorry... This is a war crime, but it's late and I need to test this
        subfolder = new Tree(
                Tree.generateHash(hashing, List.of(subitem), Collections.emptyList()),
                "folder2",
                List.of(subitem),
                Collections.emptyList()
        );
        folder = new Tree(
                Tree.generateHash(hashing, List.of(item), List.of(subfolder)),
                "folder1",
                List.of(item),
                List.of(subfolder)
        );
        commit = new Commit(
                Commit.generateHash(hashing, "Marco", LocalDateTime.of(2003, 11, 23, 11, 45), folder),
                folder,
                "Marco",
                LocalDateTime.of(2003, 11, 23, 11, 45)
        );

        referenceTracker = new ReferenceTracker();
        referenceTracker.addTree(commit.getMainTree().getName(), commit.getMainTree());

        FileWriter fileWriter = new DiskFileWriter();
        saveEntitiesVisitor = new SaveEntitiesVisitor(fileWriter);
    }


    @Test
    void visitRealCommit() {
        // The visit of blobs and trees is automatically tested when visiting commits
        Commit realCommit = commitBuilder.build(Paths.get(""));

        ReferenceTracker realReferenceTracker = new ReferenceTracker();
        realReferenceTracker.addTree(realCommit.getMainTree().getName(), realCommit.getMainTree());

        realCommit.accept(saveEntitiesVisitor);
        realReferenceTracker.getReferences().accept(saveEntitiesVisitor);
    }

    @Test
    void visitConstructedCommit() {
        // The visit of blobs and trees is automatically tested when visiting commits
        commit.accept(saveEntitiesVisitor);
        referenceTracker.getReferences().accept(saveEntitiesVisitor);
    }
}