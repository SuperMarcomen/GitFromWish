package it.marcodemartino.mdma.io;

import it.marcodemartino.mdma.logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiskFileReader implements FileReader {

    @Override
    public byte[] readFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            Logger.error("An error occurred while reading a file!");
        }
        return new byte[0];
    }

    @Override
    public Set<Path> getFilesFromFolder(Path path) {
        try (Stream<Path> stream = Files.list(path)) {
            return stream
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            Logger.error("An error occurred reading from the working folder!");
        }
        return null;
    }

    @Override
    public Set<Path> getFilesFromWorkingFolder() {
        return getFilesFromFolder(Paths.get(""));
    }
}
