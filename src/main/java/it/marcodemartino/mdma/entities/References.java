package it.marcodemartino.mdma.entities;

import it.marcodemartino.mdma.visitors.EntityVisitor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class References extends MDMAEntity {

    private static final String REFERENCES_NAME = "refs";
    private final Map<String, String> objects;

    public References() {
        super(REFERENCES_NAME);
        objects = new HashMap<>();
    }

    public Map<String, String> getObjects() {
        return objects;
    }

    public void addObject(String hash, String path) {
        objects.put(hash, path);
    }

    public String getObject(String hash) {
        return objects.get(hash);
    }

    @Override
    public String print() {
        return objects.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
