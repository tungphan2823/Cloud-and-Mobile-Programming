package com.example.myapplication7;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText, placeEditText, participantsEditText, dateEditText, timeEditText;
    private Button submitButton, searchButton, updateButton, datePickerButton, timePickerButton;

    // Variables to store selected date and time
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        return new Meeting(title, place, participants, date, time);
    }
}
