package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by edbrook on 07/01/2018.
 */

public class EditBookDialogViewModel extends AndroidViewModel {
    private final AppDatabase mDb;

    public EditBookDialogViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
    }

    public LiveData<Book> getBook(long id) {
        return mDb.bookDao().getBook(id);
    }

    public boolean updateClicked(Book book) {
        new AsyncTask<Book, Void, Void>() {
            @Override
            protected Void doInBackground(Book... books) {
                mDb.bookDao().updateBooks(books[0]);
                return null;
            }
        }.execute(book);
        return true;
    }
}
