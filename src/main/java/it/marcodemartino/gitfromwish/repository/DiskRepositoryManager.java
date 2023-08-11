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
        tryCreateHiddenFolder("MDMA");
    }

    private void tryCreateHiddenFolder(String name) {
        try {
            createHiddenFolder(name);
        } catch (IOException e) {
            Logger.error("An error occurred while create the repository directory!");
        }
    }

    private void createHiddenFolder(String name) throws IOException {
        Path repoPath = Paths.get(getWorkingPath(), name);
        if (Files.exists(repoPath)) return;
        Files.createDirectory(repoPath);
        tryHideFile(repoPath);
    }

    private void tryHideFile(Path path) {
        try {
            hideFile(path);
        } catch (IOException e) {
            Logger.error("An error occurred while hiding the repository directory!");
        }
    }

    private void hideFile(Path path) throws IOException {
        DosFileAttributes attrs = Files.readAttributes(path, DosFileAttributes.class);
        if (!attrs.isHidden()) {
            Files.setAttribute(path, "dos:hidden", true);
        }
    }

    private String getWorkingPath() {
        return System.getProperty("user.dir");
    }
}
