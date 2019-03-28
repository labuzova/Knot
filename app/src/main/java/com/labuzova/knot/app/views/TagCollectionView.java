package com.labuzova.knot.app.views;

import android.app.Activity;

import com.arellomobile.mvp.MvpView;

public interface TagCollectionView extends MvpView {

    void showMessage(String message);

    void startNextActivity(Activity activity);
}
