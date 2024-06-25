package it.marcodemartino.mdma.commits;

import static org.junit.jupiter.api.Assertions.assertFalse;

import it.marcodemartino.mdma.builders.BlobBuilder;
import it.marcodemartino.mdma.builders.Builder;
import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.builders.TreeBuilder;
import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.encryption.SHA1Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.DiskFileReader;
import it.marcodemartino.mdma.io.DiskFileWriter;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.io.FileWriter;
import it.marcodemartino.mdma.visitors.RestoreEntitiesVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
    void restoreCommit() throws IOException {
        String commitHash = getCommitHash().getFileName().toString();
        commitsHandler.restoreCommit(commitHash);
    }

    private Path getCommitHash() throws IOException {
        try (Stream<Path> stream = Files.list(FolderNames.COMMIT.getFolderName())) {
            return stream
                .filter(file -> !Files.isDirectory(file))
                .findFirst()
                .orElseThrow();
        }
    }
}