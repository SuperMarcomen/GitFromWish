package it.marcodemartino.gitfromwish.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree extends MDMAEntity {

    private final Map<String, Blob> blobs;
    private final Map<String, Tree> trees;

    public Tree(String name, List<Blob> blobsList, List<Tree> treesList) {
        super(name);
        blobs = new HashMap<>();
        blobsList.forEach(blob -> blobs.put(blob.getName(), blob));
        trees = new HashMap<>();
        treesList.forEach(tree -> trees.put(tree.getName(), tree));
    }

    @Override
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        blobs.values().forEach(blob -> stringBuilder.append(blob.print()).append(System.lineSeparator()));
        trees.values().forEach(tree -> stringBuilder.append(tree.flatPrint()).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

    private String flatPrint() {
        return "tree: " + name;
    }

    public String getName() {
        return name;
    }

    public void addBlob(Blob blob) {
        blobs.put(blob.getName(), blob);
    }

    public Blob getBlob(String name) {
        return blobs.get(name);
    }

    public void addTree(Tree tree) {
        trees.put(tree.getName(), tree);
    }

    public Tree getTree(String name) {
        return trees.get(name);
    }
}
