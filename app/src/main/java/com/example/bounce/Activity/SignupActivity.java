package com.example.bounce.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.DeniedByServerException;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bounce.Model.OtpResponse;
import com.example.bounce.R;
import com.example.bounce.Utility.ApiClient;
import com.example.bounce.Utility.ApiInterface;
import com.example.bounce.Utility.LoginResponseDeserializer;
import com.example.bounce.Utility.Utility;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.bounce.Utility.ApiClient.getUnsafeOkHttpClient;

public class SignupActivity extends AppCompatActivity {

    private EditText mobileNumber;
    private ImageView goBt, backBt;
    private String TAG = "SIGNUP";
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        dialog = new Dialog(SignupActivity.this);
        mobileNumber = findViewById(R.id.mobile_et);
        goBt = findViewById(R.id.go_bt);
        backBt = findViewById(R.id.back_bt);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        goBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNum = mobileNumber.getText().toString().trim();
                if (mobileNum.length()<=0){
                    Log.e(TAG, "onClick: "+mobileNum );
                    new AlertDialog.Builder(SignupActivity.this)
                            .setTitle("Alert.")
                            .setMessage("Mobile number is empty.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
                else{
                    String fcmToken = Utility.getUtilityInstance().getPreference(SignupActivity.this
                    , "TOKEN_FCM");
                    otpMetod(mobileNum,fcmToken);
                }
            }
        });
    }

    private void otpMetod(final String mobileNumber, String fcmToken) {

        Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
        intent.putExtra("MOB", String.valueOf(mobileNumber));
        startActivity(intent);

    }
}
