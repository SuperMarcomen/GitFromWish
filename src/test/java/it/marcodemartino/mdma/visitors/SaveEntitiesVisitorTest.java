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
import it.marcodemartino.mdma.repository.DiskRepositoryManager;
import it.marcodemartino.mdma.repository.RepositoryManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SaveEntitiesVisitorTest {

    static SaveEntitiesVisitor saveEntitiesVisitor;
    static CommitBuilder commitBuilder;
    static ReferenceTracker referenceTracker;

    @BeforeAll
    static void init() throws IOException {
        RepositoryManager repositoryManager = new DiskRepositoryManager();
        repositoryManager.createRepository();
        Files.walk(Paths.get("MDMA"))
                .map(Path::toFile)
                .forEach(File::delete);

        FileReader fileReader = new DiskFileReader();
        Hashing hashing = new SHA1Hashing();
        Builder<Blob> blobBuilder = new BlobBuilder(fileReader, hashing);
        Builder<Tree> treeBuilder = new TreeBuilder(fileReader, hashing, blobBuilder);
        commitBuilder = new CommitBuilder(fileReader, hashing, treeBuilder);
        referenceTracker = new ReferenceTracker();

        FileWriter fileWriter = new DiskFileWriter(true);
        saveEntitiesVisitor = new SaveEntitiesVisitor(fileWriter, fileReader);
    }

    @Test
    void visitRealCommit() {
        // The visit of blobs and trees is automatically tested when visiting commits
        commitBuilder.setAuthor("Marco");
        Commit realCommit = commitBuilder.build(Paths.get(""));

        ReferenceTracker realReferenceTracker = new ReferenceTracker();
        realReferenceTracker.addTree(realCommit.getMainTree().getName(), realCommit.getMainTree());

        realCommit.accept(saveEntitiesVisitor);
        realReferenceTracker.getReferences().accept(saveEntitiesVisitor);
    }
}