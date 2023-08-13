package it.marcodemartino.mdma.builders;

import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.encryption.SHA1Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.DiskFileReader;
import it.marcodemartino.mdma.io.FileReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

class CommitBuilderTest {

    static CommitBuilder commitBuilder;

    @BeforeAll
    static void init() {
        FileReader fileReader = new DiskFileReader();
        Hashing hashing = new SHA1Hashing();
        Builder<Blob> blobBuilder = new BlobBuilder(fileReader, hashing);
        Builder<Tree> treeBuilder = new TreeBuilder(fileReader, hashing, blobBuilder);
        commitBuilder = new CommitBuilder(fileReader, hashing, treeBuilder);
    }

    @Test
    void build() {
        commitBuilder.setAuthor("Marco");
        Commit commit = commitBuilder.build(Paths.get(""));
        System.out.println(commit.print());
    }
}