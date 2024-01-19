package com.example.exmple2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Window;
public class MainActivity extends AppCompatActivity {
    String tag = "EVH_Demo: ";
    long startTime; // Variable to store start time
      long endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.d(tag, tag + "onCreate()");
        startTime = System.currentTimeMillis(); // Record start time
    }

    protected void onStart() {
        super.onStart();
        endTime = System.currentTimeMillis();
        Log.d(tag, tag + "onStart() - Elapsed Time: " + (endTime - startTime) + " ms");
        startTime = endTime;
    }

    protected void onRestart() {
        super.onRestart();
        endTime = System.currentTimeMillis();
        Log.d(tag, tag + "onReStart() - Elapsed Time: " + (endTime - startTime) + " ms");
        startTime = endTime;
    }

    protected void onResume() {
        super.onResume();
        endTime = System.currentTimeMillis();
        Log.d(tag, tag + "onResume() - Elapsed Time: " + (endTime - startTime) + " ms");
        startTime = endTime;
    }

    protected void onPause() {
        super.onPause();
        endTime = System.currentTimeMillis();
        Log.d(tag, tag + "onPause() - Elapsed Time: " + (endTime - startTime) + " ms");
        startTime = endTime;
    }

    protected void onStop() {
        super.onStop();
        endTime = System.currentTimeMillis();
        Log.d(tag, tag + "onStop() - Elapsed Time: " + (endTime - startTime) + " ms");
        startTime = endTime;
    }

    protected void onDestroy() {
        super.onDestroy();
        endTime = System.currentTimeMillis();
        Log.d(tag, tag + "onDestroy() - Elapsed Time: " + (endTime - startTime) + " ms");
        startTime = endTime;
    }

}
