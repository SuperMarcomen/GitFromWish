package it.marcodemartino.mdma.io;

import java.nio.file.Path;

public interface FileWriter {

    void writeFile(Path path, byte[] bytes);
    void createFolder(Path path);
}
