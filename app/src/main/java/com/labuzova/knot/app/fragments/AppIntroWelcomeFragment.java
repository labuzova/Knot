package com.labuzova.knot.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labuzova.knot.R;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;

public class AppIntroWelcomeFragment extends Fragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_intro_welcome, container, false);
    }
}
