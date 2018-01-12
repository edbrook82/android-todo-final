package uk.co.dekoorb.android.todolibrary.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import uk.co.dekoorb.android.todolibrary.db.dao.TodoDao;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by ed on 04/01/18.
 */
@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
}
