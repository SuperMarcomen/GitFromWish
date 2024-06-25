package it.marcodemartino.mdma.commits;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

        commitsHandler = new CommitsHandler(commitBuilder, new ReferenceTracker(), fileReader);
    }

    @Test
    void loadCommits() {
        commitsHandler.loadCommits();
        assertFalse(commitsHandler.getCommits().isEmpty());
    }
}