package uk.co.dekoorb.android.todolibrary.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import uk.co.dekoorb.android.todolibrary.R;

/**
 * Created by c3469162 on 11/01/2018.
 */

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private Context mContext;

    private ColorDrawable background = new ColorDrawable();
    private int backgroundColour;

    public SwipeToDeleteCallback(Context context) {
        super(0, ItemTouchHelper.LEFT);
        mContext = context;
        backgroundColour = ContextCompat.getColor(mContext,R.color.colorDelete);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        // Red delete background
        background.setColor(backgroundColour);
        background.setBounds(itemView.getRight() + (int)dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
