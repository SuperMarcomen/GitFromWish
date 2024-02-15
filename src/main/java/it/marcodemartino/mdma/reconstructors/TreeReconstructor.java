package it.marcodemartino.mdma.reconstructors;

import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TreeReconstructor extends Reconstructor<Tree> {

    private final Reconstructor<Blob> blobReconstructor;

    public TreeReconstructor(FileReader fileReader, ReferenceTracker referenceTracker, Reconstructor<Blob> blobReconstructor) {
        super(fileReader, referenceTracker);
        this.blobReconstructor = blobReconstructor;
    }

    @Override
    public Tree reconstruct(String hash) {
        String path = referenceTracker.getObjectPath(hash);
        String content = fileReader.readFileAsString(Paths.get(FolderNames.TREE.getFolderName().toString(), hash));
        String[] lines = content.split(System.lineSeparator());
        List<Blob> blobs = new ArrayList<>();
        List<Tree> trees = new ArrayList<>();

        for (String line : lines) {
            String[] args = line.split(":");
            System.out.println(line);
            switch (args[0]) {
                case "blob" -> blobs.add(blobReconstructor.reconstruct(args[1]));
                case "tree" -> trees.add(reconstruct(args[1]));
                default -> System.err.println("Unrecognized file type in tree: " + hash + ". It was: " + args[1]);
            }
        }
        return new Tree(hash, path, blobs, trees);
    }
}
