package it.marcodemartino.gitfromwish.builders;

import it.marcodemartino.gitfromwish.encryption.Hashing;
import it.marcodemartino.gitfromwish.entities.Blob;
import it.marcodemartino.gitfromwish.entities.Tree;
import it.marcodemartino.gitfromwish.io.FileReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TreeBuilder extends Builder<Tree> {

    private final Builder<Blob> blobBuilder;

    public TreeBuilder(FileReader fileReader, Hashing hashing, Builder<Blob> blobBuilder) {
        super(fileReader, hashing);
        this.blobBuilder = blobBuilder;
    }

    @Override
    Tree build(Path path) {
        Set<Path> files = fileReader.getFilesFromFolder(path);
        List<Blob> blobs = new ArrayList<>();
        List<Tree> subTrees = new ArrayList<>();

        // the order of files should be always the same (?). Hopefully
        // otherwise shit goes south (hash is different even though the files are the same)
        for (Path file : files) {
            if (Files.isDirectory(file)) {
                subTrees.add(build(file));
            } else {
                blobs.add(blobBuilder.build(file));
            }
        }

        String hash = generateHash(blobs, subTrees);
        return new Tree(hash, blobs, subTrees);
    }

    private String generateHash(List<Blob> blobs, List<Tree> subTrees) {
        Tree tempTree = new Tree("", blobs, subTrees);
        String hashesString = tempTree.print();
        return hashing.hash(hashesString.getBytes());
    }
}
