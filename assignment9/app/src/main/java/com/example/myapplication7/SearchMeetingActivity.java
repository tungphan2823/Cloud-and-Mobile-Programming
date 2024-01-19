package com.example.myapplication7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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


    private static final List<Meeting> storedMeetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meeting);



        // Initialize views
        searchTitleEditText = findViewById(R.id.searchTitleEditText);
        searchDateEditText = findViewById(R.id.searchDateEditText);
        searchParticipantsEditText = findViewById(R.id.searchParticipantsEditText);
        searchResultsTextView = findViewById(R.id.searchResultsTextView);
        searchButton = findViewById(R.id.searchButton);
        backButton = findViewById(R.id.backButton);

        // Apply settings
        applySettings();


        searchTitleEditText = findViewById(R.id.searchTitleEditText);
        searchDateEditText = findViewById(R.id.searchDateEditText);
        searchParticipantsEditText = findViewById(R.id.searchParticipantsEditText);
        searchButton = findViewById(R.id.searchButton);
        searchResultsTextView = findViewById(R.id.searchResultsTextView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
                boolean useExternalStorage = prefs.getInt("StorageOption", 0) == 1;
                if (useExternalStorage) {
                    Meeting.loadMeetingsFromFile(SearchMeetingActivity.this, true);
                }

                // Perform the search on the centralized list
                String searchTitle = searchTitleEditText.getText().toString();
                String searchDate = searchDateEditText.getText().toString();
                String searchParticipants = searchParticipantsEditText.getText().toString();
                List<Meeting> results = searchMeetings(searchTitle, searchDate, searchParticipants);

                // Display results
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
    @Override
    protected void onResume() {
        super.onResume();
        applySettings(); // To ensure updated settings are applied
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
    private void applySettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int fontSize = sharedPreferences.getInt("fontSize", 16);
        String fontColor = sharedPreferences.getString("fontColor", "#000000");
        String backgroundColor = sharedPreferences.getString("backgroundColor", "#FFFFFF");
        String fontFamily = sharedPreferences.getString("fontFamily", "sans-serif");
        Typeface typeface = Typeface.create(fontFamily, Typeface.NORMAL);
        // Apply settings to EditTexts and TextView
        applyStyleToEditText(searchTitleEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(searchDateEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(searchParticipantsEditText, fontSize, fontColor, typeface);
        searchResultsTextView.setTextSize(fontSize);
        searchResultsTextView.setTextColor(Color.parseColor(fontColor));
        searchResultsTextView.setTypeface(typeface);
        // Apply style to Buttons
        searchButton.setTextSize(fontSize);
        searchButton.setTextColor(Color.parseColor(fontColor));
        backButton.setTextSize(fontSize);
        backButton.setTextColor(Color.parseColor(fontColor));

        // Apply background color to the layout
        View layout = findViewById(R.id.search_main_layout); // Replace with your layout ID
        layout.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    private void applyStyleToEditText(EditText editText, int fontSize, String fontColor, Typeface typeface) {
        editText.setTextSize(fontSize);
        editText.setTextColor(Color.parseColor(fontColor));
        editText.setHintTextColor(Color.parseColor(fontColor));
        editText.setTypeface(typeface);
    }
}
