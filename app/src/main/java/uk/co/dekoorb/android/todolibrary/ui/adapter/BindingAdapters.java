package uk.co.dekoorb.android.todolibrary.ui.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by ed on 04/01/18.
 */

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showGone(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleInvisible")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
