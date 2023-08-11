package it.marcodemartino.gitfromwish.entities;

public abstract class MDMAEntity {

    protected final String name;

    public MDMAEntity(String name) {
        this.name = name;
    }

    public abstract String print();
}
