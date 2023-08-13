package it.marcodemartino.gitfromwish.io;

import it.marcodemartino.gitfromwish.logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiskFileWriter implements FileWriter {

    public DiskFileWriter() {
        tryCreateFolder();
    }

    private void tryCreateFolder() {
        try {
            Files.createDirectories(Paths.get("MDMA", "blob"));
            Files.createDirectories(Paths.get("MDMA", "tree"));
            Files.createDirectories(Paths.get("MDMA", "commit"));
        } catch (IOException e) {
            Logger.error("An error occurred while creating the folders for the entities!");
        }
    }

    @Override
    public void writeFile(Path path, byte[] bytes) {
        tryWriteFile(path, bytes);
    }

    private void tryWriteFile(Path path, byte[] bytes) {
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            Logger.error("An error occurred while writing a file! ");
            e.printStackTrace();
        }
    }
}
