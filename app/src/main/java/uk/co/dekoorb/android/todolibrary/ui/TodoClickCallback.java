package uk.co.dekoorb.android.todolibrary.ui;

import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by ed on 04/01/18.
 */

public interface TodoClickCallback {
    void onClick(Todo todo);
    void onLongClick(Todo todo);
}
