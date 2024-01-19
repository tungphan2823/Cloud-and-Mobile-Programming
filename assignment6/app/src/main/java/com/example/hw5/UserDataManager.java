package com.example.hw5;

import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.List;

public class UserDataManager {

    public static void addNewUser(List<String> allContacts, List<String> firstNames, List<String> lastNames, List<String> phoneNumbers,
                                  AutoCompleteTextView autoCompleteFirstName, AutoCompleteTextView autoCompleteLastName,
                                  AutoCompleteTextView autoCompletePhoneNumber) {

        String firstName = autoCompleteFirstName.getText().toString().trim();
        String lastName = autoCompleteLastName.getText().toString().trim();
        String phoneNumber = autoCompletePhoneNumber.getText().toString().trim();

        // Check if any of the fields are empty
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(autoCompleteFirstName.getContext(), "Please enter all details for the new user", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new user string
        String newUser = firstName + " " + lastName + " - " + phoneNumber;

        // Add the new user to the allContacts list
        allContacts.add(newUser);

        // Update the AutoCompleteTextView adapters
        updateAutoCompleteAdapters(allContacts, firstNames, lastNames, phoneNumbers,
                autoCompleteFirstName, autoCompleteLastName, autoCompletePhoneNumber);

        // Clear input fields
        autoCompleteFirstName.getText().clear();
        autoCompleteLastName.getText().clear();
        autoCompletePhoneNumber.getText().clear();

        // Notify the user that the new user has been added
        Toast.makeText(autoCompleteFirstName.getContext(), "New user added successfully", Toast.LENGTH_SHORT).show();
    }

    public static void updateAutoCompleteAdapters(List<String> allContacts, List<String> firstNames, List<String> lastNames, List<String> phoneNumbers,
                                                  AutoCompleteTextView autoCompleteFirstName, AutoCompleteTextView autoCompleteLastName,
                                                  AutoCompleteTextView autoCompletePhoneNumber) {

        // Update the firstNames, lastNames, and phoneNumbers lists
        firstNames.clear();
        lastNames.clear();
        phoneNumbers.clear();

        for (String contact : allContacts) {
            String[] parts = contact.split(" - ");
            String firstName = parts[0].split(" ")[0];
            String lastName = parts[0].split(" ")[1];
            String phoneNumber = parts[1];
            firstNames.add(firstName);
            lastNames.add(lastName);
            phoneNumbers.add(phoneNumber);
        }

        // Update the AutoCompleteTextView adapters
        ArrayAdapter<String> firstNameAdapter = new ArrayAdapter<>(autoCompleteFirstName.getContext(), android.R.layout.simple_dropdown_item_1line, firstNames);
        autoCompleteFirstName.setAdapter(firstNameAdapter);

        ArrayAdapter<String> lastNameAdapter = new ArrayAdapter<>(autoCompleteLastName.getContext(), android.R.layout.simple_dropdown_item_1line, lastNames);
        autoCompleteLastName.setAdapter(lastNameAdapter);

        ArrayAdapter<String> phoneNumberAdapter = new ArrayAdapter<>(autoCompletePhoneNumber.getContext(), android.R.layout.simple_dropdown_item_1line, phoneNumbers);
        autoCompletePhoneNumber.setAdapter(phoneNumberAdapter);
    }
}
