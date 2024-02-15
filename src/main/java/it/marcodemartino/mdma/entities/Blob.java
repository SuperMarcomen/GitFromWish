package it.marcodemartino.mdma.entities;

import it.marcodemartino.mdma.visitors.EntityVisitor;

import java.io.InputStream;

public class Blob extends MDMAPathEntity {

    private final InputStream content;

    public Blob(String name, InputStream content, String path) {
        super(name, path);
        this.content = content;
    }

    public InputStream getContent() {
        return content;
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
