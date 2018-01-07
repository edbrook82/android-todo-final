package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by edbrook on 07/01/2018.
 */

public class BookDetailPagerViewModel extends AndroidViewModel {
    private final AppDatabase mDb;
    private final LiveData<List<Book>> mBookList;

    public BookDetailPagerViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
        mBookList = mDb.bookDao().getAllBooks();
    }

    public LiveData<List<Book>> getBooksList() {
        return this.mBookList;
    }
}
