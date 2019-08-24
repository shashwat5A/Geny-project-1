package com.example.bounce.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.DeniedByServerException;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

public class OtpActivity extends AppCompatActivity {

    private ImageView goBackBt, goBt;
    private TextView  resendOtpTv, mobileTv;
    private EditText otpEt;
    private Dialog dialog;
    private String TAG = "OTP";
    private String otpStr;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);
        dialog = new Dialog(OtpActivity.this);
        goBackBt = findViewById(R.id.back_bt);
        mobileTv =  findViewById(R.id.mobile_num_tv);
        goBt = findViewById(R.id.go_two_bt);
        resendOtpTv = findViewById(R.id.resend_otp_tv);
        otpEt = findViewById(R.id.otp_et);
        final String mobileNum = getIntent().getStringExtra("MOB");
        mobileTv.setText(mobileNum);
        final String fcmToken = Utility.getUtilityInstance().getPreference(OtpActivity.this
                , "TOKEN_FCM");
        goBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        resendOtpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtp(fcmToken);
            }
        });
       goBt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (otpEt.getText().toString().trim().equalsIgnoreCase("0007")){
                  Intent intent = new Intent(OtpActivity.this, HomeScreen.class);
                 intent.putExtra("NUM", mobileNum);
                  startActivity(intent);
                   Toast.makeText(getApplicationContext(),"Successfully signed in.",Toast.LENGTH_SHORT).show();
               }
           }
       });
       // getOtp(fcmToken);


    }

    private void getOtp(String fcmToken) {

        Utility.getUtilityInstance().showGifPopup(OtpActivity.this, true, dialog, " ");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(OtpResponse.class, new LoginResponseDeserializer());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                /*.client(getUnsafeOkHttpClient().build())*/
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<OtpResponse> callValidateAuctionId = apiService.loginUser(fcmToken);
        callValidateAuctionId.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                Utility.getUtilityInstance().showGifPopup(OtpActivity.this, false, dialog, " ");
                String success = response.body().getSuccess();
                Log.e(TAG, "onResponse: " + response.body().getSuccess());
                if (success.equalsIgnoreCase("yes")){
                    otpEt.setText(response.body().getOtp());
                    otpStr = response.body().getOtp();
                }
                else{
                    new android.app.AlertDialog.Builder(OtpActivity.this)
                            .setTitle("Error")
                            .setMessage("Some error occurred please try after sometime.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Utility.getUtilityInstance().showGifPopup(OtpActivity.this, false, dialog, " ");
                if (t instanceof DeniedByServerException) {
                    new android.app.AlertDialog.Builder(OtpActivity.this)
                            .setTitle("Error")
                            .setMessage("Access denied by server")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (t instanceof java.net.SocketTimeoutException) {
                    new android.app.AlertDialog.Builder(OtpActivity.this)
                            .setTitle("Error")
                            .setMessage("Server time out error")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (t instanceof java.net.ConnectException) {
                    new android.app.AlertDialog.Builder(OtpActivity.this)
                            .setTitle("Error")
                            .setMessage("No internet connection")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                } else {

                    new android.app.AlertDialog.Builder(OtpActivity.this)
                            .setTitle("Error")
                            .setMessage("Some error occurred please try after sometime.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
                //

            }
        });

    }


}
