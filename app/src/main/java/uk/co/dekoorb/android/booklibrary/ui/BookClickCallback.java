package uk.co.dekoorb.android.booklibrary.ui;

import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */

public interface BookClickCallback {
    void onClick(Book book);
    void onLongClick(Book book);
}
