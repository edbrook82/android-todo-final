package uk.co.dekoorb.android.todolibrary.ui;


import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.todolibrary.R;
import uk.co.dekoorb.android.todolibrary.databinding.TodoListFragmentBinding;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;
import uk.co.dekoorb.android.todolibrary.ui.adapter.TodoAdapter;
import uk.co.dekoorb.android.todolibrary.ui.dialog.AddTodoDialogFragment;
import uk.co.dekoorb.android.todolibrary.viewmodel.TodoListViewModel;

/**
 * TodoListFragment is a {@link Fragment} to display a list of todos.
 * Use the {@link TodoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoListFragment extends Fragment {

    public static final String TAG = "TodoListFragment";

    public static final String ADD_DIALOG_TAG = "add_dialog_tag";

    private TodoAdapter mTodoAdapter;
    private TodoListFragmentBinding mBinding;
    private TodoListViewModel mViewModel;

    private TodoListActionsListener mListener;

    public interface TodoListActionsListener {
        void onTodoSelected(Todo todo);
    }

    public TodoListFragment() {
        // Required empty public constructor
    }

    public static TodoListFragment newInstance() {
        return new TodoListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (TodoListActionsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TodoListActionsListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTodoAdapter = new TodoAdapter(mTodoClickCallback);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.todo_list_fragment, container, false);
        mBinding.setIsLoading(true);
        mBinding.todosList.setAdapter(mTodoAdapter);
        mBinding.fabAdd.setOnClickListener(fabAddListener);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TodoListViewModel.class);
        subscribeUI();
        setSwipeToDelete();
    }

    private void subscribeUI() {
        mViewModel.getTodosList().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                if (todos != null) {
                    mBinding.setIsLoading(false);
                    mTodoAdapter.setTodoList(todos);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    private void setSwipeToDelete() {
        SwipeToDeleteCallback callback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.deleteTodoAtPos(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mBinding.todosList);
    }

    private final TodoClickCallback mTodoClickCallback = new TodoClickCallback() {
        @Override
        public void onClick(Todo todo) {
            mListener.onTodoSelected(todo);
        }

        @Override
        public void onLongClick(final Todo todo) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.delete_todo)
                    .setMessage(todo.getTitle())
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mViewModel.deleteTodo(todo);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User cancelled
                        }
                    })
                    .create()
                    .show();
        }
    };

    private final View.OnClickListener fabAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AddTodoDialogFragment()
                    .show(getFragmentManager(), ADD_DIALOG_TAG);
        }
    };
}
