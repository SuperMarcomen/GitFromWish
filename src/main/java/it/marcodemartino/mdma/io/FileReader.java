package it.marcodemartino.mdma.io;

import java.nio.file.Path;
import java.util.Set;

public interface FileReader {

    byte[] readFile(Path path);
    Set<Path> getFilesFromFolder(Path path);
    Set<Path> getFilesFromWorkingFolder();
}
