package com.example.myapplication7;

import android.content.Intent;
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
            participantsTextView.setText(meeting.getParticipantsAsString()); // Assuming you have this method in your Meeting class
            dateTextView.setText(meeting.getDate());
            timeTextView.setText(meeting.getTime());
        }
            // ... existing code ...

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
}
