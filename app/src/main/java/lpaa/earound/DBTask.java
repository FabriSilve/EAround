package lpaa.earound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

import static lpaa.earound.DBQuery.*;


public class DBTask {

    private final String TAG = "DBTask";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBTask(Context context) {
        Log.d(TAG, "DBTask: costructor");
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
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

    public UserData getUser() {
        Log.d(TAG, "getUser: start");

        String where = " '1' = '1'";
        //String[] whereArgs = null;

        this.openReadableDatabase();
        Cursor cursor = db.query(USERDATA, null, where, null, null, null, null);

        ArrayList<UserData> users = new ArrayList<>();
        while(cursor.moveToNext()) {
            users.add(getUserToCursor(cursor));
        }

        cursor.close();
        this.closeDB();

        return !users.isEmpty()? users.get(0) : null;
    }

    private UserData getUserToCursor(Cursor cursor) {
        Log.d(TAG, "getUserToCursor: start");
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                Log.d(TAG, "getUserToCursor: find user "+cursor.getString(cursor.getColumnIndex(USERDATA_USER)));
                UserData user = new UserData(
                        cursor.getString(cursor.getColumnIndex(USERDATA_USER)),
                        cursor.getString(cursor.getColumnIndex(USERDATA_CITY))
                );
                return user;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void insertUser(UserData user) {
        Log.d(TAG, "insertUser: start");
        ContentValues cv = new ContentValues();
        cv.put(USERDATA_USER, user.getUsername());
        cv.put(USERDATA_CITY, user.getCity());

        openWritableDatabase();
        db.insert(USERDATA, null, cv);
        this.closeDB();
    }

    public void deleteUser() {
        Log.d(TAG, "deleteUser: start");
        openWritableDatabase();
        db.execSQL("DROP TABLE "+ USERDATA);
        closeDB();
    }

    public void updateEvents(ArrayList<Event> events) {
        deleteEvents();
        insertEvents(events);
    }

    public void insertEvents(ArrayList<Event> events) {
        Log.d(TAG, "insertEvents: start");

        openWritableDatabase();
        for(Event event : events) {
            /*ContentValues cv = new ContentValues();
            cv.put(EVENTS_ID, event.getId());
            cv.put(EVENTS_NAME, event.getName());
            cv.put(EVENTS_DESCRIPTION, event.getDescription());
            cv.put(EVENTS_DATE, String.valueOf(event.getDate()));
            cv.put(EVENTS_ADDRESS, event.getAddress());
            cv.put(EVENTS_LAT, event.getLat());
            cv.put(EVENTS_LON, event.getLon());
            cv.put(EVENTS_IMAGE, event.getImage());
            db.insert(EVENTS, null, cv);*/
            db.execSQL("INSERT INTO " + EVENTS + " VALUES ("+
                event.getId() + ", " +
                event.getName() + ", " +
                event.getDescription() + ", " +
                String.valueOf(event.getDate()) + ", " +
                null /*event.getAddress()*/ + ", " +
                null /*String.valueOf(event.getLat())*/ + ", " +
                null /*String.valueOf(event.getLon())*/ + ", " +
                null /*event.getImage()*/ +
                ");"
            );
        }
        this.closeDB();
    }

    public void deleteEvents() {
        Log.d(TAG, "deleteEvents: start");
        openWritableDatabase();
        db.execSQL(DROP_EVENTS_TABLE);
        closeDB();
    }

    public ArrayList<Event> getEvents() {
        Log.d(TAG, "getEvents: ");

        String where = EVENTS_ID +" > ?";
        String[] whereArgs = { "0" };

        this.openReadableDatabase();
        try {
            db.rawQuery("SELECT * FROM Events;", null);
        } catch (SQLiteException e) {
            Log.e(TAG, "getEvents: Exception test connection db",e );
            db.execSQL(CREATE_EVENTS_TABLE);
            Log.d(TAG, "getEvents: Table Events Created");
        }

        ArrayList<Event> events = new ArrayList<>();
        try {
            //Cursor cursor = db.query(EVENTS, null, where, whereArgs, null, null, null);
            Cursor cursor = db.rawQuery("SELECT * FROM "+EVENTS+";", null);
            while(cursor.moveToNext()) {
                events.add(getEventToCursor(cursor));
            }
            Log.d(TAG, "getEvents: events added");
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getEvents: Exception: \n", e);
        }

        this.closeDB();

        return events;
    }

    private Event getEventToCursor(Cursor cursor) {
        Log.d(TAG, "getEventToCursor: start");
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                Log.d(TAG, "getEventToCursor: find event "+cursor.getString(cursor.getColumnIndex(EVENTS_NAME)));
                return new Event(
                        cursor.getInt(cursor.getColumnIndex(EVENTS_ID)),
                        cursor.getString(cursor.getColumnIndex(EVENTS_NAME)),
                        cursor.getString(cursor.getColumnIndex(EVENTS_DESCRIPTION)),
                        Date.valueOf(cursor.getString(cursor.getColumnIndex(EVENTS_DATE))),
                        cursor.getString(cursor.getColumnIndex(EVENTS_ADDRESS)),
                        cursor.getFloat(cursor.getColumnIndex(EVENTS_LAT)),
                        cursor.getFloat(cursor.getColumnIndex(EVENTS_LON)),
                        cursor.getString(cursor.getColumnIndex(EVENTS_IMAGE))
                );
            } catch (Exception e) {
                return null;
            }
        }
    }
}
