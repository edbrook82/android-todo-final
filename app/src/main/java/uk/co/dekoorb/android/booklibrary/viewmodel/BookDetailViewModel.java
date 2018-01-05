package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;

/**
 * Created by ed on 05/01/18.
 */

public class BookDetailViewModel extends AndroidViewModel {
    private final AppDatabase mDb;

    BookDetailViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
    }
}
