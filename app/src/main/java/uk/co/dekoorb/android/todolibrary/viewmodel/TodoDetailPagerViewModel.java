package uk.co.dekoorb.android.todolibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import uk.co.dekoorb.android.todolibrary.BaseApp;
import uk.co.dekoorb.android.todolibrary.db.AppDatabase;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by edbrook on 07/01/2018.
 */

public class TodoDetailPagerViewModel extends BaseViewModel {
    private final LiveData<List<Todo>> mTodoList;

    public TodoDetailPagerViewModel(Application app) {
        super(app);
        mTodoList = mDb.todoDao().getAllTodos();
    }

    public LiveData<List<Todo>> getTodosList() {
        return this.mTodoList;
    }
}
