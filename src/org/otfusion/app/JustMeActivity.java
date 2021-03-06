/*
 * 	Start Here yo dawg!
 * JustMeActivity.java:
 * This class is part of the JustMe Android Application old comments stay:
 * 
 *  First App ever, so it will suck
 *  Just Me - Android app
 *  2011 Jose M. Salcido
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *  Basic Info:
 *  This app will connect to your or another's one server and will tell you
 *  if it is online or offline.
 *  
 *  If after, 5segs (5000ms) you dont get an answer then:
 *  
 *  It is offline or, you dont have internet. 
 */
package org.otfusion.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JustMeActivity extends Activity {

	private Toast toast;
	private Context mContexto;
	private JustMeEngine engine;
	private JustMeDialogs dialogs;
	private SharedPreferences mSharedPreferences;
	
	private Button button;
	private EditText textBox;
	private ImageView imagenTop;
	private TextView textoTop;
	
	private String mAddress;
	
	public static final int PREFERENCES_ID = Menu.FIRST;
	public static final int ABOUT_ID = PREFERENCES_ID + 1;
	public static final int EXIT_ID = ABOUT_ID + 1;
	
	public static final int ABOUT_DIALOG = 1;
	
	private static final String SHAREDPREFERENCES = "values";
	//private final String DEBUGTAG = "[DEBUG]";
	
	/**
	 * OnCreate method:
	 * this method will create the main layout and functions of the app
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		fillApp();
	}
	
	/**
	 * onStop method:
	 * this method will save the last used server to load again the last time
	 */
	@Override
	protected void onStop() {
		super.onStop();
		mSharedPreferences = getSharedPreferences(SHAREDPREFERENCES, 0);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString("address", mAddress);
		editor.commit();
		textoTop.setText(R.string.textoTop);
		imagenTop.setImageResource(R.drawable.neutral);
	}
	
	/*
	 * TODO: Corregir aqui ?, NO RECUERDO QUE IVA A CORREGIR LULZ!!! JAJAJAJAJAJAJA
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * This method will fill and prepare the views and ui.
	 */
	private void fillApp() {
		// Get context for other uses.
		mContexto = getApplicationContext();
		// Creates the JustMeEngine engine object to work with.
		engine = new JustMeEngine();
		// Layout objects
		imagenTop = (ImageView) findViewById(R.id.imagenTop);
		button = (Button) findViewById(R.id.button);
		textBox = (EditText) findViewById(R.id.name);
		textoTop = (TextView) findViewById(R.id.textoTop);
		
		// Set events and fill objects
		button.setOnClickListener(new OnlineButton());
		imagenTop.setImageResource(R.drawable.neutral);
		
		// SharedPreferences loaded.
		mSharedPreferences = getSharedPreferences(SHAREDPREFERENCES, 0);
		mAddress = mSharedPreferences.getString("address", mAddress);
		
		if(mAddress != null)
			textBox.setText(mAddress);
	}

	/**
	 * onCreateOptionsMenu method:
	 * this method will create the options menu for the app
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// group, id, order, int/char sequence
		menu.add(0, PREFERENCES_ID, 0, R.string.menuSettings);
		menu.add(0, ABOUT_ID, 1, R.string.menuAbout);
		menu.add(0, EXIT_ID, 2, R.string.menuExit);
		return true;
	}

	/**
	 * onOptionsItemSelected method:
	 * this will handle the options menu selections.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case PREFERENCES_ID:
			Intent preferencesIntent = new Intent(mContexto, org.otfusion.app.JustMePreferencesActivity.class);
			startActivity(preferencesIntent);
			return true;
		case ABOUT_ID:
			showDialog(ABOUT_DIALOG);
			return true;
		case EXIT_ID:
			this.finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * onCreateDialog method:
	 * this method will show some info about the application
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		dialogs = new JustMeDialogs(this);
		Dialog dialog;
		switch (id) {
		case ABOUT_DIALOG:
			dialog = dialogs.getDialog(ABOUT_DIALOG);
			break;
		default:
			dialog = null;
			break;
		}
		return dialog;
	}
	
	/**
	 * OnlineButton class:
	 * this class will handle the click listener of the button in the main UI.
	 */
	public class OnlineButton implements View.OnClickListener {
		public OnlineButton() {}
		public void onClick(View v) {
			button.setEnabled(false);
			mAddress = textBox.getText().toString();
			engine.setAddress(mAddress);
			engine.setPort(mAddress);
			mAddress = engine.getHost();
			boolean online = engine.isOnline();
			if (online) {
				// Do the online dance?
				imagenTop.setImageResource(R.drawable.online);
				textoTop.setText(mAddress + " - ONLINE");
				toast = Toast.makeText(mContexto, R.string.yes, Toast.LENGTH_LONG);
			} else {
				// Do the offline dance?
				imagenTop.setImageResource(R.drawable.offline);
				textoTop.setText(mAddress + " - OFFLINE");
				toast = Toast.makeText(mContexto, R.string.no, Toast.LENGTH_LONG);
			}
			toast.show();
			button.setEnabled(true);
		}
	}
}
