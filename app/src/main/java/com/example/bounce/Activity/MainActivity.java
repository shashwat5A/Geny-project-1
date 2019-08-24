package com.example.bounce.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bounce.R;
import com.example.bounce.Utility.Utility;
import com.flipboard.bottomsheet.BottomSheetLayout;

public class MainActivity extends AppCompatActivity implements BottomActivity.ButtonClicked {

    private static final String TAG = "MAIN";
    private Button signupBt, enterCodeBt;
    private TextView enterCodeTv, haveCodeTv;
    private BottomSheetLayout bottomSheetLayout;
    int backValue = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: BOUNCE "+ Utility.getUtilityInstance().getPreference(MainActivity.this
        , "TOKEN_FCM") );

        signupBt = findViewById(R.id.signup_bt);
        enterCodeTv = findViewById(R.id.enter_code_tv);
        bottomSheetLayout = findViewById(R.id.bottomsheet);
        haveCodeTv = findViewById(R.id.referal_code_tv);


        signupBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        enterCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomActivity bottomActivity = new BottomActivity();
                bottomActivity.show(getSupportFragmentManager(), "BottomSheet");
            }


        });


        updateStatusBarColor("#AAAAAA");

    }
    public void updateStatusBarColor(String color) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
            finishAffinity();


    }

    @Override
    public void buttonClicked(String referealCode) {
        haveCodeTv.setText(referealCode);
        enterCodeTv.setText("Change Code");
    }
}
