package com.labuzova.knot.app.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.labuzova.knot.app.servises.CheckCurrentUserService;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static String PREFERENCES_FIRST_START = "isFirstRun";

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "com.labuzova.action.close")) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver(broadcastReceiver,
                new IntentFilter("com.labuzova.action.close"));

        SharedPreferences activityPreferences = getPreferences(Activity.MODE_PRIVATE);
        boolean isFirstRun = activityPreferences.getBoolean(PREFERENCES_FIRST_START, true);

        if (isFirstRun) {
            activityPreferences.edit()
                    .putBoolean(PREFERENCES_FIRST_START, false).apply();

            Intent intent = new Intent(this, AppIntroActivity.class);
            startActivity(intent);
            finish();
        } else {
            startService(new Intent(this, CheckCurrentUserService.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
