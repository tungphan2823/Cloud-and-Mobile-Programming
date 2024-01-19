package com.example.myapplication7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchMeetingActivity extends AppCompatActivity {

    private EditText searchTitleEditText, searchDateEditText, searchParticipantsEditText;
    private Button searchButton;
    private TextView searchResultsTextView;
    private Button backButton;

    // Temporary list to simulate stored meetings
    private static final List<Meeting> storedMeetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meeting);

        searchTitleEditText = findViewById(R.id.searchTitleEditText);
        searchDateEditText = findViewById(R.id.searchDateEditText);
        searchParticipantsEditText = findViewById(R.id.searchParticipantsEditText);
        searchButton = findViewById(R.id.searchButton);
        searchResultsTextView = findViewById(R.id.searchResultsTextView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTitle = searchTitleEditText.getText().toString();
                String searchDate = searchDateEditText.getText().toString();
                String searchParticipants = searchParticipantsEditText.getText().toString();
                List<Meeting> results = searchMeetings(searchTitle, searchDate, searchParticipants);
                displaySearchResults(results);
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(SearchMeetingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Meeting> searchMeetings(String title, String date, String participant) {
        // If all search fields are empty, return all meetings
        if (title.isEmpty() && date.isEmpty() && participant.isEmpty()) {
            return new ArrayList<>(Meeting.getStoredMeetings());
        }

        List<Meeting> results = new ArrayList<>();
        for (Meeting meeting : Meeting.getStoredMeetings()) {
            if ((title.isEmpty() || meeting.getTitle().contains(title))
                    && (date.isEmpty() || meeting.getDate().equals(date))
                    && (participant.isEmpty() || meeting.getParticipants().contains(participant))) {
                results.add(meeting);
            }
        }
        return results;
    }


    private void displaySearchResults(List<Meeting> meetings) {
        StringBuilder results = new StringBuilder();
        if (meetings.isEmpty()) {
            results.append("No meetings found.");
        } else {
            for (Meeting meeting : meetings) {
                results.append("Title: ").append(meeting.getTitle()).append("\n");
                results.append("Place: ").append(meeting.getPlace()).append("\n");
                results.append("Participants: ").append(meeting.getParticipantsAsString()).append("\n");
                results.append("Date: ").append(meeting.getDate()).append("\n");
                results.append("Time: ").append(meeting.getTime()).append("\n\n");
            }
        }
        searchResultsTextView.setText(results.toString());
    }
}
