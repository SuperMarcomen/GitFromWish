package it.marcodemartino.mdma.io;

import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiskFileWriter implements FileWriter {

    public DiskFileWriter() {
        tryCreateFolder();
    }

    private void tryCreateFolder() {
        try {
            for (FolderNames folder : FolderNames.values()) {
                Files.createDirectories(folder.getFolderName());
            }
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
