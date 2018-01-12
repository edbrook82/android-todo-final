package uk.co.dekoorb.android.todolibrary.ui.dialog;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import uk.co.dekoorb.android.todolibrary.R;
import uk.co.dekoorb.android.todolibrary.databinding.TodoDialogBinding;
import uk.co.dekoorb.android.todolibrary.viewmodel.AddTodoDialogViewModel;

/**
 * Created by edbrook on 07/01/2018.
 */

public class AddTodoDialogFragment extends DialogFragment {
    public static final String TAG = "AddTodoDialogFragment";

    private AddTodoDialogViewModel mViewModel;
    private TodoDialogBinding mBinding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.todo_dialog, null, false);
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.add_todo_dialog_title)
                .setView(mBinding.getRoot())
                .setPositiveButton(R.string.save, onSaveListener)
                .setNegativeButton(R.string.cancel, onCancelListener)
                .create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(AddTodoDialogViewModel.class);
        if (savedInstanceState == null) {
            mViewModel.reset();
        }
        mBinding.setTodo(mViewModel.getTodo());
    }

    private DialogInterface.OnClickListener onSaveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            mViewModel.addClicked();
        }
    };

    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            mViewModel.reset();
        }
    };
}
