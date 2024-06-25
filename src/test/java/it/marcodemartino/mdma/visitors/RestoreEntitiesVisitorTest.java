package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.builders.BlobBuilder;
import it.marcodemartino.mdma.builders.Builder;
import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.builders.TreeBuilder;
import it.marcodemartino.mdma.commits.CommitsHandler;
import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.encryption.SHA1Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RestoreEntitiesVisitorTest {

    static EntityVisitor restoreEntitiesVisitor;
    static CommitsHandler commitsHandler;

    @BeforeAll
    static void init() {
        FileWriter fileWriter = new DiskFileWriter(true);
        FileReader fileReader = new DiskFileReader();
        restoreEntitiesVisitor = new RestoreEntitiesVisitor(fileWriter, fileReader);

        Hashing hashing = new SHA1Hashing();
        Builder<Blob> blobBuilder = new BlobBuilder(fileReader, hashing);
        Builder<Tree> treeBuilder = new TreeBuilder(fileReader, hashing, blobBuilder);
        CommitBuilder commitBuilder = new CommitBuilder(fileReader, hashing, treeBuilder);
        commitsHandler = new CommitsHandler(commitBuilder, new ReferenceTracker(), fileReader);
        commitsHandler.loadCommits();
    }

    @Test
    void restore() throws IOException {
        String commitHash = getCommitHash().getFileName().toString();
        Commit commit = commitsHandler.getCommitFromHash(commitHash);
        restoreEntitiesVisitor.visit(commit);
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