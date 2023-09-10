package it.marcodemartino.mdma.entities;

import it.marcodemartino.mdma.visitors.EntityVisitor;

public abstract class MDMAEntity {

    protected final String name;

    public MDMAEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String print();

    public abstract void accept(EntityVisitor visitor);
}
