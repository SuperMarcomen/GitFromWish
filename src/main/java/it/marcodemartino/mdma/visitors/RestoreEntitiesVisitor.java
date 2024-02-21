package it.marcodemartino.mdma.visitors;

import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.References;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;
import it.marcodemartino.mdma.io.FileWriter;

import java.io.InputStream;
import java.nio.file.Paths;

public class RestoreEntitiesVisitor implements EntityVisitor {

    private final FileWriter fileWriter;
    private final FileReader fileReader;

    public RestoreEntitiesVisitor(FileWriter fileWriter, FileReader fileReader) {
        this.fileWriter = fileWriter;
        this.fileReader = fileReader;
    }

    @Override
    public void visit(Blob blob) {
        InputStream inputStream = fileReader.readFile(Paths.get(blob.getPath()));
        fileWriter.writeFile(Paths.get(blob.getPath()), inputStream);
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
