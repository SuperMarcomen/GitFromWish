package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.References;
import it.marcodemartino.mdma.entities.Tree;

public interface EntityVisitor {

    void visit(Blob blob);
    void visit(Tree tree);
    void visit(Commit commit);
    void visit(References references);
}
