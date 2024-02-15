package it.marcodemartino.mdma.io;

import java.io.InputStream;
import java.nio.file.Path;

public interface FileWriter {

    void writeFile(Path path, InputStream content);
    void writeFile(Path path, byte[] bytes);
}
