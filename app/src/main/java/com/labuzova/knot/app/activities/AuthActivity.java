package com.labuzova.knot.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.labuzova.knot.R;
import com.labuzova.knot.app.presenters.AuthPresenter;
import com.labuzova.knot.app.views.AuthView;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends MvpAppCompatActivity implements AuthView {

    @InjectPresenter
    AuthPresenter authPresenter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btn_sign_in_with_google)
    Button btnSingInWithGoogle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void lockUi() {
        btnSingInWithGoogle.setEnabled(false);
        progressBar.bringToFront();
    }

    @Override
    public void unLockUi() {
        btnSingInWithGoogle.setEnabled(true);
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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @OnClick(R.id.btn_sign_in_with_google)
    public void onClickSingInWithGoogle() {
        authPresenter.signInWithGoogle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        authPresenter.handlerActivityResult(requestCode, data);
    }
}
