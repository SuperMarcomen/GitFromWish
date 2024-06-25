package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.entities.*;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.reconstructors.*;

import java.nio.file.Path;
import java.util.*;

public class CommitsHandler {

    private final Map<String, Commit> commits;
    private final CommitBuilder commitBuilder;
    private final ReferenceTracker referenceTracker;
    private final CommitReconstructor commitReconstructor;
    private final FileReader fileReader;

    public CommitsHandler(CommitBuilder commitBuilder, ReferenceTracker referenceTracker, FileReader fileReader) {
        this.commits = new HashMap<>();
        this.commitBuilder = commitBuilder;
        this.referenceTracker = referenceTracker;
        Reconstructor<Blob> blobReconstructor = new BlobReconstructor(fileReader, referenceTracker);
        Reconstructor<Tree> treeReconstructor = new TreeReconstructor(fileReader, referenceTracker, blobReconstructor);
        this.commitReconstructor = new CommitReconstructor(fileReader, referenceTracker, treeReconstructor);
        this.fileReader = fileReader;

        referenceTracker.load(fileReader);
    }

    public void loadCommits() {
        Set<Path> commitFiles = fileReader.getFilesFromFolder(FolderNames.COMMIT.getFolderName());
        for (Path commitFile : commitFiles) {
            Commit commit = commitReconstructor.reconstruct(commitFile.getFileName().toString());
            commits.put(commit.getName(), commit);
        }
    }

    public Commit newCommit(String author, Path path) {
        commitBuilder.setAuthor(author);
        Commit commit = commitBuilder.build(path);
        referenceTracker.addTree(commit.getMainTree().getName(), commit.getMainTree());
        return commit;
    }

    public Commit getCommitFromHash(String hash) {
        return commits.get(hash);
    }

    public Map<String, Commit> getCommits() {
        return commits;
    }
}
