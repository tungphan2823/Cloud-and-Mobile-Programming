package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.widget.EditText;
import android.widget.LinearLayout;



import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

//task 2
    private EditText userNameEditText;
    private EditText commentEditText;
    private LinearLayout entryContainer;
    private EditText searchTextEdit;
    private EditText searchDateEdit;

    private ArrayList<BlogEntry> blogEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEditText = findViewById(R.id.user_name_edit_text);
        commentEditText = findViewById(R.id.comment_edit_text);
        entryContainer = findViewById(R.id.entry_container);
        searchTextEdit = findViewById(R.id.search_text_edit);
        searchDateEdit = findViewById(R.id.search_date_edit);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEntry();
            }
        });

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEntries();
            }
        });
    }

    private void submitEntry() {
        String userName = userNameEditText.getText().toString();
        String comment = commentEditText.getText().toString();

        if (userName.isEmpty() || comment.isEmpty()) {
            userNameEditText.setError("Please fill in both fields");
            commentEditText.setError("Please fill in both fields");
            return;
        }

        BlogEntry entry = new BlogEntry(userName, comment);
        blogEntries.add(0, entry);
        updateEntryUI();
    }

    private void updateEntryUI() {
        entryContainer.removeAllViews();

        for (int i = 0; i < blogEntries.size(); i++) {
            BlogEntry entry = blogEntries.get(i);

            TextView entryText = new TextView(this);
            entryText.setText(String.format(Locale.getDefault(),
                    "Entry %d\nDate: %s\nUser: %s\nComment: %s",
                    i + 1, entry.getDateTime(), entry.getUserName(), entry.getComment()));
            entryContainer.addView(entryText);
        }
    }

    private void searchEntries() {
        String searchText = searchTextEdit.getText().toString().toLowerCase();
        String searchDate = searchDateEdit.getText().toString();

        ArrayList<BlogEntry> searchResults = new ArrayList<>();

        for (BlogEntry entry : blogEntries) {
            boolean matchText = entry.getUserName().toLowerCase().contains(searchText) ||
                    entry.getComment().toLowerCase().contains(searchText);
            boolean matchDate = entry.getDateTime().contains(searchDate);

            if (matchText && matchDate) {
                searchResults.add(entry);
            }
        }

        entryContainer.removeAllViews();

        for (int i = 0; i < searchResults.size(); i++) {
            BlogEntry entry = searchResults.get(i);

            TextView entryText = new TextView(this);
            entryText.setText(String.format(Locale.getDefault(),
                    "Entry %d\nDate: %s\nUser: %s\nComment: %s",
                    i + 1, entry.getDateTime(), entry.getUserName(), entry.getComment()));
            entryContainer.addView(entryText);
        }
    }

    private static class BlogEntry {
        private final String userName;
        private final String comment;
        private final String dateTime;

        BlogEntry(String userName, String comment) {
            this.userName = userName;
            this.comment = comment;
            this.dateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        }

        String getUserName() {
            return userName;
        }

        String getComment() {
            return comment;
        }

        String getDateTime() {
            return dateTime;
        }
    }
}