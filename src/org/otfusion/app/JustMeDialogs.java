/*
 * JustMeDialog.java:
 * This class will do the work of preparing the dialogs
 * and returning them to the onCreateDialog on an Activity/child
 */

package org.otfusion.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class JustMeDialogs {
	
	private String mBlogURL;
	private Context mContext;
	
	public JustMeDialogs(Context context) {
		mContext = context;
	}
	
	/**
	 * prepareDialog(int id):
	 * this method will prepare the dialog box selected by an id
	 * passed by the onCreateDialog on an Activity.
	 * @param id
	 * @return
	 */
	private Dialog prepareDialog(int id) {
		Dialog dialog;
		switch(id) {
		case JustMeActivity.ABOUT_DIALOG:
			String info = mContext.getString(R.string.dialogContent);
			mBlogURL = "http://otfusion.org/jose152";

			/*
			 * This actually is a mere copy of the Android Developer's guide
			 * @developer.android.com how to create Dialogs topic!
			 * 
			 * please dont judge me :(
			 */
			
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			/*
			 * I dont really know what this does but... it works!
			 */
			LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.about_dialog,
					(ViewGroup) new View(mContext).findViewById(R.id.layout_root));
	
			/*
			 * Taking control again!
			 */
			TextView tv = (TextView) layout.findViewById(R.id.text);
			tv.setText(info);
	
			ImageView img = (ImageView) layout.findViewById(R.id.image);
			img.setImageResource(R.drawable.icon);
	
			builder.setCancelable(false)
					.setPositiveButton(mContext.getString(R.string.dialogCancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
								}
							})
					.setNegativeButton(mContext.getString(R.string.dialogVisit),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									Uri uri = Uri.parse(mBlogURL);
									Intent i = new Intent(Intent.ACTION_VIEW, uri);
									mContext.startActivity(i);
								}
							});
			builder.setView(layout);
			dialog = builder.create();
			break;
		default:
			dialog = null;
			break;
		}
		return dialog;
	}
	
	/**
	 * getDialog(int id) method:
	 * this method will return a prepared dialog for being used in the activity
	 * @param id
	 * @return
	 */
	public Dialog getDialog(int id) {
		return this.prepareDialog(id);
	}
}
