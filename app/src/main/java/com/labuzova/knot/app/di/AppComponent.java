package com.labuzova.knot.app.di;

import android.content.Context;

import com.labuzova.knot.app.activities.AuthActivity;
import com.labuzova.knot.app.presenters.AuthPresenter;
import com.labuzova.knot.app.presenters.TagCollectionPresenter;
import com.labuzova.knot.app.servises.CheckCurrentUserService;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ContextModule.class, FirebaseModule.class})
@Singleton
public interface AppComponent {

    Context getContext();

    void injectsCheckCurrentUser(CheckCurrentUserService checkCurrentUserService);

    void injectsAuthActivity(AuthActivity authActivity);

    void injectsAuthPresenter(AuthPresenter authPresenter);

    void injectsTagCollectionPresenter(TagCollectionPresenter tagCollectionPresenter);
}
