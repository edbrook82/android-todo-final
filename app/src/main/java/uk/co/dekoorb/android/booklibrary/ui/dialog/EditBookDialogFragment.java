package uk.co.dekoorb.android.booklibrary.ui.dialog;

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

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.databinding.BookDialogBinding;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;
import uk.co.dekoorb.android.booklibrary.viewmodel.EditBookDialogViewModel;

/**
 * Created by edbrook on 08/01/2018.
 */

public class EditBookDialogFragment extends DialogFragment {
    public static final String TAG = "EditBookDialogFragment";

    public static final String ARG_BOOK_ID = "book_id";

    private EditBookDialogViewModel mViewModel;
    private BookDialogBinding mBinding;
    private long mBookId;

    public EditBookDialogFragment() {
        // required empty constructor
    }

    public static EditBookDialogFragment newInstance(long bookId) {
        EditBookDialogFragment fragment = new EditBookDialogFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBookId = getArguments().getLong(ARG_BOOK_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.book_dialog, null, false);
        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.edit_book_dialog_title)
                .setView(mBinding.getRoot())
                .setPositiveButton(R.string.save, onSaveListener)
                .setNegativeButton(R.string.cancel, onCancelListener)
                .create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(EditBookDialogViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getBook(mBookId).observe(this, new Observer<Book>() {
            @Override
            public void onChanged(@Nullable Book book) {
                mBinding.setBook(book);
            }
        });
    }

    private DialogInterface.OnClickListener onSaveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            mViewModel.updateClicked(mBinding.getBook());
        }
    };

    private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            // Do nothing
        }
    };
}
