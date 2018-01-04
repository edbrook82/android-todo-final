package uk.co.dekoorb.android.booklibrary.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import uk.co.dekoorb.android.booklibrary.db.dao.BookDao;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */
@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}
