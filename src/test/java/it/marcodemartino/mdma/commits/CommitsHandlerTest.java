package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.builders.BlobBuilder;
import it.marcodemartino.mdma.builders.Builder;
import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.builders.TreeBuilder;
import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.encryption.SHA1Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.DiskFileReader;
import it.marcodemartino.mdma.io.DiskFileWriter;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.io.FileWriter;
import it.marcodemartino.mdma.visitors.RestoreEntitiesVisitor;
import it.marcodemartino.mdma.visitors.SaveEntitiesVisitor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CommitsHandlerTest {

    static CommitsHandler commitsHandler;

    @BeforeAll
    static void init() {
        FileReader fileReader = new DiskFileReader();
        FileWriter fileWriter = new DiskFileWriter();
        Hashing hashing = new SHA1Hashing();

        Builder<Blob> blobBuilder = new BlobBuilder(fileReader, hashing);
        Builder<Tree> treeBuilder = new TreeBuilder(fileReader, hashing, blobBuilder);
        CommitBuilder commitBuilder = new CommitBuilder(fileReader, hashing, treeBuilder);

        SaveEntitiesVisitor saveEntitiesVisitor = new SaveEntitiesVisitor(fileWriter, fileReader);
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