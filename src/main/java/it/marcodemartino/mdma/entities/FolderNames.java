package it.marcodemartino.mdma.entities;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum FolderNames {

    MAIN(Paths.get("MDMA")),
    BLOB(Paths.get(MAIN.getFolderName().toString(), "blob")),
    TREE(Paths.get(MAIN.getFolderName().toString(), "tree")),
    COMMIT(Paths.get(MAIN.getFolderName().toString(), "commit")),
    REFS(Paths.get(MAIN.getFolderName().toString(), "refs"));

    private final Path folderName;

    FolderNames(Path folderName) {
        this.folderName = folderName;
    }

    public Path getFolderName() {
        return this.folderName;
    }
}
