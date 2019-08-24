package com.example.bounce.Activity;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bounce.R;

public class SplashScreen  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Intent mainActivity = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(mainActivity);
                    }
                },
                3000
        );
    }
}
