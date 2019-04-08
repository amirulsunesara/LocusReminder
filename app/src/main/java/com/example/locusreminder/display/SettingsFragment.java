package com.example.locusreminder.display;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.locusreminder.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // floating action button for settings fragment
        FloatingActionButton floatingActionButton = ((ViewReminders) getActivity()).getFloatingActionButton();
        floatingActionButton.hide();
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }


}
