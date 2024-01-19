package com.example.myapplication7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "MeetingDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_MEETINGS = "meetings";

    // Meeting Table Columns
    private static final String COLUMN_MEETING_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PLACE = "place";
    private static final String COLUMN_PARTICIPANTS = "participants";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_IMAGE_PATH = "image_path";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEETINGS_TABLE = "CREATE TABLE " + TABLE_MEETINGS +
                "(" +
                COLUMN_MEETING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_PLACE + " TEXT," +
                COLUMN_PARTICIPANTS + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_IMAGE_PATH + " TEXT" + // New column for the image path
                ")";
        db.execSQL(CREATE_MEETINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation, to change if you have more complex upgrade scenarios
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETINGS);
            onCreate(db);
        }
    }

    // Insert a meeting into the database
    public void addMeeting(Meeting meeting) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, meeting.getTitle());
            values.put(COLUMN_PLACE, meeting.getPlace());
            values.put(COLUMN_PARTICIPANTS, meeting.getParticipantsAsString()); // Assuming this is a comma-separated string
            values.put(COLUMN_DATE, meeting.getDate());
            values.put(COLUMN_TIME, meeting.getTime());
            values.put(COLUMN_IMAGE_PATH, String.join(",", meeting.getImagePaths())); // Joining image paths into a single string

            db.insertOrThrow(TABLE_MEETINGS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // Error handling
        } finally {
            db.endTransaction();
        }
    }

    // Get all meetings from the database
    public List<Meeting> getAllMeetings() {
        List<Meeting> meetings = new ArrayList<>();

        String MEETINGS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_MEETINGS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MEETINGS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                    int placeIndex = cursor.getColumnIndex(COLUMN_PLACE);
                    int participantsIndex = cursor.getColumnIndex(COLUMN_PARTICIPANTS);
                    int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                    int timeIndex = cursor.getColumnIndex(COLUMN_TIME);
                    int imagePathIndex = cursor.getColumnIndex(COLUMN_IMAGE_PATH);

                    if (titleIndex != -1 && placeIndex != -1 && participantsIndex != -1 && dateIndex != -1 && timeIndex != -1 && imagePathIndex != -1) {
                        String title = cursor.getString(titleIndex);
                        String place = cursor.getString(placeIndex);
                        List<String> participants = Arrays.asList(cursor.getString(participantsIndex).split(",\\s*"));
                        String date = cursor.getString(dateIndex);
                        String time = cursor.getString(timeIndex);
                        String imagePathsConcatenated = cursor.getString(imagePathIndex);
                        List<String> imagePaths = new ArrayList<>(Arrays.asList(imagePathsConcatenated.split(",")));

                        Meeting newMeeting = new Meeting(title, place, participants, date, time, imagePaths);
                        meetings.add(newMeeting);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Error handling
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return meetings;
    }


    // Update a meeting in the database
    public int updateMeeting(Meeting meeting) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, meeting.getTitle());
        values.put(COLUMN_PLACE, meeting.getPlace());
        values.put(COLUMN_PARTICIPANTS, meeting.getParticipantsAsString());
        values.put(COLUMN_DATE, meeting.getDate());
        values.put(COLUMN_TIME, meeting.getTime());
        values.put(COLUMN_IMAGE_PATH, String.join(",", meeting.getImagePaths())); // Update with new image paths

        return db.update(TABLE_MEETINGS, values, COLUMN_TITLE + " = ?", new String[]{meeting.getTitle()});
    }

    // Delete a meeting in the database
    public void deleteMeeting(String meetingTitle) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_MEETINGS, COLUMN_TITLE + " = ?", new String[]{meetingTitle});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // Error handling
        } finally {
            db.endTransaction();
        }
    }

    public List<Meeting> searchMeetings(String title, String date, String participant) {
        List<Meeting> meetings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "";
        List<String> selectionArgs = new ArrayList<>();

        if (!title.isEmpty()) {
            selection += COLUMN_TITLE + " LIKE ?";
            selectionArgs.add("%" + title + "%");
        }
        if (!date.isEmpty()) {
            if (!selection.isEmpty()) {
                selection += " AND ";
            }
            selection += COLUMN_DATE + " = ?";
            selectionArgs.add(date);
        }
        if (!participant.isEmpty()) {
            if (!selection.isEmpty()) {
                selection += " AND ";
            }
            selection += COLUMN_PARTICIPANTS + " LIKE ?";
            selectionArgs.add("%" + participant + "%");
        }

        Cursor cursor = db.query(TABLE_MEETINGS, null, selection, selectionArgs.toArray(new String[0]), null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                    int placeIndex = cursor.getColumnIndex(COLUMN_PLACE);
                    int participantsIndex = cursor.getColumnIndex(COLUMN_PARTICIPANTS);
                    int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                    int timeIndex = cursor.getColumnIndex(COLUMN_TIME);
                    int imagePathIndex = cursor.getColumnIndex(COLUMN_IMAGE_PATH);

                    if (titleIndex != -1 && placeIndex != -1 && participantsIndex != -1 && dateIndex != -1 && timeIndex != -1 && imagePathIndex != -1) {
                        String retrievedTitle = cursor.getString(titleIndex);
                        String retrievedPlace = cursor.getString(placeIndex);
                        List<String> retrievedParticipants = Arrays.asList(cursor.getString(participantsIndex).split(",\\s*"));
                        String retrievedDate = cursor.getString(dateIndex);
                        String retrievedTime = cursor.getString(timeIndex);
                        String imagePathsConcatenated = cursor.getString(imagePathIndex);
                        List<String> imagePaths = (imagePathsConcatenated != null && !imagePathsConcatenated.isEmpty()) ?
                                Arrays.asList(imagePathsConcatenated.split(",")) :
                                new ArrayList<>();

                        Meeting newMeeting = new Meeting(retrievedTitle, retrievedPlace, retrievedParticipants, retrievedDate, retrievedTime, imagePaths);
                        meetings.add(newMeeting);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Handle exception
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return meetings;
    }





}
