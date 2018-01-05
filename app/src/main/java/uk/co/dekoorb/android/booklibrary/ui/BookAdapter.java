package uk.co.dekoorb.android.booklibrary.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.R;
import uk.co.dekoorb.android.booklibrary.databinding.BookListItemBinding;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */

class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> mBookList;
    private BookClickCallback mBookClickCallback;

    BookAdapter(BookClickCallback callback) {
        mBookClickCallback = callback;
    }

    void setBookList(List<Book> bookList) {
        mBookList = bookList;
        notifyDataSetChanged();
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BookListItemBinding itemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.book_list_item,
                        parent, false);
        return new BookViewHolder(itemBinding, mBookClickCallback);
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

    static class BookViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        private BookListItemBinding mBinding;
        private BookClickCallback mCallback;

        BookViewHolder(BookListItemBinding itemBinding, BookClickCallback clickCallback) {
            super(itemBinding.getRoot());
            mCallback = clickCallback;
            mBinding = itemBinding;
            mBinding.bookListItem.setOnClickListener(this);
            mBinding.bookListItem.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCallback.onClick(mBinding.getBook());
        }

        @Override
        public boolean onLongClick(View v) {
            mCallback.onLongClick(mBinding.getBook());
            return true;
        }
    }
}
