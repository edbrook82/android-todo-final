package uk.co.dekoorb.android.archtest.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ed on 04/01/18.
 */

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    private String id;
    private String title;
    private String author;
    private String description;
    private boolean hasRead;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
