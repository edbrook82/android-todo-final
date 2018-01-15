package uk.co.dekoorb.android.todolibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import uk.co.dekoorb.android.todolibrary.BaseApp;
import uk.co.dekoorb.android.todolibrary.db.AppDatabase;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;


/**
 * Created by ed on 05/01/18.
 */

public class TodoDetailViewModel extends BaseViewModel {
    public TodoDetailViewModel(Application app) {
        super(app);
    }

    public LiveData<Todo> getTodo(long id) {
        return mDb.todoDao().getTodo(id);
    }

    public void toggleRead(Todo todo) {
        todo.setComplete(!todo.isComplete());
        new AsyncTask<Todo, Void, Void>() {
            @Override
            protected Void doInBackground(Todo... todos) {
                mDb.todoDao().updateTodos(todos);
                return null;
            }
        }.execute(todo);
    }

    public void deleteTodo(Todo todo) {
        new AsyncTask<Todo, Void, Void>() {
            @Override
            protected Void doInBackground(Todo... todos) {
                mDb.todoDao().deleteTodos(todos);
                return null;
            }
        }.execute(todo);
    }
}
