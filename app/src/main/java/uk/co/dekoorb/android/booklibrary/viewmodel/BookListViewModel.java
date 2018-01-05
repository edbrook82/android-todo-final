package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by ed on 04/01/18.
 */

public class BookListViewModel extends AndroidViewModel {
    private static final String DATABASE_NAME = "books_database";

    private final AppDatabase mDb;
    private final LiveData<List<Book>> mBookList;

    public BookListViewModel(Application app) {
        super(app);
        mDb = ((BaseApp)app).getAppDb();
        mBookList = mDb.bookDao().getAllBooks();
    }

    public LiveData<List<Book>> getBooksList() {
        return this.mBookList;
    }

    public void addClicked() {
        final Book book = new Book();
        book.setRead(Math.random() >= 0.5);
        book.setTitle("The Colour of Magic - " + (int)(Math.random()*1000));
        book.setAuthor("Terry Pratchett");
        book.setDescription("The story takes place on the Discworld, a planet-sized flat disc carried through space on the backs of four huge elephants – Berilia, Tubul, Great T'Phon and Jerakeen – who themselves stand on the shell of Great A'Tuin, a gigantic sea turtle. The surface of the disc contains oceans and continents, and with them, civilisations, cities, forests and mountains.");

        new AsyncTask<Book, Void, Void>() {
            @Override
            protected Void doInBackground(Book... books) {
                mDb.bookDao().addBooks(books[0]);
                return null;
            }
        }.execute(book);
    }

    public void deleteBook(Book book) {
        new AsyncTask<Book, Void, Void>() {
            @Override
            protected Void doInBackground(Book... books) {
                mDb.bookDao().deleteBooks(books[0]);
                return null;
            }
        }.execute(book);
    }
}
