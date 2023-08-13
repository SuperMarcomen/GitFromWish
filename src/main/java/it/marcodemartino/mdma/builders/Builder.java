package it.marcodemartino.mdma.builders;

import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.io.FileReader;

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
