package uk.co.dekoorb.android.todolibrary.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.todolibrary.R;
import uk.co.dekoorb.android.todolibrary.databinding.TodoListItemBinding;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;
import uk.co.dekoorb.android.todolibrary.ui.TodoClickCallback;

/**
 * Created by ed on 04/01/18.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> mTodoList;
    private TodoClickCallback mTodoClickCallback;

    public TodoAdapter(TodoClickCallback callback) {
        mTodoClickCallback = callback;
    }

    public void setTodoList(List<Todo> todoList) {
        mTodoList = todoList;
        notifyDataSetChanged();
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TodoListItemBinding itemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.todo_list_item,
                        parent, false);
        return new TodoViewHolder(itemBinding, mTodoClickCallback);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.mBinding.setTodo(mTodoList.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTodoList == null ? 0 : mTodoList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        private TodoListItemBinding mBinding;
        private TodoClickCallback mCallback;

        TodoViewHolder(TodoListItemBinding itemBinding, TodoClickCallback clickCallback) {
            super(itemBinding.getRoot());
            mCallback = clickCallback;
            mBinding = itemBinding;
            mBinding.todoListItem.setOnClickListener(this);
            mBinding.todoListItem.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallback.onClick(mBinding.getTodo());
        }

        @Override
        public boolean onLongClick(View v) {
            mCallback.onLongClick(mBinding.getTodo());
            return true;
        }
    }
}
