package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.reconstructors.BlobReconstructor;
import it.marcodemartino.mdma.reconstructors.CommitReconstructor;
import it.marcodemartino.mdma.reconstructors.Reconstructor;
import it.marcodemartino.mdma.reconstructors.TreeReconstructor;
import it.marcodemartino.mdma.visitors.RestoreEntitiesVisitor;
import it.marcodemartino.mdma.visitors.SaveEntitiesVisitor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommitsHandler {

    private final Map<String, Commit> commits;
    private final CommitBuilder commitBuilder;
    private final SaveEntitiesVisitor saveEntityVisitor;
    private final ReferenceTracker referenceTracker;
    private final CommitReconstructor commitReconstructor;
    private final FileReader fileReader;
    private final RestoreEntitiesVisitor restoreEntitiesVisitor;

    public CommitsHandler(CommitBuilder commitBuilder, SaveEntitiesVisitor saveEntityVisitor, FileReader fileReader, RestoreEntitiesVisitor restoreEntitiesVisitor) {
        this.commits = new HashMap<>();
        this.commitBuilder = commitBuilder;
        this.saveEntityVisitor = saveEntityVisitor;
        this.referenceTracker = new ReferenceTracker();
        this.restoreEntitiesVisitor = restoreEntitiesVisitor;
        Reconstructor<Blob> blobReconstructor = new BlobReconstructor(fileReader, referenceTracker);
        Reconstructor<Tree> treeReconstructor = new TreeReconstructor(fileReader, referenceTracker, blobReconstructor);
        this.commitReconstructor = new CommitReconstructor(fileReader, referenceTracker, treeReconstructor);
        this.fileReader = fileReader;
    }

    public void restoreCommit(String hash) {
        Commit commit = commits.get(hash);
        commit.accept(restoreEntitiesVisitor);
    }

    public void loadCommits() {
        referenceTracker.load(fileReader);
        Set<Path> commitFiles = fileReader.getFilesFromFolder(FolderNames.COMMIT.getFolderName());
        for (Path commitFile : commitFiles) {
            Commit commit = commitReconstructor.reconstruct(commitFile.getFileName().toString());
            commits.put(commit.getName(), commit);
        }
    }

    public void newCommit(String author) {
        commitBuilder.setAuthor(author);
        Commit commit = commitBuilder.build(Paths.get(""));
        referenceTracker.addTree(commit.getMainTree().getName(), commit.getMainTree());
        commit.accept(saveEntityVisitor);
        referenceTracker.getReferences().accept(saveEntityVisitor);
    }

    public Map<String, Commit> getCommits() {
        return commits;
    }
}
