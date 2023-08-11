package it.marcodemartino.gitfromwish.builders;

import it.marcodemartino.gitfromwish.encryption.Hashing;
import it.marcodemartino.gitfromwish.encryption.SHA1Hashing;
import it.marcodemartino.gitfromwish.entities.Blob;
import it.marcodemartino.gitfromwish.entities.Commit;
import it.marcodemartino.gitfromwish.entities.Tree;
import it.marcodemartino.gitfromwish.io.DiskFileReader;
import it.marcodemartino.gitfromwish.io.FileReader;
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