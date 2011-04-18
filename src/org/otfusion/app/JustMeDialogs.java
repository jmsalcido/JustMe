/*
 * Just Me?
 * Jose M. Salcido Aguilar
 * 
 * JustMeDialog.java:
 * This class will do the work of preparing the dialogs
 * and returning them to the onCreateDialog on an Activity/child
 * 
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
	
	public static final int ABOUT_DIALOG_MAIN = 1;
	private static String mBlogURL;
	private Context mContext;
	
	public JustMeDialogs(Context context) {
		mContext = context;
	}
	
	private Dialog prepareDialog(int id) {
		Dialog dialog;
		switch(id) {
		case ABOUT_DIALOG_MAIN:
			String info = mContext.getString(R.string.dialogContent);
			mBlogURL = "http://otfusion.org/jose152";

			/*
			 * This actually is a mere copy of the Android Developer's guide
			 * @developer.android.com how to create Dialogs topic!
			 * 
			 * please dont judge me :(
			 */
			
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			LayoutInflater inflater = (LayoutInflater) mContext.getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.about_dialog,
					(ViewGroup) new View(mContext).findViewById(R.id.layout_root));
	
			TextView tv = (TextView) layout.findViewById(R.id.text);
			tv.setText(info);
	
			ImageView img = (ImageView) layout.findViewById(R.id.image);
			img.setImageResource(R.drawable.icon);
	
			builder.setCancelable(false)
					.setPositiveButton(mContext.getString(R.string.dialogCancel),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {
								}
							})
					.setNegativeButton(mContext.getString(R.string.dialogVisit),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
									/*
									 * Open the blog
									 */
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
	
	public Dialog getDialog(int id) {
		return this.prepareDialog(id);
	}
}
