package com.example.assign3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.content.res.Configuration;

import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView latestRandomNumberText;
    private int latestRandomNumber = -1;
    private TextView randomNumberText;
    private Handler handler;
    private Random random;
    private Date lastOrientationChangeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomNumberText = findViewById(R.id.randomNumberText);
        latestRandomNumberText = findViewById(R.id.latestRandomNumberText);
        handler = new Handler(Looper.getMainLooper());
        random = new Random();


        handler.post(updateRandomNumber);
    }

    private Runnable updateRandomNumber = new Runnable() {
        @Override
        public void run() {

            int randomNumber = random.nextInt(100);
            randomNumberText.setText(String.valueOf(randomNumber));

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Save the current date and time
        lastOrientationChangeDate = new Date();

        // Show a Toast with the saved date and time
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(lastOrientationChangeDate);
        Toast.makeText(this, "Last orientation change: " + formattedDate, Toast.LENGTH_SHORT).show();

        // Update the latest random number only on orientation change
        latestRandomNumber = random.nextInt(100); // Change 100 to the range you want
        latestRandomNumberText.setText("Latest Random Number: " + latestRandomNumber);
    }
}