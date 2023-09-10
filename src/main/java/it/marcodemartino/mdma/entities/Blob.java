package it.marcodemartino.mdma.entities;

import it.marcodemartino.mdma.visitors.EntityVisitor;

public class Blob extends MDMAPathEntity {

    private final byte[] content;

    public Blob(String name, byte[] content, String path) {
        super(name, path);
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public String print() {
        return "blob: " + name;
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
