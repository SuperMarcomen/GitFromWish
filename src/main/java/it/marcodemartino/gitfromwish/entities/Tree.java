package it.marcodemartino.gitfromwish.entities;

import it.marcodemartino.gitfromwish.encryption.Hashing;
import it.marcodemartino.gitfromwish.visitors.EntityVisitor;

import java.util.Collection;
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
        return print(blobs.values(), trees.values());
    }

    public static String generateHash(Hashing hashing, Collection<Blob> blobsList, Collection<Tree> treesList) {
        return hashing.hash(print(blobsList, treesList).getBytes());
    }

    private static String print(Collection<Blob> blobsList, Collection<Tree> treesList) {
        StringBuilder stringBuilder = new StringBuilder();
        blobsList.forEach(blob -> stringBuilder.append(blob.print()).append(System.lineSeparator()));
        treesList.forEach(tree -> stringBuilder.append(tree.flatPrint()).append(System.lineSeparator()));
        return stringBuilder.toString();
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
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

    public Collection<Blob> getBlobs() {
        return blobs.values();
    }

    public void addTree(Tree tree) {
        trees.put(tree.getName(), tree);
    }

    public Tree getTree(String name) {
        return trees.get(name);
    }

    public Collection<Tree> getTrees() {
        return trees.values();
    }
}
