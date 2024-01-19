package com.example.hw5;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.view.View;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {



    private AutoCompleteTextView autoCompleteFirstName, autoCompleteLastName, autoCompletePhoneNumber;
    private Button searchButton;
    private Button addButton;

    private ListView searchResultsListView;

    private List<String> allContacts = new ArrayList<>(Arrays.asList(
            "John Doe - 123-456-7890",
            "Johnny Smith - 123-333-7890",
            "Jane Smithy - 987-654-3210",
            "Bob Johnson - 555-123-4567",
            "Alice Williams - 888-999-0000"
    ));

    private List<String> firstNames = new ArrayList<>();
    private List<String> lastNames = new ArrayList<>();
    private List<String> phoneNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        autoCompleteFirstName = findViewById(R.id.autoCompleteFirstName);
        autoCompleteLastName = findViewById(R.id.autoCompleteLastName);
        autoCompletePhoneNumber = findViewById(R.id.autoCompletePhoneNumber);
        searchButton = findViewById(R.id.searchButton);
        addButton = findViewById(R.id.addButton);
        searchResultsListView = findViewById(R.id.searchResultsListView);

        // Extract first names, last names, and phone numbers from allContacts
        for (String contact : allContacts) {
            String[] parts = contact.split(" - ");
            String firstName = parts[0].split(" ")[0];
            String lastName = parts[0].split(" ")[1];
            String phoneNumber = parts[1];
            firstNames.add(firstName);
            lastNames.add(lastName);
            phoneNumbers.add(phoneNumber);
        }

        // Set adapters for AutoCompleteTextViews
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDataManager.addNewUser(allContacts, firstNames, lastNames, phoneNumbers,
                        autoCompleteFirstName, autoCompleteLastName, autoCompletePhoneNumber);
            }
        });
        ArrayAdapter<String> firstNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, firstNames);
        autoCompleteFirstName.setAdapter(firstNameAdapter);

        ArrayAdapter<String> lastNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, lastNames);
        autoCompleteLastName.setAdapter(lastNameAdapter);

        ArrayAdapter<String> phoneNumberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, phoneNumbers);
        autoCompletePhoneNumber.setAdapter(phoneNumberAdapter);

        // Set a listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String firstName = autoCompleteFirstName.getText().toString().trim();
        String lastName = autoCompleteLastName.getText().toString().trim();
        String phoneNumber = autoCompletePhoneNumber.getText().toString().trim();

        List<String> searchResults = new ArrayList<>();

        // Perform search based on the entered criteria
        for (String contact : allContacts) {
            String[] parts = contact.split(" - ");
            String contactFirstName = parts[0].split(" ")[0];
            String contactLastName = parts[0].split(" ")[1];
            String contactPhoneNumber = parts[1];

            if ((TextUtils.isEmpty(firstName) || contactFirstName.startsWith(firstName)) &&
                    (TextUtils.isEmpty(lastName) || contactLastName.startsWith(lastName)) &&
                    (TextUtils.isEmpty(phoneNumber) || contactPhoneNumber.startsWith(phoneNumber))) {
                searchResults.add(contact);
            }
        }

        // Display search results in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        searchResultsListView.setAdapter(adapter);
    }
}