package uk.co.dekoorb.android.booklibrary.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import uk.co.dekoorb.android.booklibrary.BaseApp;
import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by edbrook on 07/01/2018.
 */

public class AddBookDialogViewModel extends AndroidViewModel {
    private final AppDatabase mDb;
    private final Book mBook;

    public AddBookDialogViewModel(Application app) {
        super(app);
        mDb = ((BaseApp) app).getAppDb();
        mBook = new Book();
    }

    public Book getBook() {
        return mBook;
    }

    public boolean addClicked() {
//        final Book book = new Book();
//        book.setRead(Math.random() >= 0.5);
//        book.setTitle("The Colour of Magic - " + (int)(Math.random()*1000));
//        book.setAuthor("Terry Pratchett");
//        book.setDescription("The story takes place on the Discworld, a planet-sized flat disc carried through space on the backs of four huge elephants – Berilia, Tubul, Great T'Phon and Jerakeen – who themselves stand on the shell of Great A'Tuin, a gigantic sea turtle. The surface of the disc contains oceans and continents, and with them, civilisations, cities, forests and mountains.");

        new AsyncTask<Book, Void, Void>() {
            @Override
            protected Void doInBackground(Book... books) {
                mDb.bookDao().addBooks(books[0]);
                return null;
            }
        }.execute(mBook);

        return true;
    }
}
