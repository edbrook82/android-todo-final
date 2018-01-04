package uk.co.dekoorb.android.archtest.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import uk.co.dekoorb.android.archtest.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */

@Dao
public interface BookDao {
    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM books WHERE id = :id")
    LiveData<Book> getBook(int id);

    @Query("SELECT * FROM books WHERE hasRead = 1")
    LiveData<List<Book>> getBooksRead();

    @Insert
    void addBook(Book book);

    @Update
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);
}
