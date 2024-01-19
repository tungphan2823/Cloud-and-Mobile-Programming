package com.example.myapplication7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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

        // Initialize views
        searchTitleEditText = findViewById(R.id.searchTitleEditText);
        titleEditText = findViewById(R.id.titleEditText);
        placeEditText = findViewById(R.id.placeEditText);
        participantsEditText = findViewById(R.id.participantsEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        searchButton = findViewById(R.id.searchButton);
        updateButton = findViewById(R.id.updateButton);
        backButton = findViewById(R.id.backButton);

        // Apply settings
        applySettings();



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
        List<String> imagePaths = oldMeeting.getImagePaths();

        Meeting newMeeting = new Meeting(title, place, participants, date, time, imagePaths);
        Meeting.updateMeeting(oldMeeting, newMeeting); // Update meeting in the centralized list
    }


    @Override
    protected void onResume() {
        super.onResume();

        applySettings();
    }

    private void applySettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int fontSize = sharedPreferences.getInt("fontSize", 16);
        String fontColor = sharedPreferences.getString("fontColor", "#000000");
        String backgroundColor = sharedPreferences.getString("backgroundColor", "#FFFFFF");
        String fontFamily = sharedPreferences.getString("fontFamily", "sans-serif");
        Typeface typeface = Typeface.create(fontFamily, Typeface.NORMAL);

        // Apply settings to EditTexts
        applyStyleToEditText(searchTitleEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(titleEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(placeEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(participantsEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(dateEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(timeEditText, fontSize, fontColor, typeface);

        // Apply style to Buttons
        applyStyleToButton(searchButton, fontSize, fontColor, typeface);
        applyStyleToButton(updateButton, fontSize, fontColor, typeface);
        applyStyleToButton(backButton, fontSize, fontColor, typeface);

        // Apply background color to the layout
        View layout = findViewById(R.id.update_main_layout);
        layout.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    private void applyStyleToEditText(EditText editText, int fontSize, String fontColor, Typeface typeface) {
        editText.setTextSize(fontSize);
        editText.setTextColor(Color.parseColor(fontColor));
        editText.setHintTextColor(Color.parseColor(fontColor));
        editText.setTypeface(typeface);
    }

    private void applyStyleToButton(Button button, int fontSize, String fontColor, Typeface typeface) {
        button.setTextSize(fontSize);
        button.setTextColor(Color.parseColor(fontColor));
        button.setTypeface(typeface);
    }
}
