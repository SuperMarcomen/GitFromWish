package it.marcodemartino.mdma.io;

import it.marcodemartino.mdma.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiskFileReader implements FileReader {

    @Override
    public InputStream readFile(Path path) {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            Logger.error("An error occurred while reading a file!");
            e.printStackTrace();
        }
        return InputStream.nullInputStream();
    }

    @Override
    public String readFileAsString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            Logger.error("An error occurred while reading a file!");
            e.printStackTrace();
        }
        return "";
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
