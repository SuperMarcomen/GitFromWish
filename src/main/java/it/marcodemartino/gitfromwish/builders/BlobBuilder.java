package it.marcodemartino.gitfromwish.builders;

import it.marcodemartino.gitfromwish.encryption.Hashing;
import it.marcodemartino.gitfromwish.entities.Blob;
import it.marcodemartino.gitfromwish.io.FileReader;

import java.nio.file.Path;

public class BlobBuilder extends Builder<Blob> {

    public BlobBuilder(FileReader fileReader, Hashing hashing) {
        super(fileReader, hashing);
    }

    @Override
    public Blob build(Path path) {
        byte[] content = fileReader.readFile(path);
        String hash = hashing.hash(content);
        return new Blob(hash, content);
    }
}
