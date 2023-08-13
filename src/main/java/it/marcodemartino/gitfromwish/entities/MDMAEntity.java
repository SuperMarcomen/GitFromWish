package it.marcodemartino.gitfromwish.entities;

import it.marcodemartino.gitfromwish.visitors.EntityVisitor;

public abstract class MDMAEntity {

    protected final String name;

    public MDMAEntity(String name) {
        this.name = name;
    }

    public abstract String print();

    public abstract void accept(EntityVisitor visitor);
}
