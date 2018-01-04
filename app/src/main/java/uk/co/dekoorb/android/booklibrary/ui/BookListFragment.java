package uk.co.dekoorb.android.booklibrary.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.databinding.BookListFragmentBinding;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;
import uk.co.dekoorb.android.booklibrary.viewmodel.BookListViewModel;

/**
 * BookListFragment is a {@link Fragment} to display a list of books.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {

    public static final String TAG = "BookListFragment";

    private BookAdapter mBookAdapter;
    private BookListFragmentBinding mBinding;

    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance() {
        BookListFragment fragment = new BookListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBookAdapter = new BookAdapter(mBookClickCallback);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.book_list_fragment, container, false);
        mBinding.setIsLoading(true);
        mBinding.booksList.setAdapter(mBookAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final BookListViewModel bookListViewModel =
                ViewModelProviders.of(this).get(BookListViewModel.class);

        subscribeUI(bookListViewModel);
    }

    private void subscribeUI(BookListViewModel viewModel) {
        viewModel.getBooksList().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                if (books != null) {
                    mBinding.setIsLoading(false);
                    mBookAdapter.setBookList(books);
                } else {
                    mBinding.setIsLoading(true);
                }
                mBinding.executePendingBindings();
            }
        });
    }

    private final BookClickCallback mBookClickCallback = new BookClickCallback() {
        @Override
        public void onClick(Book book) {

        }

        @Override
        public void onLongClick(Book book) {

        }
    };

}
