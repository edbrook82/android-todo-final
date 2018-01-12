package uk.co.dekoorb.android.todolibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import uk.co.dekoorb.android.todolibrary.BaseApp;
import uk.co.dekoorb.android.todolibrary.db.AppDatabase;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by edbrook on 07/01/2018.
 */

public class AddTodoDialogViewModel extends AndroidViewModel {
    private final AppDatabase mDb;
    private Todo mTodo;

    public AddTodoDialogViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
        mTodo = new Todo();
    }

    public Todo getTodo() {
        return mTodo;
    }

    public void reset() {
        mTodo = new Todo();
    }

    public boolean addClicked() {
        new AsyncTask<Todo, Void, Void>() {
            @Override
            protected Void doInBackground(Todo... todos) {
                mDb.todoDao().addTodos(todos[0]);
                return null;
            }
        }.execute(mTodo);

        reset();
        return true;
    }
}
