package uk.co.dekoorb.android.todolibrary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import uk.co.dekoorb.android.todolibrary.db.entity.Todo;
import uk.co.dekoorb.android.todolibrary.ui.TodoDetailFragment;

/**
 * Created by edbrook on 07/01/2018.
 */

public class TodoPagerAdapter extends FragmentStatePagerAdapter {

    private List<Todo> mTodos;

    public TodoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTodosList(List<Todo> todos) {
        mTodos = todos;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        long todoId = mTodos.get(position).getId();
        return TodoDetailFragment.newInstance(todoId);
    }

    @Override
    public int getCount() {
        return mTodos == null ? 0 : mTodos.size();
    }
}
