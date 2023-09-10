package it.marcodemartino.mdma.entities;

import it.marcodemartino.mdma.encryption.Hashing;
import it.marcodemartino.mdma.visitors.EntityVisitor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commit extends MDMAEntity {

    private static final String PRINT_MESSAGE = "author;%s" + System.lineSeparator()
            + "date;%s" + System.lineSeparator()
            + "%s";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
    private final Tree mainTree;
    private final String author;
    private final LocalDateTime dateTime;

    public Commit(String name, Tree mainTree, String author, LocalDateTime dateTime) {
        super(name);
        this.mainTree = mainTree;
        this.author = author;
        this.dateTime = dateTime;
    }

    public Tree getMainTree() {
        return mainTree;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String print() {
        return print(author, dateTime, mainTree);
    }

    public static String generateHash(Hashing hashing, String author, LocalDateTime dateTime, Tree mainTree) {
        return hashing.hash(print(author, dateTime, mainTree).getBytes());
    }

    private static String print(String author, LocalDateTime dateTime, Tree mainTree) {
        return PRINT_MESSAGE.formatted(
                author,
                DATE_FORMATTER.format(dateTime),
                mainTree.getName()
        );
    }

    @Override
    public void accept(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
