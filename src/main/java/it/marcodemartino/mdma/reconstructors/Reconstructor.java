package it.marcodemartino.mdma.reconstructors;

import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.io.FileReader;

public abstract class Reconstructor<T> {

    protected final FileReader fileReader;
    protected final ReferenceTracker referenceTracker;

    public Reconstructor(FileReader fileReader, ReferenceTracker referenceTracker) {
        this.fileReader = fileReader;
        this.referenceTracker = referenceTracker;
    }

    public abstract T reconstruct(String hash);
}