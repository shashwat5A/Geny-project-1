package com.example.bounce.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.bounce.R;

public class Utility {
    private static Utility utilityInstance;

    private Utility() {
    }

    public static synchronized Utility getUtilityInstance() {
        if (null == utilityInstance) {
            utilityInstance = new Utility();
        }
        return utilityInstance;
    }

    public void setPreference(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("Bounce", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();

    }

    public String getPreference(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("Bounce", Context.MODE_PRIVATE);
        String result = prefs.getString(key, "");
        //String a = prefs.getString(key,"");
        return result;
    }

    public void showGifPopup(final Context mContext, boolean show, Dialog dialog, String title) {
        try {

            dialog.setContentView(R.layout.gif_loader);

            dialog.setCancelable(false);
            ImageView imageView = dialog.findViewById(R.id.popup_gifView_iv);
            try {
                Glide.with(mContext)
                        .asGif()
                        .load(R.drawable.loader_background)
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            //  dialog.show();

            try {
                if (!((Activity) mContext).isFinishing()) {
                    //show dialog
                    if (show) {
                        dialog.show();
                    } else {
                        dialog.dismiss();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.e("TAG", "showGifPopup: " + e.getMessage());
        }
        //  return puNumber;


    }
}
