package it.marcodemartino.gitfromwish.repository;

import it.marcodemartino.gitfromwish.logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

public class DiskRepositoryManager implements RepositoryManager {

    @Override
    public void createRepository() {
        createHiddenFolder("MDMA");
    }

    private void createHiddenFolder(String name) {
        Path repoPath = Paths.get(getWorkingPath(), name);
        if (Files.exists(repoPath)) return;
        try {
            Files.createDirectory(repoPath);
        } catch (IOException e) {
            Logger.error("An error occurred while create the repository directory!");
        }
        hideFile(repoPath);
    }

    private void hideFile(Path path) {
        try {
            DosFileAttributes attrs = Files.readAttributes(path, DosFileAttributes.class);
            if (!attrs.isHidden()) {
                Files.setAttribute(path, "dos:hidden", true);
            }
        } catch (IOException e) {
            Logger.error("An error occurred while hiding the repository directory!");
        }
    }

    private String getWorkingPath() {
        return System.getProperty("user.dir");
    }
}
