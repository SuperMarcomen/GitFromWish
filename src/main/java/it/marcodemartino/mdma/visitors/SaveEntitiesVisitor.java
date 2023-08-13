package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileWriter;

import java.nio.file.Paths;

public class SaveEntitiesVisitor implements EntityVisitor {

    private static final String MAIN_FOLDER = "MDMA";
    private static final String BLOB_PATH = "blob";
    private static final String TREE_PATH = "tree";
    private static final String COMMIT_PATH = "commit";
    private final FileWriter fileWriter;

    public SaveEntitiesVisitor(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    @Override
    public void visit(Blob blob) {
        fileWriter.writeFile(Paths.get(MAIN_FOLDER, BLOB_PATH, blob.getName()), blob.getContent());
    }

    @Override
    public void visit(Tree tree) {
        fileWriter.writeFile(Paths.get(MAIN_FOLDER, TREE_PATH, tree.getName()), tree.print().getBytes());
        for (Tree subtree : tree.getTrees()) {
            visit(subtree);
        }
        for (Blob blob : tree.getBlobs()) {
            visit(blob);
        }
    }

    @Override
    public void visit(Commit commit) {
        fileWriter.writeFile(Paths.get(MAIN_FOLDER, COMMIT_PATH, commit.getName()), commit.print().getBytes());
        visit(commit.getMainTree());
    }
}
