package com.labuzova.knot.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.labuzova.knot.R;
import com.labuzova.knot.app.fragments.AddNewItemFragment;
import com.labuzova.knot.app.fragments.EncyclopediaFragment;
import com.labuzova.knot.app.fragments.HomeFragment;
import com.labuzova.knot.app.presenters.TagCollectionPresenter;
import com.labuzova.knot.app.views.TagCollectionView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TagCollectionActivity extends MvpAppCompatActivity implements TagCollectionView {

    @InjectPresenter
    TagCollectionPresenter tagCollectionPresenter;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_collection);
        ButterKnife.bind(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.action_add_new_item:
                        selectedFragment = new AddNewItemFragment();
                        break;
                    case R.id.action_encyclopedia:
                        selectedFragment = new EncyclopediaFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startNextActivity(Activity activity) {
        startActivity(new Intent(this, activity.getClass()));
        finish();
    }

    public void signOut() {
        tagCollectionPresenter.singOut();
    }
}
