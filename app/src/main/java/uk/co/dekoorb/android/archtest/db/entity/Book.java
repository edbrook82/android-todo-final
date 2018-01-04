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
}
