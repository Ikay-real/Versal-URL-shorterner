package com.ikay.real.shorterner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ikay.real.shorterner.R;

public class SplashActivity extends AppCompatActivity {
    Animation textViewLogoAnimation;
    MotionLayout motionLayout;
    ImageView imageView;
    TextView textViewLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //drawable = getResources().getDrawable(R.drawable.versal_logo);
        imageView = findViewById(R.id.imageViewLogo);
        motionLayout= findViewById(R.id.MotionLayoutBase);
        textViewLogo= findViewById(R.id.textViewLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startApp();
                Toast.makeText(SplashActivity.this,"Re",Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void startApp(){


    }
    @Override
    public void onBackPressed() {

    }
}