package it.marcodemartino.mdma.entities;

import it.marcodemartino.mdma.visitors.EntityVisitor;

public class Blob extends MDMAPathEntity {

    public Blob(String name, String path) {
        super(name, path);
    }

    @Override
    public String print() {
        return "blob:" + name;
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
