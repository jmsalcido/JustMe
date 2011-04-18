package org.otfusion.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class JustMePreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	private SharedPreferences mSharedPreferences;
	//private JustMePreferences mSettings;
	private JustMeNotification mNotifier;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharedPreferences = getPreferenceManager().getSharedPreferences();
		mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
		mNotifier = new JustMeNotification(this);
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("enableNotifications")) {
			mNotifier.update();
		}
	}
}
