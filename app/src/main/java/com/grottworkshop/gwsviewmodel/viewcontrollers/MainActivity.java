package com.grottworkshop.gwsviewmodel.viewcontrollers;

import android.os.Bundle;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.OnClick;


import com.grottworkshop.gwsviewmodel.R;

/**
 * Created by fgrott on 3/31/2015.
 */
public class MainActivity extends ProjectBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.root_content, new UserListFragment(), "user-list-fragment").commit();
        }
    }

    @OnClick(R.id.button1)
    public void onOpenFragmentClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.root_content, new EmptyFragment(), "empty-fragment").addToBackStack(null).commit();
    }

    @OnClick(R.id.button2)
    public void closeClicked() {
        finish();
    }
}
