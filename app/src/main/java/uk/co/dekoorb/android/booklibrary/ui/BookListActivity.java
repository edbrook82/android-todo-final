package uk.co.dekoorb.android.booklibrary.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

public class BookListActivity extends AppCompatActivity
        implements BookListFragment.OnBookSelectedListener {

    private static final String TAG = "BookListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        if (savedInstanceState == null) {
            BookListFragment bookListFragment = BookListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.book_list_frame, bookListFragment, BookListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onBookSelected(Book book) {
        Log.d(TAG, "onBookSelected: " + book);
    }
}
