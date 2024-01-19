package com.example.myapplication7;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Meeting implements Serializable {
    private String title;
    private String place;
    private List<String> participants;
    private String date;
    private String time;

    // Static list to store all meetings
    private static List<Meeting> storedMeetings = new ArrayList<>();

    // Default constructor
    public Meeting() {
        this.participants = new ArrayList<>();
    }

    // Parameterized constructor
    public Meeting(String title, String place, List<String> participants, String date, String time) {
        this.title = title;
        this.place = place;
        this.participants = participants;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Method to add a participant
    public void addParticipant(String participant) {
        this.participants.add(participant);
    }

    // Method to remove a participant
    public boolean removeParticipant(String participant) {
        return this.participants.remove(participant);
    }

    // Method to convert the list of participants into a single string
    public String getParticipantsAsString() {
        StringBuilder participantsString = new StringBuilder();
        for (String participant : participants) {
            participantsString.append(participant).append(", ");
        }
        // Remove the last comma and space
        if (!participants.isEmpty()) {
            participantsString.delete(participantsString.length() - 2, participantsString.length());
        }
        return participantsString.toString();
    }

    // Method to convert the Meeting object to a string representation
    @Override
    public String toString() {
        return "Meeting{" +
                "title='" + title + '\'' +
                ", place='" + place + '\'' +
                ", participants=" + getParticipantsAsString() +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    // Static methods for centralized meeting management

    // Method to get all stored meetings
    public static List<Meeting> getStoredMeetings() {
        return storedMeetings;
    }

    // Method to add a new meeting
    public static void addMeeting(Meeting meeting) {
        storedMeetings.add(meeting);
    }

    // Method to update an existing meeting
    public static void updateMeeting(Meeting oldMeeting, Meeting newMeeting) {
        int index = storedMeetings.indexOf(oldMeeting);
        if (index != -1) {
            storedMeetings.set(index, newMeeting);
        }
    }

    // Method to search for a meeting by title
    public static Meeting searchMeetingByTitle(String title) {
        for (Meeting meeting : storedMeetings) {
            if (meeting.getTitle().equals(title)) {
                return meeting;
            }
        }
        return null;
    }

    // Serialize the list of meetings to JSON
    private static String serializeMeetingsList() {
        Gson gson = new Gson();
        return gson.toJson(storedMeetings);
    }

    // Deserialize the list of meetings from JSON
    private static void deserializeMeetingsList(String json) {
        Gson gson = new Gson();
        Type meetingListType = new TypeToken<ArrayList<Meeting>>(){}.getType();
        storedMeetings = gson.fromJson(json, meetingListType);
    }

    // Save the meetings to a file
    public static void saveMeetingsToFile(Context context, boolean useExternal) {
        String json = serializeMeetingsList();
        File file = getStorageFile(context, useExternal);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (IOException e) {
            // Handle exceptions more gracefully here
            e.printStackTrace();
        }
    }

    // Load the meetings from a file
    public static void loadMeetingsFromFile(Context context, boolean useExternal) {
        File file = getStorageFile(context, useExternal);
        StringBuilder json = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            deserializeMeetingsList(json.toString());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // Get the file for storing meetings
    private static File getStorageFile(Context context, boolean useExternal) {
        // Ensure the correct permissions are handled outside this class
        // If using external, also check the state of the external storage
        File directory = useExternal ? context.getExternalFilesDir(null) : context.getFilesDir();
        return new File(directory, "meeting.json");
    }
}
