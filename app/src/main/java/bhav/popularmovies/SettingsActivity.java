package bhav.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * Created by bhav on 4/14/16 for the Popular Movies Project.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        //support for pre lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        final SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        getPreferenceScreen().findPreference("sort").setSummary(
                prefs.getString("sort", "Most Popular")
        );
        getPreferenceScreen().findPreference("sort").setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = prefs.edit();
                String temp = (String) newValue;
                editor.putString("sort", temp);
//                editor.putBoolean("doIneedtofetchdata", true);
                preference.setSummary(temp);
                DataFetcher df = new DataFetcher(SettingsActivity.this, false);
                df.execute();
                editor.apply();
                return true;
            }
        });
    }
}
