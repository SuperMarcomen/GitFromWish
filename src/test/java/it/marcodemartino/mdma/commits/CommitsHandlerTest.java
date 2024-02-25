package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.builders.*;
import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.encryption.SHA1Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.*;
import it.marcodemartino.mdma.visitors.RestoreEntitiesVisitor;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CommitsHandlerTest {

    static CommitsHandler commitsHandler;

    @BeforeAll
    static void init() {
        FileReader fileReader = new DiskFileReader();
        FileWriter fileWriter = new DiskFileWriter(true);
        Hashing hashing = new SHA1Hashing();

        Builder<Blob> blobBuilder = new BlobBuilder(fileReader, hashing);
        Builder<Tree> treeBuilder = new TreeBuilder(fileReader, hashing, blobBuilder);
        CommitBuilder commitBuilder = new CommitBuilder(fileReader, hashing, treeBuilder);

        RestoreEntitiesVisitor restoreEntitiesVisitor = new RestoreEntitiesVisitor(fileWriter, fileReader);

        commitsHandler = new CommitsHandler(commitBuilder, fileReader, restoreEntitiesVisitor);
    }

    @Test
    @Order(1)
    void loadCommits() {
        commitsHandler.loadCommits();
        assertFalse(commitsHandler.getCommits().isEmpty());
    }

    @Test
    @Order(2)
    void restoreCommit() {
        commitsHandler.restoreCommit("a670117bec77257dc0f35cd5f3b6142499c0a575");
    }
}