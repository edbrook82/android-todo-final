package uk.co.dekoorb.android.booklibrary.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uk.co.dekoorb.android.booklibrary.R;

public class BookDetailActivity extends AppCompatActivity {

    public static final String EXTRA_BOOK_ID = "BookDetailActivity.EXTRA_BOOK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if (savedInstanceState == null) {
            int bookId = getIntent().getIntExtra(EXTRA_BOOK_ID, -1);
            if (bookId != -1) {
                BookDetailFragment fragment = BookDetailFragment.newInstance(bookId);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.book_detail_frame, fragment, BookDetailFragment.TAG)
                        .commit();
            }
        }
    }
}
