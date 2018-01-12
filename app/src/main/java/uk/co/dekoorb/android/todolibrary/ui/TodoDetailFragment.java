package uk.co.dekoorb.android.todolibrary.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import uk.co.dekoorb.android.todolibrary.R;
import uk.co.dekoorb.android.todolibrary.databinding.TodoDetailFragmentBinding;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;
import uk.co.dekoorb.android.todolibrary.ui.dialog.EditTodoDialogFragment;
import uk.co.dekoorb.android.todolibrary.viewmodel.TodoDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoDetailFragment extends Fragment {
    public static final String TAG = "TodoDetailFragment";

    private static final String ARG_TODO_ID = "todo_id";

    private long mTodoId;
    private TodoDetailViewModel mViewModel;
    private TodoDetailFragmentBinding mBinding;

    private TodoDetailFragmentActions mListener;

    public interface TodoDetailFragmentActions {
        void onTodoDeleted();
        void onSearchGoogle(String title);
    }

    public TodoDetailFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param todoId Id of the todo to display.
     * @return A new instance of fragment TodoDetailFragment.
     */
    public static TodoDetailFragment newInstance(long todoId) {
        TodoDetailFragment fragment = new TodoDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TODO_ID, todoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTodoId = getArguments().getLong(ARG_TODO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.todo_detail_fragment, container, false);
        mBinding.fabEdit.setOnClickListener(fabEditListener);
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (TodoDetailFragmentActions) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TodoDetailFragmentActions");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.todo_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_toggle_complete:
                mViewModel.toggleRead(mBinding.getTodo());
                break;
            case R.id.action_search_amazon:
                mListener.onSearchGoogle(mBinding.getTodo().getTitle());
                break;
            case R.id.action_edit:
                editTodo();
                break;
            case R.id.action_delete:
                mViewModel.deleteTodo(mBinding.getTodo());
                mListener.onTodoDeleted();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TodoDetailViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getTodo(mTodoId).observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {
                if (todo != null) {
                    mBinding.setTodo(todo);
                }
            }
        });
    }

    private View.OnClickListener fabEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editTodo();
        }
    };

    private void editTodo() {
            EditTodoDialogFragment.newInstance(mTodoId)
                    .show(getFragmentManager(), EditTodoDialogFragment.TAG);
    }
}
