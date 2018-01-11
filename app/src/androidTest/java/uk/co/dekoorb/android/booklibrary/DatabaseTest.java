package uk.co.dekoorb.android.booklibrary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import uk.co.dekoorb.android.booklibrary.db.AppDatabase;
import uk.co.dekoorb.android.booklibrary.db.dao.BookDao;
import uk.co.dekoorb.android.booklibrary.db.entity.Book;

/**
 * Created by c3469162 on 11/01/2018.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private BookDao mBookDao;
    private AppDatabase mDb;

    private static long sId = 1234;
    private static String sTitle = "The Colour of Magic";
    private static String sAuthor = "Terry Pratchett";
    private static String sDescr = "The story takes place on the Discworld, a planet-sized flat disc carried " +
            "through space on the backs of four huge elephants – Berilia, Tubul, Great T'Phon " +
            "and Jerakeen – who themselves stand on the shell of Great A'Tuin, a gigantic sea " +
            "turtle.";
    private static boolean sHasRead = true;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mBookDao = mDb.bookDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void addBookToDb() throws Exception {
        Book book = createNewBook();
        Book bookFromDb = getValue(mBookDao.getBook(sId));
        assertThat(bookFromDb, equalTo(book));
    }

    @Test
    public void updateBookInDb() throws Exception {
        Book book = createNewBook();
        book.setRead(false);
        mBookDao.updateBooks(book);
        Book bookFromDb = getValue(mBookDao.getBook(sId));
        assertThat(bookFromDb, equalTo(book));
    }

    @Test
    public void deleteBookFromDb() throws Exception {
        Book book = createNewBook();
        mBookDao.deleteBooks(book);
        Book bookFromDb = getValue(mBookDao.getBook(sId));
        assertNull(bookFromDb);
    }

    private Book createNewBook() {
        Book book = new Book(sTitle, sAuthor, sDescr, sHasRead);
        book.setId(sId);
        mBookDao.addBooks(book);
        return book;
    }

    // Required as Dao returns LiveData objects which are async loaded - i.e. if you try
    // to test the returned value straight away it will likely be null
    //
    // Code from Android Architecture Components example:
    // see --> https://github.com/googlesamples/android-architecture-components/blob/master/
    //              GithubBrowserSample/app/src/test-common/java/
    //              com/android/example/github/util/LiveDataTestUtil.java
    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
