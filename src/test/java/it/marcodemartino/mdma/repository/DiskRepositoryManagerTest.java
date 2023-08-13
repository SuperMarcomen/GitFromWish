package it.marcodemartino.mdma.repository;

import org.junit.jupiter.api.Test;

class DiskRepositoryManagerTest {

    @Test
    void createRepository() {
        RepositoryManager repositoryManager = new DiskRepositoryManager();
        repositoryManager.createRepository();
    }
}