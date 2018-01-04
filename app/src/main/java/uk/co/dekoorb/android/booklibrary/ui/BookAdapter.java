package uk.co.dekoorb.android.booklibrary.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.databinding.BookListItemBinding;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> mBookList;
    private BookClickCallback mBookClickCallback;

    public BookAdapter(BookClickCallback callback) {
        mBookClickCallback = callback;
    }

    public void setBookList(List<Book> bookList) {
        mBookList = bookList;
        notifyDataSetChanged();
//        Should ideally use something like this instead for performance improvement
//        if (mBookList == null) {
//            mBookList = bookList;
//            notifyItemRangeChanged(0, bookList.size());
//        } else {
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(...);
//            mBookList = bookList;
//            result.dispatchUpdatesTo(this);
//        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BookListItemBinding itemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.book_list_item,
                        parent, false);
        return new BookViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.mBinding.setBook(mBookList.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBookList == null ? 0 : mBookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private BookListItemBinding mBinding;

        BookViewHolder(BookListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mBinding = itemBinding;
        }
    }
}
