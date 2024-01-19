package com.example.myapplication7;
import android.Manifest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int RESULT_LOAD_IMAGE = 1001;
    private EditText titleEditText, placeEditText, participantsEditText, dateEditText, timeEditText;
    private Button submitButton, searchButton, updateButton, datePickerButton, timePickerButton,settingsButton;
    private DatabaseHelper db;
    private List<String> selectedImagePaths = new ArrayList<>();
    // Variables to store selected date and time
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

        Button buttonLoadImage = findViewById(R.id.buttonSelectImage);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        // Initialize existing UI elements
        titleEditText = findViewById(R.id.titleEditText);
        placeEditText = findViewById(R.id.placeEditText);
        participantsEditText = findViewById(R.id.participantsEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        submitButton = findViewById(R.id.submitButton);
        searchButton = findViewById(R.id.searchButton);
        updateButton = findViewById(R.id.updateButton);
        datePickerButton = findViewById(R.id.datePickerButton);
        timePickerButton = findViewById(R.id.timePickerButton);

        // Button to open SettingsActivity
        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Load and apply settings
        applySettings();




        titleEditText = findViewById(R.id.titleEditText);
        placeEditText = findViewById(R.id.placeEditText);
        participantsEditText = findViewById(R.id.participantsEditText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        submitButton = findViewById(R.id.submitButton);
        searchButton = findViewById(R.id.searchButton);
        updateButton = findViewById(R.id.updateButton);

        datePickerButton = findViewById(R.id.datePickerButton);
        timePickerButton = findViewById(R.id.timePickerButton);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = createMeetingFromInput();
                Meeting.addMeeting(meeting); // Add meeting to the centralized list
                db.addMeeting(meeting);
                // Save the updated list of meetings to file
                SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
                boolean useExternalStorage = prefs.getInt("StorageOption", 0) == 1;
                if(useExternalStorage){
                    Meeting.saveMeetingsToFile(MainActivity.this, true);
                }
                //
                Intent intent = new Intent(MainActivity.this, DisplayMeetingActivity.class);
                intent.putExtra("MEETING", meeting); // Pass the meeting object
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchMeetingActivity.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateMeetingActivity.class);
                startActivity(intent);
            }
        });
    }
// ensure that MainActivity updates its UI with the new settings
    @Override
    protected void onResume() {
        super.onResume();
        // Load and apply settings each time the activity resumes
        applySettings();
    }

    // Function to show the date picker dialog
    private void showDatePicker() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the selected date
                        selectedYear = year;
                        selectedMonth = month;
                        selectedDay = dayOfMonth;

                        // Update the dateEditText with the selected date
                        dateEditText.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                },
                currentYear,
                currentMonth,
                currentDay
        );

        // Show the date picker dialog
        datePickerDialog.show();
    }

    // Function to show the time picker dialog
    private void showTimePicker() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Create a time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Update the selected time
                        selectedHour = hourOfDay;
                        selectedMinute = minute;

                        // Update the timeEditText with the selected time
                        timeEditText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                },
                currentHour,
                currentMinute,
                true // 24-hour format
        );

        // Show the time picker dialog
        timePickerDialog.show();
    }

    private Meeting createMeetingFromInput() {
        String title = titleEditText.getText().toString();
        String place = placeEditText.getText().toString();
        List<String> participants = Arrays.asList(participantsEditText.getText().toString().split(",\\s*"));
        String date = dateEditText.getText().toString();
        String time = timeEditText.getText().toString();

        return new Meeting(title, place, participants, date, time, selectedImagePaths);
    }



    private void applySettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int fontSize = sharedPreferences.getInt("fontSize", 16);
        String fontColor = sharedPreferences.getString("fontColor", "#000000");
        String backgroundColor = sharedPreferences.getString("backgroundColor", "#FFFFFF");
        String fontFamily = sharedPreferences.getString("fontFamily", "sans-serif");
        Typeface typeface = Typeface.create(fontFamily, Typeface.NORMAL);

        // Apply settings to EditTexts
        applyStyleToEditText(titleEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(placeEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(participantsEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(dateEditText, fontSize, fontColor, typeface);
        applyStyleToEditText(timeEditText, fontSize, fontColor, typeface);

        // Apply style to Buttons
        applyStyleToButton(submitButton, fontSize, fontColor, typeface);
        applyStyleToButton(searchButton, fontSize, fontColor, typeface);
        applyStyleToButton(updateButton, fontSize, fontColor, typeface);
        applyStyleToButton(datePickerButton, fontSize, fontColor, typeface);
        applyStyleToButton(timePickerButton, fontSize, fontColor, typeface);
        applyStyleToButton(settingsButton, fontSize, fontColor, typeface);

        // Apply background color to the main layout
        View mainLayout = findViewById(R.id.activity_main);
        mainLayout.setBackgroundColor(Color.parseColor(backgroundColor));
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    if (columnIndex != -1) {
                        String picturePath = cursor.getString(columnIndex);
                        selectedImagePaths.add(picturePath); // Add the path to the list
                        Log.d("MainActivity", "Selected Image Path: " + picturePath);
                    }
                }
                cursor.close();
            }
        }
    }

}
