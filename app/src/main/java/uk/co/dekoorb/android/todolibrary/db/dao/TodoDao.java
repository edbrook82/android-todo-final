package uk.co.dekoorb.android.todolibrary.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by ed on 04/01/18.
 */

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY title ASC")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * FROM todos")
    Cursor getAllTodosCursor();

    @Query("SELECT * FROM todos WHERE id = :id")
    LiveData<Todo> getTodo(long id);

    @Query("SELECT * FROM todos WHERE hasRead = 1")
    LiveData<List<Todo>> getTodosRead();

    @Insert
    void addTodos(Todo... todos);

    @Update
    void updateTodos(Todo... todos);

    @Delete
    void deleteTodos(Todo... todos);
}
