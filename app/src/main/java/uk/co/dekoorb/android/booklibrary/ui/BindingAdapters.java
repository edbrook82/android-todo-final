package uk.co.dekoorb.android.booklibrary.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by ed on 04/01/18.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}