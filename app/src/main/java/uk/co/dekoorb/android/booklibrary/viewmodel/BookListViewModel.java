package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */

public class BookListViewModel extends AndroidViewModel {
    private static final String DATABASE_NAME = "books_database";

    private final AppDatabase mDb;
    private final LiveData<List<Book>> mBookList;

    public BookListViewModel(Application app) {
        super(app);
        mDb = ((BaseApp)app).getAppDb();
        mBookList = mDb.bookDao().getAllBooks();
    }

    public LiveData<List<Book>> getBooksList() {
        return this.mBookList;
    }
}
