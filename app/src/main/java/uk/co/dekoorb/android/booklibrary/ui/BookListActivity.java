package uk.co.dekoorb.android.booklibrary.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.dekoorb.android.booklibrary.R;

public class BookListActivity extends AppCompatActivity {

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
}
