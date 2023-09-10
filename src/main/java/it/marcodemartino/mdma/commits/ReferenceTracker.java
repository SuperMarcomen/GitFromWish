package it.marcodemartino.mdma.commits;

import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.References;
import it.marcodemartino.mdma.entities.Tree;

public class ReferenceTracker {

    private final References references;

    public ReferenceTracker() {
        references = new References();
    }

    public void addBlob(String hash, Blob blob) {
        references.addObject(hash, blob.getPath());
    }

    public void addTree(String hash, Tree tree) {
        references.addObject(hash, tree.getPath());
        for (Blob blob : tree.getBlobs()) {
            addBlob(blob.getName(), blob);
        }
        for (Tree subTree : tree.getTrees()) {
            addTree(subTree.getName(), subTree);
        }
    }

    public References getReferences() {
        return references;
    }
}
