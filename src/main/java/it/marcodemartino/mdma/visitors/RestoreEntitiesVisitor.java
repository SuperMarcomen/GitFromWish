package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.References;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileWriter;

import java.nio.file.Paths;

public class RestoreEntitiesVisitor implements EntityVisitor {

    private final FileWriter fileWriter;

    public RestoreEntitiesVisitor(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    @Override
    public void visit(Blob blob) {
        fileWriter.writeFile(Paths.get(blob.getPath()), blob.getContent());
    }

    @Override
    public void visit(Tree tree) {
        for (Blob blob : tree.getBlobs()) {
            visit(blob);
        }
        for (Tree subTree : tree.getTrees()) {
            visit(subTree);
        }
    }

    @Override
    public void visit(Commit commit) {
        visit(commit.getMainTree());
    }

    @Override
    public void visit(References references) {

    }
}
