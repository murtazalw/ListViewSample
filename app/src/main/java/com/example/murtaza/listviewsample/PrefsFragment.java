package com.example.murtaza.listviewsample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Murtaza on 06-May-2016.
 */
public class PrefsFragment extends PreferenceFragment {

    SwitchPreference sp;
    final String LISTS_SHOWN = "Lists are shown";
    final String LISTS_NOT_SHOWN = "Lists are not shown";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.main_settings);

        sp = (SwitchPreference) findPreference("pref_key_lists");

        // Default is ON
        if(sp.isChecked())
            sp.setSummary(LISTS_SHOWN);
        else
            sp.setSummary(LISTS_NOT_SHOWN);

        sp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference arg0, Object obj) {
                boolean isChecked = ((Boolean) obj).booleanValue();

                if(isChecked)
                    arg0.setSummary(LISTS_SHOWN);
                else
                    arg0.setSummary(LISTS_NOT_SHOWN);

                return true;
            }
        });
    }

}
