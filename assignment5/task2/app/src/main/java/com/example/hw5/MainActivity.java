package com.example.hw5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.widget.EditText;
import android.widget.TextView;


import android.widget.LinearLayout;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {


    // Declare variables for the UI elements and user data
    private EditText inputField;
    private TextView infoText;
    private Button continueButton, backButton;
    private String userName, password;
    private int state = 0;  // State variable to keep track of the current state of the application

    // Override the onCreate method which is called when the activity is starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set the user interface layout for this activity

        // Initialize the UI elements
        inputField = findViewById(R.id.inputField);
        infoText = findViewById(R.id.infoText);
        continueButton = findViewById(R.id.continueButton);
        backButton = findViewById(R.id.backButton);

        // Set an OnClickListener for the Continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform different actions based on the current state
                switch (state) {
                    case 0:  // If state is 0, get the user name and prompt for password
                        userName = inputField.getText().toString();
                        inputField.setText("");
                        infoText.setText("Enter Password");
                        state = 1;  // Move to the next state
                        break;
                    case 1:  // If state is 1, get the password and display the user info and current date and time
                        password = inputField.getText().toString();
                        inputField.setVisibility(View.GONE);
                        continueButton.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        infoText.setText("User Name: " + userName + "\nPassword: " + password + "\nCurrent Time: " + currentTime);
                        state = 2;  // Move to the next state
                        break;
                }
            }
        });

        // Set an OnClickListener for the Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the UI and state to the initial state
                inputField.setVisibility(View.VISIBLE);
                continueButton.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.GONE);
                inputField.setText("");
                infoText.setText("Enter User Name");
                state = 0;
            }
        });
    }
}