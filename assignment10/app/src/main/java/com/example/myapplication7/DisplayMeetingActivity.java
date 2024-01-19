package com.example.myapplication7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayMeetingActivity extends AppCompatActivity {

    private TextView titleTextView, placeTextView, participantsTextView, dateTextView, timeTextView;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meeting);



        // Initialize TextViews
        titleTextView = findViewById(R.id.titleTextView);
        placeTextView = findViewById(R.id.placeTextView);
        participantsTextView = findViewById(R.id.participantsTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        backButton = findViewById(R.id.backButton);
        // Load and apply settings from SharedPreferences
        applySettings();


        titleTextView = findViewById(R.id.titleTextView);
        placeTextView = findViewById(R.id.placeTextView);
        participantsTextView = findViewById(R.id.participantsTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);

        // Retrieve the Meeting object passed from MainActivity
        Meeting meeting = (Meeting) getIntent().getSerializableExtra("MEETING");

        if (meeting != null) {
            titleTextView.setText(meeting.getTitle());
            placeTextView.setText(meeting.getPlace());
            participantsTextView.setText(meeting.getParticipantsAsString());
            dateTextView.setText(meeting.getDate());
            timeTextView.setText(meeting.getTime());
        }


        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(DisplayMeetingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void applySettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int fontSize = sharedPreferences.getInt("fontSize", 16);
        String fontColor = sharedPreferences.getString("fontColor", "#000000");
        String backgroundColor = sharedPreferences.getString("backgroundColor", "#FFFFFF");
        String fontFamily = sharedPreferences.getString("fontFamily", "sans-serif");
        Typeface typeface = Typeface.create(fontFamily, Typeface.NORMAL);
        // Apply settings to TextViews
        applyStyleToTextView(titleTextView, fontSize, fontColor, typeface);
        applyStyleToTextView(placeTextView, fontSize, fontColor, typeface);
        applyStyleToTextView(participantsTextView, fontSize, fontColor, typeface);
        applyStyleToTextView(dateTextView, fontSize, fontColor, typeface);
        applyStyleToTextView(timeTextView, fontSize, fontColor, typeface);
        // Apply style to Button
        backButton.setTextSize(fontSize);
        backButton.setTextColor(Color.parseColor(fontColor));
        // Apply background color to the main layout
        View mainLayout = findViewById(R.id.display_main);
        mainLayout.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    private void applyStyleToTextView(TextView textView, int fontSize, String fontColor, Typeface typeface) {
        textView.setTextSize(fontSize);
        textView.setTextColor(Color.parseColor(fontColor));
        textView.setTypeface(typeface);
    }
}
