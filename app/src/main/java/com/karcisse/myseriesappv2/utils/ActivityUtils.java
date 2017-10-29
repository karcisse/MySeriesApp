package com.karcisse.myseriesappv2.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.karcisse.myseriesappv2.R;

public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_frame, fragment);
        transaction.commit();
    }

    public static void replaceFragmentAndAddToBackstack(@NonNull FragmentManager fragmentManager,
                                                        @NonNull Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();

    }
}
