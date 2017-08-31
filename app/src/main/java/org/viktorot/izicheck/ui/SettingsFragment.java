package org.viktorot.izicheck.ui;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import org.viktorot.izicheck.R;
import org.viktorot.izicheck.Storage;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setSharedPreferenceName();
        addPreferencesFromResource(R.xml.preferences);
    }

    private void setSharedPreferenceName() {
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(Storage.NAME);
    }
}
