package uk.co.dekoorb.android.todolibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import uk.co.dekoorb.android.todolibrary.BaseApp;
import uk.co.dekoorb.android.todolibrary.db.AppDatabase;

/**
 * Created by c3469162 on 15/01/2018.
 */

public abstract class BaseViewModel extends AndroidViewModel {
    final AppDatabase mDb;

    public BaseViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
    }
}
