package com.example.murtaza.listviewsample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

import java.io.Console;

/**
 * Created by Murtaza on 03-May-2016.
 */
public class ListViewSettingsActivity
        extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }


    public static class PrefsFragment extends PreferenceFragment {

        SwitchPreference switchPreference;
        final String LISTS_SHOWN = "Lists are shown";
        final String LISTS_NOT_SHOWN = "Lists are not shown";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.main_settings);

            switchPreference = (SwitchPreference) findPreference("pref_key_lists");

            // Default is ON
            if(switchPreference.isChecked())
                switchPreference.setSummary(LISTS_SHOWN);
            else
                switchPreference.setSummary(LISTS_NOT_SHOWN);

            switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference sp, Object obj) {
                    boolean isChecked = ((Boolean) obj).booleanValue();

                    if(isChecked)
                        sp.setSummary(LISTS_SHOWN);
                    else
                        sp.setSummary(LISTS_NOT_SHOWN);

                    return true;
                }
            });
        }

    }

}
