package it.marcodemartino.mdma.io;

import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiskFileWriter implements FileWriter {

    public DiskFileWriter() {
        for (FolderNames folder : FolderNames.values()) {
            createFolder(folder.getFolderName());
        }
    }

    @Override
    public void writeFile(Path path, InputStream content) {
        tryWriteInputStream(path, content);
    }

    @Override
    public void writeFile(Path path, byte[] bytes) {
        tryWriteFile(path, bytes);
    }

    @Override
    public void createFolder(Path path) {
        tryCreateFolder(path);
    }

    private void tryCreateFolder(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            Logger.error("An error occurred while creating the folders for the entities!");
        }
    }

    private static void tryWriteInputStream(Path path, InputStream content) {
        try {
            Files.copy(content, path);
        } catch (IOException e) {
            Logger.error("An error occurred while writing a file! ");
        }
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
