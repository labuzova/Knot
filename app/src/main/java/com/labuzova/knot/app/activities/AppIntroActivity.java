package com.labuzova.knot.app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.labuzova.knot.R;
import com.labuzova.knot.app.fragments.AppIntroEncyclopediaFragment;
import com.labuzova.knot.app.fragments.AppIntroLibraryFragment;
import com.labuzova.knot.app.fragments.AppIntroWelcomeFragment;
import com.labuzova.knot.app.servises.CheckCurrentUserService;

import java.util.Objects;

import androidx.annotation.Nullable;

public class AppIntroActivity extends AppIntro {

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), "com.labuzova.action.close")) {
                finish();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver(broadcastReceiver,
                new IntentFilter("com.labuzova.action.close"));


        addSlide(new AppIntroWelcomeFragment());
        addSlide(new AppIntroLibraryFragment());
        addSlide(new AppIntroEncyclopediaFragment());

        int colorBlue = getResources().getColor(R.color.colorPrimary);
        int colorGray = getResources().getColor(R.color.light_gray);

        showStatusBar(false);
        showSeparator(false);
        setIndicatorColor(colorBlue, colorGray);

        showSkipButton(false);
        setNextArrowColor(colorBlue);
        setColorDoneText(colorBlue);

        setDepthAnimation();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    private void startCheckCurrentUser() {
        startService(new Intent(this, CheckCurrentUserService.class));
    }

    @Override
    public void onDonePressed() {
        startCheckCurrentUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
