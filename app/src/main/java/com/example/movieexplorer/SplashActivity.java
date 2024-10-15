package com.example.movieexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.logo);
        logo.animate().rotation(360f).setDuration(6000);

        //diminuer la taille
       // logo.animate().scaleX(0.5f).scaleY(0.5f).setDuration(3000);
        // escendre verticalement vers le bas selon axe y
      // logo.animate().translationYBy(1000f).setDuration(2000);

        // moins couleur jusqu'à dispartion
        //logo.animate().alpha(0f).setDuration(6000);
        Thread t=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(8000);

                    Intent intent=new Intent(SplashActivity.this,ListActivity.class);
                    startActivity(intent);

                }
                catch (InterruptedException e){e.printStackTrace();}
            }
        };
        t.start();

    }
}