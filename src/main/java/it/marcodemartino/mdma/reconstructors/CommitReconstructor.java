package it.marcodemartino.mdma.reconstructors;

import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.entities.Commit;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.entities.Tree;
import it.marcodemartino.mdma.io.FileReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommitReconstructor extends Reconstructor<Commit> {

    private final Reconstructor<Tree> treeReconstructor;

    public CommitReconstructor(FileReader fileReader, ReferenceTracker referenceTracker, Reconstructor<Tree> treeReconstructor) {
        super(fileReader, referenceTracker);
        this.treeReconstructor = treeReconstructor;
    }

    @Override
    public Commit reconstruct(String hash) {
        Path path = Paths.get(FolderNames.COMMIT.getFolderName().toString(), hash);
        String content = fileReader.readFileAsString(path);
        String[] lines = content.split(System.lineSeparator());
        String author = lines[0].split(";")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(lines[1].split(";")[1], formatter);
        Tree mainTree = treeReconstructor.reconstruct(lines[2]);

        return new Commit(hash, mainTree, author, date);
    }
}
