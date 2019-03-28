package com.labuzova.knot.app.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.labuzova.knot.app.activities.AuthActivity;
import com.labuzova.knot.app.di.App;
import com.labuzova.knot.app.views.TagCollectionView;

import javax.inject.Inject;

@InjectViewState
public class TagCollectionPresenter extends MvpPresenter<TagCollectionView> {

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    GoogleSignInClient googleSignInClient;

    public TagCollectionPresenter() {
        App.getAppComponent().injectsTagCollectionPresenter(this);
    }

    public void singOut() {
        firebaseAuth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            getViewState().showMessage("Успешный выход");
            getViewState().startNextActivity(new AuthActivity());
        });
    }
}
