package it.marcodemartino.mdma.builders;

import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.entities.*;
import it.marcodemartino.mdma.io.FileReader;

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
    public Tree build(Path path) {
        Set<Path> files = fileReader.getFilesFromFolder(path);
        List<Blob> blobs = new ArrayList<>();
        List<Tree> subTrees = new ArrayList<>();

        // the order of files should be always the same (?). Hopefully
        // otherwise shit goes south (hash is different even though the files are the same)
        for (Path file : files) {
            if (Files.isDirectory(file)) {
                if (file.equals(FolderNames.MAIN.getFolderName())) continue;
                subTrees.add(build(file));
            } else {
                blobs.add(blobBuilder.build(file));
            }
        }

        return new Tree(Tree.generateHash(hashing, blobs, subTrees), path.toString(), blobs, subTrees);
    }
}
