package it.marcodemartino.mdma.io;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Set;

public interface FileReader {

    InputStream readFile(Path path);
    String readFileAsString(Path path);
    Set<Path> getFilesFromFolder(Path path);
    Set<Path> getFilesFromWorkingFolder();
}
