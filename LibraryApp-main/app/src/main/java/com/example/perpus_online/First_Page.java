package com.example.perpus_online;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class First_Page extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;

    //ImageView
    ImageView book, text;
    //Animations
    Animation topAnimation, bottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_page);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        book = findViewById(R.id.book);
        text = findViewById(R.id.text);

        book.setAnimation(topAnimation);
        text.setAnimation(bottomAnimation);

        //Splash Screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(First_Page.this, Login.class);

            Pair[] pairs= new Pair[2];
            pairs[0] = new Pair<View, String>(book, "logo_book");
            pairs[1] = new Pair<View, String>(text, "logo_text");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(First_Page.this, pairs);
            startActivity(intent, options.toBundle());
            finish();
        },SPLASH_TIME_OUT);
    }
}
