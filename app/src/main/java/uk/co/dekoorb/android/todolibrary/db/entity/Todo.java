package uk.co.dekoorb.android.todolibrary.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ed on 04/01/18.
 */

@Entity(tableName = "todos")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String author;
    private String description;
    private boolean hasRead;

    public Todo() {}

    @Ignore
    public Todo(String title, String author, String description, boolean hasRead) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.hasRead = hasRead;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return hasRead;
    }

    public void setRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    @Override
    public String toString() {
        return id + "-" + title;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Todo)) {
            return false;
        }
        Todo b = (Todo) obj;
        return b.getId() == this.getId() &&
                b.getTitle().equals(this.getTitle()) &&
                b.getAuthor().equals(this.getAuthor()) &&
                b.getDescription().equals(this.getDescription()) &&
                b.isRead() == this.isRead();
    }
}
