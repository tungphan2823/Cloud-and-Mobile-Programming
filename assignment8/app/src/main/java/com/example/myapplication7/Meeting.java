package com.example.myapplication7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
}
