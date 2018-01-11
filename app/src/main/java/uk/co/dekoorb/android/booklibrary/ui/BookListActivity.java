package uk.co.dekoorb.android.booklibrary.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Random;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;
import uk.co.dekoorb.android.booklibrary.ui.dialog.AddBookDialogFragment;

public class BookListActivity extends AppCompatActivity
        implements BookListFragment.BookListActionsListener {

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
        long bookId = book.getId();
        Intent detailIntent = BookDetailActivity.getIntent(this, bookId);
        startActivity(detailIntent);
    }

    ///// ================ THE FOLLOWING CODE IS FOR DEMO/TESTING PURPOSES ONLY ================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                createDummyData();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void createDummyData() {
        final AppDatabase db = ((BaseApp)getApplication()).getAppDb();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (int i=0; i<10; ++i) {
                    Book book = new Book(i + " " + genString(2), genString(3), genString(40), Math.random()>0.5);
                    db.bookDao().addBooks(book);
                }
                return null;
            }
        }.execute();
    }

    private String genString(int len) {
        String ltrs = "abcdefghijklmnopqrstuvwxyz";
        int ltrsLen = ltrs.length();
        Random r = new Random();
        int n = r.nextInt(len) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<n; ++i) {
            int l = r.nextInt(10) + 1;
            for (int j=0; j<l; ++j) {
                sb.append(ltrs.charAt(r.nextInt(ltrsLen)));
            }
            sb.append(" ");
        }
        return sb.toString();
    }
    ///// ================ ================ ================ ================
}
