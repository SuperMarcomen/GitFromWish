package it.marcodemartino.gitfromwish.builders;

import it.marcodemartino.gitfromwish.encryption.Hashing;
import it.marcodemartino.gitfromwish.entities.Commit;
import it.marcodemartino.gitfromwish.entities.Tree;
import it.marcodemartino.gitfromwish.io.FileReader;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class CommitBuilder extends Builder<Commit> {

    private final Builder<Tree> treeBuilder;
    private String author;
    private LocalDateTime currentDateTime;

    public CommitBuilder(FileReader fileReader, Hashing hashing, Builder<Tree> treeBuilder) {
        super(fileReader, hashing);
        this.treeBuilder = treeBuilder;
        this.author = "";
        this.currentDateTime = LocalDateTime.now();
    }

    @Override
    Commit build(Path path) {
        Tree mainTree = treeBuilder.build(path);
        currentDateTime = LocalDateTime.now();
        return new Commit(generateHash(mainTree), mainTree, author, currentDateTime);
    }

    private String generateHash(Tree mainTree) {
        Commit tempCommit = new Commit("", mainTree, author, currentDateTime);
        String hashesString = tempCommit.print();
        return hashing.hash(hashesString.getBytes());
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
