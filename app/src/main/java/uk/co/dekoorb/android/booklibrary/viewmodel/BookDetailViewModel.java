package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;


/**
 * Created by ed on 05/01/18.
 */

public class BookDetailViewModel extends AndroidViewModel {
    private final AppDatabase mDb;

    public BookDetailViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
    }

    public LiveData<Book> getBook(long id) {
        return mDb.bookDao().getBook(id);
    }

    public void toggleRead(Book book) {
        book.setRead(!book.isRead());
        new AsyncTask<Book, Void, Void>() {
            @Override
            protected Void doInBackground(Book... books) {
                mDb.bookDao().updateBooks(books);
                return null;
            }
        }.execute(book);
    }

    public void deleteBook(Book book) {
        new AsyncTask<Book, Void, Void>() {
            @Override
            protected Void doInBackground(Book... books) {
                mDb.bookDao().deleteBooks(books);
                return null;
            }
        }.execute(book);
    }
}
