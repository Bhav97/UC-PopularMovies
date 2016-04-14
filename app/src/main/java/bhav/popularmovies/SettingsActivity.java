package bhav.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by bhav on 4/14/16 for the Popular Movies Project.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        final SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        getPreferenceScreen().findPreference("sort").setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = prefs.edit();
                String temp = (String) newValue;
                editor.putString("sort",temp.toLowerCase().replace(" ",""));
                preference.setSummary(temp);
                editor.apply();
                return true;
            }
        });
    }
}
