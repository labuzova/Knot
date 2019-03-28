package com.labuzova.knot.app.servises;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.labuzova.knot.app.activities.AuthActivity;
import com.labuzova.knot.app.activities.TagCollectionActivity;
import com.labuzova.knot.app.di.App;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class CheckCurrentUserService extends Service {

    @Inject
    FirebaseAuth firebaseAuth;

    private FirebaseUser currentUser;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.getAppComponent().injectsCheckCurrentUser(this);
        currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkCurrentUser(currentUser);
        stopSelf();
        return START_NOT_STICKY;
    }

    void checkCurrentUser(FirebaseUser currentUser) {
        if (currentUser != null) {
            showMessage("Вы вошли как: " + currentUser.getDisplayName());
            startActivity(new TagCollectionActivity());
        } else {
            showMessage("Необходимо авторизироваться");
            startActivity(new AuthActivity());
        }
    }

    public void startActivity(Activity activity) {
        Intent dialogIntent = new Intent(this, activity.getClass());
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);

        sendBroadcast(new Intent("com.labuzova.action.close"));
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
