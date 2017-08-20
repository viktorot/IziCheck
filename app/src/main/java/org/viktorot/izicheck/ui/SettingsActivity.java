package org.viktorot.izicheck.ui;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import org.viktorot.izicheck.R;
import org.viktorot.izicheck.Storage;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(Storage.NAME);

        addPreferencesFromResource(R.xml.preferences);
    }
}
