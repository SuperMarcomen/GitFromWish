package it.marcodemartino.gitfromwish.io;

import java.nio.file.Path;
import java.util.Set;

public interface FileReader {

    Set<Path> getFilesFromWorkingFolder();
}
