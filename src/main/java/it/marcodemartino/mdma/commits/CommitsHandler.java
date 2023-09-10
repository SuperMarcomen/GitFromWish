package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.visitors.EntityVisitor;

import java.nio.file.Paths;

public class CommitsHandler {

    private final CommitBuilder commitBuilder;
    private final EntityVisitor saveEntityVisitor;
    private final ReferenceTracker referenceTracker;

    public CommitsHandler(CommitBuilder commitBuilder, EntityVisitor saveEntityVisitor, ReferenceTracker referenceTracker) {
        this.commitBuilder = commitBuilder;
        this.saveEntityVisitor = saveEntityVisitor;
        this.referenceTracker = referenceTracker;
    }

    public void newCommit(String author) {
        commitBuilder.setAuthor(author);
        Commit commit = commitBuilder.build(Paths.get(""));
        referenceTracker.addTree(commit.getMainTree().getName(), commit.getMainTree());
        commit.accept(saveEntityVisitor);
    }
}
