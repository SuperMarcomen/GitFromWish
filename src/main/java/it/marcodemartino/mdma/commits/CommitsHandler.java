package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.entities.*;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.reconstructors.*;
import it.marcodemartino.mdma.visitors.EntityVisitor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CommitsHandler {

    private final Map<String, Commit> commits;
    private final CommitBuilder commitBuilder;
    private final ReferenceTracker referenceTracker;
    private final CommitReconstructor commitReconstructor;
    private final FileReader fileReader;
    private final EntityVisitor restoreEntitiesVisitor;

    public CommitsHandler(CommitBuilder commitBuilder, FileReader fileReader, EntityVisitor restoreEntitiesVisitor) {
        this.commits = new HashMap<>();
        this.commitBuilder = commitBuilder;
        this.referenceTracker = new ReferenceTracker();
        this.restoreEntitiesVisitor = restoreEntitiesVisitor;
        Reconstructor<Blob> blobReconstructor = new BlobReconstructor(fileReader, referenceTracker);
        Reconstructor<Tree> treeReconstructor = new TreeReconstructor(fileReader, referenceTracker, blobReconstructor);
        this.commitReconstructor = new CommitReconstructor(fileReader, referenceTracker, treeReconstructor);
        this.fileReader = fileReader;
    }

    public void restoreCommit(Commit commit) {
        commit.accept(restoreEntitiesVisitor);
    }

    public void restoreCommit(String hash) {
        Commit commit = commits.get(hash);
        restoreCommit(commit);
    }

    public void loadCommits() {
        referenceTracker.load(fileReader);
        Set<Path> commitFiles = fileReader.getFilesFromFolder(FolderNames.COMMIT.getFolderName());
        for (Path commitFile : commitFiles) {
            Commit commit = commitReconstructor.reconstruct(commitFile.getFileName().toString());
            commits.put(commit.getName(), commit);
        }
    }

    public Commit newCommit(String author) {
        commitBuilder.setAuthor(author);
        Commit commit = commitBuilder.build(Paths.get(""));
        referenceTracker.addTree(commit.getMainTree().getName(), commit.getMainTree());
        return commit;
    }

    public Map<String, Commit> getCommits() {
        return commits;
    }
}
