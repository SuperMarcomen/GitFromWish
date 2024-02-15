package it.marcodemartino.mdma.io;

import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class DiskFileWriter implements FileWriter {

    public DiskFileWriter() {
        for (FolderNames folder : FolderNames.values()) {
            tryCreateFolder(folder.getFolderName());
        }
    }

    @Override
    public void writeFile(Path path, InputStream content) {
        if (Files.notExists(path.getParent())) {
            tryCreateFolder(path.getParent());
        }
        tryWriteInputStream(path, content);
    }

    @Override
    public void writeFile(Path path, byte[] bytes) {
        tryWriteFile(path, bytes);
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
            Files.copy(content, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Logger.error("An error occurred while writing a file! ");
            e.printStackTrace();
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
