package it.marcodemartino.mdma.reconstructors;

import it.marcodemartino.mdma.commits.ReferenceTracker;
import it.marcodemartino.mdma.entities.Blob;
import it.marcodemartino.mdma.entities.FolderNames;
import it.marcodemartino.mdma.io.FileReader;

import java.io.InputStream;
import java.nio.file.Paths;

public class BlobReconstructor extends Reconstructor<Blob> {

    public BlobReconstructor(FileReader fileReader, ReferenceTracker referenceTracker) {
        super(fileReader, referenceTracker);
    }

    @Override
    public Blob reconstruct(String hash) {
        String path = referenceTracker.getObjectPath(hash);
        InputStream content = fileReader.readFile(Paths.get(FolderNames.BLOB.getFolderName().toString(), hash));
        return new Blob(hash, content, path);
    }
}
