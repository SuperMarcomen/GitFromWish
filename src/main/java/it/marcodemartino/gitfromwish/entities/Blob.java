package it.marcodemartino.gitfromwish.entities;

public class Blob {

    private final String name;
    private final String content;

    public Blob(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
