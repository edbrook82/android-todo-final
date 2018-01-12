package uk.co.dekoorb.android.todolibrary;

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

import uk.co.dekoorb.android.todolibrary.db.AppDatabase;
import uk.co.dekoorb.android.todolibrary.db.dao.TodoDao;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;

/**
 * Created by c3469162 on 11/01/2018.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private TodoDao mTodoDao;
    private AppDatabase mDb;

    private static long sId = 1234;
    private static String sTitle = "Android Application";
    private static String sDescr = "Create an Android application to store a collection of Todo items. " +
            "It should provide CRUD operations. If possible use MVVM & write some tests.";
    private static boolean sCompleted = true;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mTodoDao = mDb.todoDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void addTodoToDb() throws Exception {
        Todo todo = createNewTodo();
        Todo todoFromDb = getValue(mTodoDao.getTodo(sId));
        assertThat(todoFromDb, equalTo(todo));
    }

    @Test
    public void updateTodoInDb() throws Exception {
        Todo todo = createNewTodo();
        todo.setComplete(false);
        mTodoDao.updateTodos(todo);
        Todo todoFromDb = getValue(mTodoDao.getTodo(sId));
        assertThat(todoFromDb, equalTo(todo));
    }

    @Test
    public void deleteTodoFromDb() throws Exception {
        Todo todo = createNewTodo();
        mTodoDao.deleteTodos(todo);
        Todo todoFromDb = getValue(mTodoDao.getTodo(sId));
        assertNull(todoFromDb);
    }

    private Todo createNewTodo() {
        Todo todo = new Todo(sTitle, sDescr, sCompleted);
        todo.setId(sId);
        mTodoDao.addTodos(todo);
        return todo;
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
