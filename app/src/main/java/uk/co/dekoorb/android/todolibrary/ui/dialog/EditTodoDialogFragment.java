package uk.co.dekoorb.android.todolibrary.ui.dialog;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import uk.co.dekoorb.android.todolibrary.R;
import uk.co.dekoorb.android.todolibrary.databinding.TodoDialogBinding;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;
import uk.co.dekoorb.android.todolibrary.viewmodel.EditTodoDialogViewModel;

/**
 * Created by edbrook on 08/01/2018.
 */

public class EditTodoDialogFragment extends DialogFragment {
    public static final String TAG = "EditTodoDialogFragment";

    public static final String ARG_TODO_ID = "todo_id";

    private EditTodoDialogViewModel mViewModel;
    private TodoDialogBinding mBinding;
    private long mTodoId;

    public EditTodoDialogFragment() {
        // required empty constructor
    }

    public static EditTodoDialogFragment newInstance(long todoId) {
        EditTodoDialogFragment fragment = new EditTodoDialogFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TODO_ID, todoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTodoId = getArguments().getLong(ARG_TODO_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.todo_dialog, null, false);
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.edit_todo_dialog_title)
                .setView(mBinding.getRoot())
                .setPositiveButton(R.string.save, onSaveListener)
                .setNegativeButton(R.string.cancel, onCancelListener)
                .create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(EditTodoDialogViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getTodo(mTodoId).observe(this, new Observer<Todo>() {
            @Override
            public void onChanged(@Nullable Todo todo) {
                mBinding.setTodo(todo);
            }
        });
    }

    private DialogInterface.OnClickListener onSaveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            mViewModel.updateClicked(mBinding.getTodo());
        }
    };

    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // Do nothing
        }
    };
}
