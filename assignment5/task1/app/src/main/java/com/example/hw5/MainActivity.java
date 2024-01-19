package com.example.hw5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle visibility of button2
                if (button2.getVisibility() == View.VISIBLE) {
                    button2.setVisibility(View.INVISIBLE);
                } else {
                    button2.setVisibility(View.VISIBLE);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move button1 vertically
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) button1.getLayoutParams();
                layoutParams.topMargin += 50; // You can adjust the value as needed
                button1.setLayoutParams(layoutParams);
            }
        });
    }
}