package com.example.pjs4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import model.FireBase;
import model.FirestoreCallback;


public class Test extends AppCompatActivity {

    private ImageView img_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        this.img_view = findViewById(R.id.img_view);

        FireBase fb = FireBase.getInstance();
        fb.getImage("apples-1841132_1920.jpg", new FirestoreCallback<Bitmap>() {

            @Override
            public void onCallback(Bitmap b) {
                img_view.setImageBitmap(b);
            }
        });
    }


}
