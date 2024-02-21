package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.entities.References;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.io.FileWriter;

import java.io.InputStream;
import java.nio.file.Paths;

public class SaveEntitiesVisitor implements EntityVisitor {

    private final FileWriter fileWriter;
    private final FileReader fileReader;

    public SaveEntitiesVisitor(FileWriter fileWriter, FileReader fileReader) {
        this.fileWriter = fileWriter;
        this.fileReader = fileReader;
    }

    @Override
    public void visit(Blob blob) {
        InputStream inputStream = fileReader.readFile(Paths.get(blob.getPath()));
        fileWriter.writeFile(Paths.get(FolderNames.BLOB.getFolderName().toString(), blob.getName()), inputStream);
    }

    @Override
    public void visit(Tree tree) {
        fileWriter.writeFile(Paths.get(FolderNames.TREE.getFolderName().toString(), tree.getName()), tree.print().getBytes());
        for (Tree subtree : tree.getTrees()) {
            visit(subtree);
        }
        for (Blob blob : tree.getBlobs()) {
            visit(blob);
        }
    }

    @Override
    public void visit(Commit commit) {
        fileWriter.writeFile(Paths.get(FolderNames.COMMIT.getFolderName().toString(), commit.getName()), commit.print().getBytes());
        visit(commit.getMainTree());
    }

    @Override
    public void visit(References references) {
        fileWriter.writeFile(Paths.get(FolderNames.REFS.getFolderName().toString(), references.getName()), references.print().getBytes());
    }
}
