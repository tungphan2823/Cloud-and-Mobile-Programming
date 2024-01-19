package com.example.myapplication7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateMeetingActivity extends AppCompatActivity {

    private EditText searchTitleEditText, titleEditText, placeEditText, participantsEditText, dateEditText, timeEditText;
    private Button searchButton, updateButton;
    private Meeting selectedMeeting;
    private Button backButton;

    // Temporary list to simulate stored meetings
    private static final List<Meeting> storedMeetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_meeting);

        searchTitleEditText = findViewById(R.id.searchTitleEditText);
        titleEditText = findViewById(R.id.titleEditText);
        placeEditText = findViewById(R.id.placeEditText);
        participantsEditText = findViewById(R.id.participantsEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);

        searchButton = findViewById(R.id.searchButton);
        updateButton = findViewById(R.id.updateButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTitle = searchTitleEditText.getText().toString();
                selectedMeeting = searchMeeting(searchTitle);
                if (selectedMeeting != null) {
                    fillMeetingDetails(selectedMeeting);
                } else {
                    // Handle case where meeting is not found
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMeeting != null) {
                    updateMeetingDetails(selectedMeeting);
                    // Handle saving updated details, in a real app this would involve database operations
                }
            }
        });

        // ... existing code ...

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(UpdateMeetingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Meeting searchMeeting(String title) {
        return Meeting.searchMeetingByTitle(title); // Use centralized method
    }
    private void fillMeetingDetails(Meeting meeting) {
        titleEditText.setText(meeting.getTitle());
        placeEditText.setText(meeting.getPlace());
        participantsEditText.setText(meeting.getParticipantsAsString());
        dateEditText.setText(meeting.getDate());
        timeEditText.setText(meeting.getTime());
    }

    private void updateMeetingDetails(Meeting oldMeeting) {
        String title = titleEditText.getText().toString();
        String place = placeEditText.getText().toString();
        List<String> participants = Arrays.asList(participantsEditText.getText().toString().split(",\\s*"));
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();

        Meeting newMeeting = new Meeting(title, place, participants, date, time);
        Meeting.updateMeeting(oldMeeting, newMeeting); // Update meeting in the centralized list
    }
}
