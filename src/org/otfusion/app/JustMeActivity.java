/*
 * 	Start Here yo dawg!
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
 *  ALSO: Javadoc style is for little crying girls or Im just too lazy to build and write it.
 * 
 * 
 *  Basic Info:
 *  This app will ping your or another's one server and will tell you
 *  if it is online or offline.
 *  
 *  If after, 5segs (5000ms) you dont get an answer then:
 *  
 *  It is offline or, you dont have internet.
 *  
 *  Also, the game.
 * 
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
import android.widget.Toast;

public class JustMeActivity extends Activity {

	public static Toast toast;
	public static Context mContexto;
	public static JustMeEngine engine;
	public static JustMeDialogs dialogs;
	public static SharedPreferences mSharedPreferences;
	
	public static Button button;
	public static EditText textBox;
	
	public static String mAddress;
	public static final String SHAREDPREFERENCES = "values";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Get context for other uses.
		mContexto = getApplicationContext();
		
		// SharedPreferences loaded.
		mSharedPreferences = getSharedPreferences(SHAREDPREFERENCES, 0);
		mAddress = mSharedPreferences.getString("address", mAddress);
		
		// Creates the JustMeEngine engine object to work with.
		engine = new JustMeEngine(this);
		
		// Layout objects
		button = (Button) findViewById(R.id.button);
		textBox = (EditText) findViewById(R.id.name);
		button.setOnClickListener(new OnlineButton());
		
		if(mAddress != null)
			textBox.setText(mAddress);
	}
	

	@Override
	protected void onStop() {
		super.onStop();
		mSharedPreferences = getSharedPreferences(SHAREDPREFERENCES, 0);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString("address", mAddress);
		editor.commit();
	}

	/*
	 * TODO: Corregir aqui ?, NO RECUERDO QUE IVA A CORREGIR LULZ!!! JAJAJAJAJAJAJA
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	// MENU OPTIONS
	public static final int PREFERENCES_ID = Menu.FIRST;
	public static final int ABOUT_ID = PREFERENCES_ID + 1;
	public static final int EXIT_ID = ABOUT_ID + 1;

	// DIALOGS
	public static final int ABOUT_DIALOG = 1;

	/*
	 * onCreateOptionsMenu menu for the application
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

	/*
	 * onOptionsItemSelected selecting an option
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

	/*
	 * About Dialog - Showing some info about the application like author's
	 * blog, and other stupid things.
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
	
	/*
	 * Handling the click on the button.
	 */
	public class OnlineButton implements View.OnClickListener {
		public OnlineButton() {}
		@Override
		public void onClick(View v) {
			mAddress = textBox.getText().toString();
			engine.setAddress(mAddress);
			engine.setPort(mAddress);
			boolean online = engine.isOnline();
			if (online) {
				// ImageView img = (ImageView) findViewById(R.id.imagenTop);
				// img.setImageResource(R.id.imageOnline);
				toast = Toast.makeText(mContexto, R.string.yes, Toast.LENGTH_LONG);
			} else {
				// img offline
				toast = Toast.makeText(mContexto, R.string.no, Toast.LENGTH_LONG);
			}
			toast.show();
		}
	}
}
