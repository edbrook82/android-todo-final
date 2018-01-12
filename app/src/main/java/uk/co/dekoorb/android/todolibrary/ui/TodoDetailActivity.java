package uk.co.dekoorb.android.todolibrary.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import uk.co.dekoorb.android.todolibrary.R;

public class TodoDetailActivity extends AppCompatActivity implements TodoDetailFragment.TodoDetailFragmentActions {

    public static final String EXTRA_TODO_ID = "TodoDetailActivity.EXTRA_TODO_ID";

    public static Intent getIntent(Context context, long todoId) {
        Intent intent = new Intent(context, TodoDetailActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        if (savedInstanceState == null) {
            long todoId = getIntent().getLongExtra(EXTRA_TODO_ID, -1);
            if (todoId != -1) {
                TodoDetailPagerFragment fragment = TodoDetailPagerFragment.newInstance(todoId);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.todo_detail_frame, fragment, TodoDetailFragment.TAG)
                        .commit();
            }
        }
    }

    @Override
    public void onTodoDeleted() {
        onBackPressed();
    }

    @Override
    public void onSearchGoogle(String title) {
        String base_url = "https://www.google.co.uk/search?q=";
        try {
            String url = base_url + URLEncoder.encode(title, "utf-8");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(url));
            if(browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            }
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Could not access Google?", Toast.LENGTH_SHORT).show();
        }
    }
}
