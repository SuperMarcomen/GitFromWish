package it.marcodemartino.gitfromwish.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiskFileReader implements FileReader {

    @Override
    public Set<Path> getFilesFromWorkingFolder() {
        try (Stream<Path> stream = Files.list(Paths.get(""))) {
            return stream
                    .collect(Collectors.toSet());
        } catch (IOException ignored) {
        }
        return null;
    }
}
