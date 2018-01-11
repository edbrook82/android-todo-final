package uk.co.dekoorb.android.booklibrary.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.dekoorb.android.booklibrary.R;

public class BookDetailActivity extends AppCompatActivity implements BookDetailFragment.BookDetailFragmentActions {

    public static final String EXTRA_BOOK_ID = "BookDetailActivity.EXTRA_BOOK_ID";

    public static Intent getIntent(Context context, long bookId) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(EXTRA_BOOK_ID, bookId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if (savedInstanceState == null) {
            long bookId = getIntent().getLongExtra(EXTRA_BOOK_ID, -1);
            if (bookId != -1) {
                BookDetailPagerFragment fragment = BookDetailPagerFragment.newInstance(bookId);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.book_detail_frame, fragment, BookDetailFragment.TAG)
                        .commit();
            }
        }
    }

    @Override
    public void onBookDeleted() {
        onBackPressed();
    }
}
