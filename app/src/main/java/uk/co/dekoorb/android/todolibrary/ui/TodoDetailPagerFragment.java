package uk.co.dekoorb.android.todolibrary.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import uk.co.dekoorb.android.todolibrary.R;
import uk.co.dekoorb.android.todolibrary.databinding.TodoDetailPagerFragmentBinding;
import uk.co.dekoorb.android.todolibrary.db.entity.Todo;
import uk.co.dekoorb.android.todolibrary.ui.adapter.TodoPagerAdapter;
import uk.co.dekoorb.android.todolibrary.viewmodel.TodoDetailPagerViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodoDetailPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodoDetailPagerFragment extends Fragment {
    private static final String ARG_TODO_ID = "todo_id";
    public static final String STATE_CURRENT_POS = "current_pos";

    private long mTodoId;
    private int mCurrentPagerPos = -1;

    private TodoDetailPagerFragmentBinding mBinding;
    private TodoPagerAdapter mPagerAdapter;
    private TodoDetailPagerViewModel mViewModel;

    public TodoDetailPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param todoId Parameter 1.
     * @return A new instance of fragment TodoDetailPagerFragment.
     */
    public static TodoDetailPagerFragment newInstance(long todoId) {
        TodoDetailPagerFragment fragment = new TodoDetailPagerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TODO_ID, todoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mTodoId = getArguments().getLong(ARG_TODO_ID);
        } else {
            mCurrentPagerPos = savedInstanceState.getInt(STATE_CURRENT_POS, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPagerAdapter = new TodoPagerAdapter(getChildFragmentManager());
        mBinding = DataBindingUtil.inflate(inflater, R.layout.todo_detail_pager_fragment, container, false);
        mBinding.todoViewpager.setAdapter(mPagerAdapter);
        mBinding.todoViewpager.addOnPageChangeListener(pageChangeListener);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TodoDetailPagerViewModel.class);
        subscribeUI();
    }

    private void subscribeUI() {
        mViewModel.getTodosList().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                if (todos != null) {
                    if (mCurrentPagerPos == -1) {
                        for (int i = 0; i < todos.size(); ++i) {
                            if (todos.get(i).getId() == mTodoId) {
                                mCurrentPagerPos = i;
                                break;
                            }
                        }
                    }
                    mPagerAdapter.setTodosList(todos);
                    mBinding.todoViewpager.setCurrentItem(mCurrentPagerPos, false);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCurrentPagerPos = mBinding.todoViewpager.getCurrentItem();
        outState.putInt(STATE_CURRENT_POS, mCurrentPagerPos);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            mCurrentPagerPos = position;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}
