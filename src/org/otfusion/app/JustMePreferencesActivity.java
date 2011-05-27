/*
 * JustMePreferencesActivity.java:
 * this class is part of JustMe Android Application
 */

package org.otfusion.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class JustMePreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	private SharedPreferences mSharedPreferences;
	private JustMeNotification mNotifier;
	
	/**
	 * OnCreate() method:
	 * this method inflates the UI and some other things.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharedPreferences = getPreferenceManager().getSharedPreferences();
		mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
		mNotifier = new JustMeNotification(this);
		addPreferencesFromResource(R.xml.settings);
	}

	/**
	 * onSharedPreferenceChanged() method:
	 * this method will implement the OnSharedPreferenceChange listener
	 * that will update the notifier when the sharedpreference is changed.
	 */
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (key.equals("enableNotifications")) {
			mNotifier.update();
		}
	}
}
