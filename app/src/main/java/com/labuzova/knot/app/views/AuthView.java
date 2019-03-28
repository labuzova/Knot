package com.labuzova.knot.app.views;

import android.app.Activity;
import android.content.Intent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AuthView extends MvpView {

    void showProgress();

    void hideProgress();

    void lockUi();

    void unLockUi();

    void showMessage(String message);

    void startNextActivity(Activity activity);

    void startActivityForResult(Intent intent, int requestCode);

}
