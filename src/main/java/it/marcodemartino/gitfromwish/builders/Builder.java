package it.marcodemartino.gitfromwish.builders;

import it.marcodemartino.gitfromwish.encryption.Hashing;
import it.marcodemartino.gitfromwish.io.FileReader;

import java.nio.file.Path;

public abstract class Builder<T> {

    protected final FileReader fileReader;
    protected final Hashing hashing;

    public Builder(FileReader fileReader, Hashing hashing) {
        this.fileReader = fileReader;
        this.hashing = hashing;
    }
    abstract T build(Path path);
}
