package it.marcodemartino.gitfromwish.visitors;

import it.marcodemartino.gitfromwish.entities.Blob;
import it.marcodemartino.gitfromwish.entities.Commit;
import it.marcodemartino.gitfromwish.entities.Tree;

public interface EntityVisitor {

    void visit(Blob blob);
    void visit(Tree tree);
    void visit(Commit commit);
}
