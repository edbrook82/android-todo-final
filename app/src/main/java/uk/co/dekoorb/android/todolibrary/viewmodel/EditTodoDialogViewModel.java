package uk.co.dekoorb.android.todolibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import uk.co.dekoorb.android.todolibrary.BaseApp;
import uk.co.dekoorb.android.todolibrary.db.AppDatabase;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by edbrook on 07/01/2018.
 */

public class EditTodoDialogViewModel extends AndroidViewModel {
    private final AppDatabase mDb;

    public EditTodoDialogViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
    }

    public LiveData<Todo> getTodo(long id) {
        return mDb.todoDao().getTodo(id);
    }

    public boolean updateClicked(Todo todo) {
        new AsyncTask<Todo, Void, Void>() {
            @Override
            protected Void doInBackground(Todo... todos) {
                mDb.todoDao().updateTodos(todos[0]);
                return null;
            }
        }.execute(todo);
        return true;
    }
}
