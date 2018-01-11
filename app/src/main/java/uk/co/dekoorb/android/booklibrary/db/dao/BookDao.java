package uk.co.dekoorb.android.booklibrary.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */

@Dao
public interface BookDao {
    @Query("SELECT * FROM books ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM books")
    Cursor getAllBooksCursor();

    @Query("SELECT * FROM books WHERE id = :id")
    LiveData<Book> getBook(long id);

    @Query("SELECT * FROM books WHERE hasRead = 1")
    LiveData<List<Book>> getBooksRead();

    @Insert
    void addBooks(Book... books);

    @Update
    void updateBooks(Book... books);

    @Delete
    void deleteBooks(Book... books);
}
