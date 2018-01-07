package uk.co.dekoorb.android.booklibrary.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.databinding.BookDetailPagerFragmentBinding;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;
import uk.co.dekoorb.android.booklibrary.viewmodel.BookDetailPagerViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailPagerFragment extends Fragment {
    private static final String TAG = "BookDetailPagerFragment";

    private static final String ARG_BOOK_ID = "book_id";
    public static final String STATE_CURRENT_POS = "current_pos";

    private int mCurrentBook = -1;
    private long mBookId;
    private BookDetailPagerFragmentBinding mBinding;
    private BookPagerAdapter mPagerAdapter;
    private BookDetailPagerViewModel mViewModel;

    public BookDetailPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bookId Parameter 1.
     * @return A new instance of fragment BookDetailPagerFragment.
     */
    public static BookDetailPagerFragment newInstance(long bookId) {
        BookDetailPagerFragment fragment = new BookDetailPagerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mBookId = getArguments().getLong(ARG_BOOK_ID);
        } else {
            mCurrentBook = savedInstanceState.getInt(STATE_CURRENT_POS, 0);
        }
        Log.d(TAG, "onCreate: pos=" + mCurrentBook);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mPagerAdapter = new BookPagerAdapter(getChildFragmentManager());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.book_detail_pager_fragment, container, false);
        mBinding.bookViewpager.setAdapter(mPagerAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookDetailPagerViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getBooksList().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                if (books != null) {
                    // Don't like this -- there must be a better way of getting the position
                    // for the current book.
                    if (mCurrentBook == -1) {
                        for (int i = 0; i < books.size(); ++i) {
                            if (books.get(i).getId() == mBookId) {
                                mCurrentBook = i;
                                break;
                            }
                        }
                    }
                    Log.d(TAG, "onChanged: pos=" + mCurrentBook);
                    mPagerAdapter.setBooksList(books);
                    mBinding.bookViewpager.setCurrentItem(mCurrentBook);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCurrentBook = mBinding.bookViewpager.getCurrentItem();
        outState.putInt(STATE_CURRENT_POS, mCurrentBook);
        Log.d(TAG, "onSaveInstanceState: pos=" + mCurrentBook);
    }
}
