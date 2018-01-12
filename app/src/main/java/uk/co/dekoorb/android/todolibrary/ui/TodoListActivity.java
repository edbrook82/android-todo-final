package uk.co.dekoorb.android.todolibrary.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Random;

import uk.co.dekoorb.android.todolibrary.BaseApp;
import uk.co.dekoorb.android.todolibrary.R;
import uk.co.dekoorb.android.todolibrary.db.AppDatabase;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;
import uk.co.dekoorb.android.todolibrary.ui.dialog.AddTodoDialogFragment;

public class TodoListActivity extends AppCompatActivity
        implements TodoListFragment.TodoListActionsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        if (savedInstanceState == null) {
            TodoListFragment todoListFragment = TodoListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.todo_list_frame, todoListFragment, TodoListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onTodoSelected(Todo todo) {
        long todoId = todo.getId();
        Intent detailIntent = TodoDetailActivity.getIntent(this, todoId);
        startActivity(detailIntent);
    }

    ///// ================ THE FOLLOWING CODE IS FOR DEMO/TESTING PURPOSES ONLY ================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                createDummyData();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void createDummyData() {
        final AppDatabase db = ((BaseApp)getApplication()).getAppDb();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (int i=0; i<10; ++i) {
                    Todo todo = new Todo(i + " " + genString(2), genString(3), genString(40), Math.random()>0.5);
                    db.todoDao().addTodos(todo);
                }
                return null;
            }
        }.execute();
    }

    private String genString(int len) {
        String ltrs = "abcdefghijklmnopqrstuvwxyz";
        int ltrsLen = ltrs.length();
        Random r = new Random();
        int n = r.nextInt(len) + 1;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<n; ++i) {
            int l = r.nextInt(10) + 1;
            for (int j=0; j<l; ++j) {
                sb.append(ltrs.charAt(r.nextInt(ltrsLen)));
            }
            sb.append(" ");
        }
        return sb.toString();
    }
    ///// ================ ================ ================ ================
}
