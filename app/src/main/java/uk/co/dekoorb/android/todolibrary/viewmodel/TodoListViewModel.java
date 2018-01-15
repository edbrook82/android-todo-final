package uk.co.dekoorb.android.todolibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import uk.co.dekoorb.android.todolibrary.BaseApp;
import uk.co.dekoorb.android.todolibrary.db.AppDatabase;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by ed on 04/01/18.
 */

public class TodoListViewModel extends BaseViewModel {
    private final LiveData<List<Todo>> mTodoList;

    public TodoListViewModel(Application app) {
        super(app);
        mTodoList = mDb.todoDao().getAllTodos();
    }

    public LiveData<List<Todo>> getTodosList() {
        return this.mTodoList;
    }

    public void deleteTodo(Todo todo) {
        new AsyncTask<Todo, Void, Void>() {
            @Override
            protected Void doInBackground(Todo... todos) {
                mDb.todoDao().deleteTodos(todos[0]);
                return null;
            }
        }.execute(todo);
    }

    public void deleteTodoAtPos(int pos) {
        List<Todo> todos = mTodoList.getValue();
        if (todos != null) {
            deleteTodo(todos.get(pos));
        }
    }
}
