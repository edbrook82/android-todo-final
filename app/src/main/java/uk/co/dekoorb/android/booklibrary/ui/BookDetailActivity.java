package uk.co.dekoorb.android.booklibrary.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    @Override
    public void onSearchAmazon(String title) {
        String base_url = "https://www.amazon.co.uk/s/ref=nb_sb_noss_2?url=search-alias%3Dstripbooks&field-keywords=";
        try {
            String url = base_url + URLEncoder.encode(title, "utf-8");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(url));
            if(browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            }
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Could not access Amazon?", Toast.LENGTH_SHORT).show();
        }
    }
}
