package uk.co.dekoorb.android.booklibrary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.db.entity.Book;
import uk.co.dekoorb.android.booklibrary.ui.BookDetailFragment;

/**
 * Created by edbrook on 07/01/2018.
 */

public class BookPagerAdapter extends FragmentStatePagerAdapter {

    private List<Book> mBooks;

    public BookPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setBooksList(List<Book> books) {
        mBooks = books;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        long bookId = mBooks.get(position).getId();
        return BookDetailFragment.newInstance(bookId);
    }

    @Override
    public int getCount() {
        return mBooks == null ? 0 : mBooks.size();
    }
}
