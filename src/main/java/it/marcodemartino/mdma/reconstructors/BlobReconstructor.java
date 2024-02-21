package it.marcodemartino.mdma.reconstructors;

import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.io.FileReader;

public class BlobReconstructor extends Reconstructor<Blob> {

    public BlobReconstructor(FileReader fileReader, ReferenceTracker referenceTracker) {
        super(fileReader, referenceTracker);
    }

    @Override
    public Blob reconstruct(String hash) {
        String path = referenceTracker.getObjectPath(hash);
        return new Blob(hash, path);
    }
}
