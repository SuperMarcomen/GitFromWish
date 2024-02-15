package it.marcodemartino.mdma.remote;

import it.marcodemartino.mdma.entities.Commit;

public interface RemoteRepository {

    Commit pullLatestCommit();
    void pushCommit(Commit commit);
}
