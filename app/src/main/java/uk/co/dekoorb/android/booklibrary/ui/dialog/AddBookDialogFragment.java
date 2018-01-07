package uk.co.dekoorb.android.booklibrary.ui.dialog;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.databinding.AddBookDialogBinding;
import uk.co.dekoorb.android.booklibrary.viewmodel.AddBookDialogViewModel;

/**
 * Created by edbrook on 07/01/2018.
 */

public class AddBookDialogFragment extends DialogFragment {

    private AddBookDialogViewModel mViewModel;
    private AddBookDialogBinding mBinding;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.add_book_dialog, null, false);
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(R.string.add_book_dialog_title)
                .setView(mBinding.getRoot())
                .setPositiveButton(R.string.save, onSaveListener)
                .create();
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddBookDialogViewModel.class);
        mBinding.setBook(mViewModel.getBook());
    }

    private DialogInterface.OnClickListener onSaveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            mViewModel.addClicked();
        }
    };
}
