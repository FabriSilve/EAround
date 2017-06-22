package lpaa.earound.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.sql.Date;
import java.util.ArrayList;

import lpaa.earound.type.Event;


public class DBTask {

    private final String TAG = "DBTask";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBTask(Context context) {
        Log.d(TAG, "DBTask: costructor");
        dbHelper = new DBHelper(context, DBQuery.DB_NAME, null, DBQuery.DB_VERSION);
    }

    private void openReadableDatabase() {
        Log.d(TAG, "openReadableDatabase: start");
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDatabase() {
        Log.d(TAG, "openWritableDatabase: start");
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        Log.d(TAG, "closeDB: start");
        if (db != null)
            db.close();
    }

    public void updateEvents(ArrayList<Event> events) {
        deleteEvents();
        insertEvents(events);
    }

    private void insertEvents(ArrayList<Event> events) {
        Log.d(TAG, "insertEvents: start");

        openWritableDatabase();
        for(Event event : events) {
            ContentValues cv = new ContentValues();
            cv.put(DBQuery.EVENTS_NAME, event.getName());
            cv.put(DBQuery.EVENTS_DESCRIPTION, event.getDescription());
            cv.put(DBQuery.EVENTS_DATE, String.valueOf(event.getDate()));
            cv.put(DBQuery.EVENTS_LAT, event.getLat());
            cv.put(DBQuery.EVENTS_LON, event.getLon());

            db.insert(DBQuery.EVENTS, null, cv);
        }
        this.closeDB();
    }

    private void deleteEvents() {
        Log.d(TAG, "deleteEvents: start");
        openWritableDatabase();
        db.execSQL(DBQuery.DELETE_EVENTS);
        closeDB();
    }

    public ArrayList<Event> getEvents() {
        Log.d(TAG, "getEvents: ");

        this.openReadableDatabase();

        ArrayList<Event> events = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM "+ DBQuery.EVENTS+";", null);
            int i = 0;
            while(cursor.moveToNext()) {
                events.add(getEventToCursor(cursor, i++));
            }
            Log.d(TAG, "getEvents: events added");
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getEvents: Exception: \n", e);
        }

        this.closeDB();

        return events;
    }

    private Event getEventToCursor(Cursor cursor, int id) {
        Log.d(TAG, "getEventToCursor: start");
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                Log.d(TAG, "getEventToCursor: find event "+ cursor.getString(cursor.getColumnIndex(DBQuery.EVENTS_NAME)));
                return new Event(
                        id,
                        cursor.getString(cursor.getColumnIndex(DBQuery.EVENTS_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBQuery.EVENTS_DESCRIPTION)),
                        Date.valueOf(cursor.getString(cursor.getColumnIndex(DBQuery.EVENTS_DATE))),
                        cursor.getDouble(cursor.getColumnIndex(DBQuery.EVENTS_LAT)),
                        cursor.getDouble(cursor.getColumnIndex(DBQuery.EVENTS_LON))
                );
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void insertUser(String username) {
        Log.d(TAG, "insertUser: start");

        openWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBQuery.USERDATA_USERNAME, username);
        db.insert(DBQuery.USERDATA, null, cv);
        this.closeDB();
    }

    public String getUser() {
        Log.d(TAG, "getUser: start");

        openReadableDatabase();
        String user = "";
        try {
            Cursor cursor = db.query(DBQuery.USERDATA, null, null, null, null, null, null);
            if(cursor != null) {
                if(cursor.getCount() == 1 && cursor.moveToNext()) {
                    user = cursor.getString(cursor.getColumnIndex(DBQuery.USERDATA_USERNAME));
                }
                cursor.close();
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "getUser: Exception: \n", e);
            user = "";
        }

        this.closeDB();
        return user;
    }

    public void deleteUser() {
        Log.d(TAG, "deleteUser: start");
        openWritableDatabase();
        db.execSQL(DBQuery.DELETE_USERDATA);
        closeDB();
    }
}
