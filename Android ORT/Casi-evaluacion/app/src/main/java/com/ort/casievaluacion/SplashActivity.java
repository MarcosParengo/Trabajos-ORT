package com.ort.casievaluacion;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.TimerTask;
import java.util.Timer;

public class SplashActivity extends AppCompatActivity
{
    private TimerTask task;
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.artaud);

        task = new TimerTask()
        {
            @Override
            public void run()
            {
                Intent Intentprincipal = new Intent (SplashActivity.this,login.class);
                startActivity(Intentprincipal);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

}
