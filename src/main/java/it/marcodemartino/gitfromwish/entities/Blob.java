package it.marcodemartino.gitfromwish.entities;

import it.marcodemartino.gitfromwish.visitors.EntityVisitor;

public class Blob extends MDMAEntity {

    String string;
    private final byte[] content;

    public Blob(String name, byte[] content) {
        super(name);
        string = new String(content);
        this.content = content;
    }

    public String getName() {
        return name;
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
