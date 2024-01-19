package com.example.myapplication7;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Button backButton, resetButton, saveButton;
    private SeekBar fontSizeSeekBar;
    private Spinner fontColorSpinner, backgroundColorSpinner,fontFamilySpinner;
    private TextView sampleText;

    // Color names and their corresponding values
    private String[] colorNames = {"Black", "Red", "Green", "Blue", "White"};
    private String[] colorValues = {"#000000", "#FF0000", "#00FF00", "#0000FF", "#FFFFFF"};
    private String[] fontFamilyNames = {"sans-serif", "serif", "monospace"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        resetButton = findViewById(R.id.resetButton);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveSettingsButton);
        fontSizeSeekBar = findViewById(R.id.fontSizeSeekBar);
        fontColorSpinner = findViewById(R.id.fontColorSpinner);
        backgroundColorSpinner = findViewById(R.id.backgroundColorSpinner);
        fontFamilySpinner = findViewById(R.id.fontFamilySpinner);
        sampleText = findViewById(R.id.sampleText);

        // Load settings
        loadSettings();

        // Setup for font color and background color spinners
        ColorSpinnerAdapter fontColorAdapter = new ColorSpinnerAdapter(this, colorNames, colorValues);
        fontColorSpinner.setAdapter(fontColorAdapter);
        ColorSpinnerAdapter backgroundColorAdapter = new ColorSpinnerAdapter(this, colorNames, colorValues);
        backgroundColorSpinner.setAdapter(backgroundColorAdapter);


        ArrayAdapter<String> fontFamilyAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fontFamilyNames);
        fontFamilyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontFamilySpinner.setAdapter(fontFamilyAdapter);

        // Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        // Reset button click listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSettings();
            }
        });

        // Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        int fontSize = sharedPreferences.getInt("fontSize", 16);
        String fontColor = sharedPreferences.getString("fontColor", "Black");
        String backgroundColor = sharedPreferences.getString("backgroundColor", "White");
        String fontFamily = sharedPreferences.getString("fontFamily", "sans-serif");
        sampleText.setTextSize(fontSize);
        sampleText.setTextColor(Color.parseColor(getColorValue(fontColor)));
        sampleText.setBackgroundColor(Color.parseColor(getColorValue(backgroundColor)));
        sampleText.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
    }

    private void saveSettings() {
        int fontSize = fontSizeSeekBar.getProgress() + 14;
        String fontColor = fontColorSpinner.getSelectedItem().toString();
        String backgroundColor = backgroundColorSpinner.getSelectedItem().toString();
        String fontFamily = fontFamilySpinner.getSelectedItem().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("fontSize", fontSize);
        editor.putString("fontColor", fontColor);
        editor.putString("backgroundColor", backgroundColor);
        editor.putString("fontFamily", fontFamily);
        editor.apply();

        sampleText.setTextSize(fontSize);
        sampleText.setTextColor(Color.parseColor(getColorValue(fontColor)));
        sampleText.setBackgroundColor(Color.parseColor(getColorValue(backgroundColor)));
        sampleText.setTypeface(Typeface.create(fontFamily, Typeface.NORMAL));
    }

    private void resetSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("fontSize", 16);
        editor.putString("fontColor", "Black");
        editor.putString("backgroundColor", "White");  // Set default background color as white
        editor.putString("fontFamily", "sans-serif");
        editor.apply();

        loadSettings();
    }

    private String getColorValue(String colorName) {
        for (int i = 0; i < colorNames.length; i++) {
            if (colorNames[i].equals(colorName)) {
                return colorValues[i];
            }
        }
        return "#FFFFFF"; // Default to White if not found
    }
}
