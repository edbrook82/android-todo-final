package uk.co.dekoorb.android.booklibrary.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.databinding.BookDetailPagerFragmentBinding;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;
import uk.co.dekoorb.android.booklibrary.ui.adapter.BookPagerAdapter;
import uk.co.dekoorb.android.booklibrary.viewmodel.BookDetailPagerViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailPagerFragment extends Fragment {
    private static final String ARG_BOOK_ID = "book_id";
    public static final String STATE_CURRENT_POS = "current_pos";

    private long mBookId;
    private int mCurrentPagerPos = -1;

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
            mCurrentPagerPos = savedInstanceState.getInt(STATE_CURRENT_POS, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPagerAdapter = new BookPagerAdapter(getChildFragmentManager());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.book_detail_pager_fragment, container, false);
        mBinding.bookViewpager.setAdapter(mPagerAdapter);
        mBinding.bookViewpager.addOnPageChangeListener(pageChangeListener);
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
                    if (mCurrentPagerPos == -1) {
                        for (int i = 0; i < books.size(); ++i) {
                            if (books.get(i).getId() == mBookId) {
                                mCurrentPagerPos = i;
                                break;
                            }
                        }
                    }
                    mPagerAdapter.setBooksList(books);
                    mBinding.bookViewpager.setCurrentItem(mCurrentPagerPos, false);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCurrentPagerPos = mBinding.bookViewpager.getCurrentItem();
        outState.putInt(STATE_CURRENT_POS, mCurrentPagerPos);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            mCurrentPagerPos = position;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}
