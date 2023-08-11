package it.marcodemartino.gitfromwish.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commit extends MDMAEntity {

    private static final String PRINT_MESSAGE = """
    author: %s
    date: %s
    %s""";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
    private final Tree mainTree;
    private final String author;
    private final LocalDateTime dateTime;

    public Commit(String name, Tree mainTree, String author, LocalDateTime dateTime) {
        super(name);
        this.mainTree = mainTree;
        this.author = author;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
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
        return PRINT_MESSAGE.formatted(
                author,
                dateFormatter.format(dateTime),
                mainTree.getName()
        );
    }
}
