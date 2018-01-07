package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by edbrook on 07/01/2018.
 */

public class AddBookDialogViewModel extends AndroidViewModel {
    private final AppDatabase mDb;
    private Book mBook;

    public AddBookDialogViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
        mBook = new Book();
    }

    public Book getBook() {
        return mBook;
    }

    public boolean addClicked() {
        new AsyncTask<Book, Void, Void>() {
            @Override
            protected Void doInBackground(Book... books) {
                mDb.bookDao().addBooks(books[0]);
                return null;
            }
        }.execute(mBook);

        mBook = new Book();
        return true;
    }
}
