package com.labuzova.knot.app.presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.labuzova.knot.app.activities.TagCollectionActivity;
import com.labuzova.knot.app.di.App;
import com.labuzova.knot.app.views.AuthView;

import javax.inject.Inject;

@InjectViewState
public class AuthPresenter extends MvpPresenter<AuthView> {

    private static final int RC_SIGN_IN = 9001;

    @Inject
    GoogleSignInOptions googleSignInOptions;
    @Inject
    FirebaseAuth firebaseAuth;
    @Inject
    GoogleSignInClient googleSignInClient;

    public AuthPresenter() {
        App.getAppComponent().injectsAuthPresenter(this);
    }

    public void signInWithGoogle() {
        getViewState().lockUi();
        getViewState().showProgress();
        Intent signInIntent = googleSignInClient.getSignInIntent();
        getViewState().startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handlerActivityResult(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                getViewState().hideProgress();
                getViewState().unLockUi();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        getViewState().showMessage("Успешный вход");
                        getViewState().startNextActivity(new TagCollectionActivity());

                    } else {
                        // If sign in fails, display a message to the user
                        getViewState().showMessage("Не удалось войти");
                        getViewState().hideProgress();
                        getViewState().unLockUi();
                    }
                });
    }

    public void singInWithFacebook() {
        getViewState().lockUi();
        getViewState().showProgress();
    }

}
