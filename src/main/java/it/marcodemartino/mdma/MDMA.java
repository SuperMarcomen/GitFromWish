package it.marcodemartino.mdma;

import it.marcodemartino.mdma.builders.BlobBuilder;
import it.marcodemartino.mdma.builders.Builder;
import it.marcodemartino.mdma.builders.CommitBuilder;
import it.marcodemartino.mdma.builders.TreeBuilder;
import it.marcodemartino.mdma.commits.CommitsHandler;
import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.visitors.EntityVisitor;
import java.nio.file.Path;

public class MDMA {

  private final EntityVisitor restoreEntitiesVisitor;
  private final EntityVisitor saveEntitiesVisitor;
  private final CommitsHandler commitsHandler;
  private final ReferenceTracker referenceTracker;

  public MDMA(EntityVisitor restoreEntitiesVisitor, EntityVisitor saveEntitiesVisitor, FileReader fileReader,
              Hashing hashing) {
    this.restoreEntitiesVisitor = restoreEntitiesVisitor;
    this.saveEntitiesVisitor = saveEntitiesVisitor;

    Builder<Blob> blobBuilder = new BlobBuilder(fileReader, hashing);
    Builder<Tree> treeBuilder = new TreeBuilder(fileReader, hashing, blobBuilder);
    CommitBuilder commitBuilder = new CommitBuilder(fileReader, hashing, treeBuilder);
    referenceTracker = new ReferenceTracker();
    commitsHandler = new CommitsHandler(commitBuilder, referenceTracker, fileReader);
  }

  public Commit newCommit(String author, Path path) {
    return commitsHandler.newCommit(author, path);
  }

  public void saveCommit(Commit commit) {
    saveEntitiesVisitor.visit(referenceTracker.getReferences());
    saveEntitiesVisitor.visit(commit);
  }

  public void restoreCommit(String hash) {
    Commit commit = commitsHandler.getCommitFromHash(hash);
    restoreCommit(commit);
  }

  public void restoreCommit(Commit commit) {
    commit.accept(restoreEntitiesVisitor);
  }
}
