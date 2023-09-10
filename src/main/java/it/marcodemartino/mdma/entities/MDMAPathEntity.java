package it.marcodemartino.mdma.entities;

public abstract class MDMAPathEntity extends MDMAEntity {

    private final String path;

    public MDMAPathEntity(String name, String path) {
        super(name);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
