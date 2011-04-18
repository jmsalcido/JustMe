/*
 * Just Me?
 * Jose M. Salcido Aguilar
 * 
 * JustMeNotification.java:
 * This class will do the work of the NotificationManager
 */

package org.otfusion.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class JustMeNotification {

	public final static int NOTIFICATION_ID = 1;
	
	private Context mContext;
	private NotificationManager mNotificationManager;
	private SharedPreferences mSharedPreferences;
	
	/*
	 * Default builder.
	 */
	public JustMeNotification(Context context) {
		mContext = context;
		mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	/*
	 * Enables or Disables the Notification
	 * TODO: Make it work OKAY.
	 */
	public void update() {
		if(mSharedPreferences.getBoolean("enableNotifications", false)) {
			this.enableNotification();
		} else {
			this.disableNotification();
		}
	}
	
	/*
	 * Starts a Notification via NotificationManager
	 */
	private void enableNotification() {
		Intent notificationIntent = new Intent(mContext, JustMeActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
		
		Notification mNotification = 
			new Notification(R.drawable.icon, mContext.getString(R.string.notificationTitle), System.currentTimeMillis());
		
		mNotification.setLatestEventInfo(mContext, mContext.getString(R.string.notificationTitle),
										 mContext.getString(R.string.notificationText),pendingIntent);
		
		mNotificationManager.notify(NOTIFICATION_ID, mNotification);
	}
	
	/*
	 * Disables the Notification
	 */
	private void disableNotification() {
		mNotificationManager.cancel(NOTIFICATION_ID);
	}
	
}
