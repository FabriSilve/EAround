package lpaa.earound.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import lpaa.earound.type.Event;
import lpaa.earound.type.LocalEvent;

import static lpaa.earound.database.DBQuery.*;


public class DBTask {

    private final String TAG = "DBTask";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DBTask(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private void openReadableDatabase() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDatabase() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    public void updateEvents(ArrayList<Event> events) {
        deleteEvents();
        insertEvents(events);
    }

    private void insertEvents(ArrayList<Event> events) {
        openWritableDatabase();
        for(Event event : events) {
            ContentValues cv = new ContentValues();
            cv.put(EVENTS_ID, event.getId());
            cv.put(EVENTS_NAME, event.getName());
            cv.put(EVENTS_DESCRIPTION, event.getDescription());
            cv.put(EVENTS_DAY, String.valueOf(event.getDay()));
            cv.put(EVENTS_ADDRESS, event.getAddress());
            cv.put(EVENTS_LAT, event.getLat());
            cv.put(EVENTS_LON, event.getLon());
            cv.put(EVENTS_OWNER, event.getOwner());

            db.insert(EVENTS, null, cv);
        }
        this.closeDB();
    }

    private void deleteEvents() {
        openWritableDatabase();
        db.delete(EVENTS, null, null);
        closeDB();
    }

    public ArrayList<Event> getEvents() {
        this.openReadableDatabase();
        ArrayList<Event> events = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM "+ EVENTS+";", null);
            //TODO da sostituire con db.query()
            int i = 0;
            while(cursor.moveToNext()) {
                events.add(getEventToCursor(cursor, i++));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getEvents: Exception: \n", e);
        }
        this.closeDB();

        return events;
    }

    private Event getEventToCursor(Cursor cursor, int id) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                return new Event(
                        id,
                        cursor.getString(cursor.getColumnIndex(EVENTS_NAME)),
                        cursor.getString(cursor.getColumnIndex(EVENTS_DESCRIPTION)),
                        Date.valueOf(cursor.getString(cursor.getColumnIndex(EVENTS_DAY))),
                        cursor.getString(cursor.getColumnIndex(EVENTS_ADDRESS)),
                        cursor.getDouble(cursor.getColumnIndex(EVENTS_LAT)),
                        cursor.getDouble(cursor.getColumnIndex(EVENTS_LON)),
                        cursor.getString(cursor.getColumnIndex(EVENTS_OWNER))
                );
            } catch (Exception e) {
                Log.e(TAG, "getEventToCursor: ",e );
                return null;
            }
        }
    }

    public void insertUser(String username) {
        openWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERDATA_USERNAME, username);
        db.insert(USERDATA, null, cv);
        this.closeDB();
    }

    public String getUser() {
        openReadableDatabase();
        String user = "";
        try {
            Cursor cursor = db.query(USERDATA, null, null, null, null, null, null);
            if(cursor != null) {
                if(cursor.getCount() == 1 && cursor.moveToNext()) {
                    user = cursor.getString(cursor.getColumnIndex(USERDATA_USERNAME));
                }
                cursor.close();
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "getUser: Exception: \n", e);
        }
        this.closeDB();
        return user;
    }

    public void deleteUser() {
        openWritableDatabase();
        db.delete(USERDATA, null, null);
        closeDB();
    }

    public void insertLocalEvent(LocalEvent event) {
        openWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MYEVENTS_NAME, event.getName());
        cv.put(MYEVENTS_DESCRIPTION, event.getDescription());
        cv.put(MYEVENTS_DAY, event.getDayString());
        cv.put(MYEVENTS_ADDRESS, event.getAddress());
        db.insert(MYEVENTS, null, cv);

        this.closeDB();
    }

    public ArrayList<LocalEvent> getLocalEvents() {
        this.openReadableDatabase();
        ArrayList<LocalEvent> events = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM "+ MYEVENTS+";", null);
            //TODO sostituire con db.query()
            int i = 0;
            while(cursor.moveToNext()) {
                events.add(getLocalEventToCursor(cursor));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getEvents: Exception: \n", e);
        }
        this.closeDB();

        return events;
    }

    private LocalEvent getLocalEventToCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                return new LocalEvent(
                        cursor.getString(cursor.getColumnIndex(MYEVENTS_NAME)),
                        cursor.getString(cursor.getColumnIndex(MYEVENTS_DESCRIPTION)),
                        Date.valueOf(cursor.getString(cursor.getColumnIndex(MYEVENTS_DAY))),
                        cursor.getString(cursor.getColumnIndex(MYEVENTS_ADDRESS))
                );
            } catch (Exception e) {
                Log.e(TAG, "getLocalEventToCursor: ",e);
                return null;
            }
        }
    }

    public void removeEvent(LocalEvent event) {
        openWritableDatabase();

        String where = MYEVENTS_ADDRESS + " = ? AND "+ MYEVENTS_DAY + " = ?";
        String[] args = { event.getAddress(), event.getDayString() };
        db.delete(MYEVENTS, where, args);

        closeDB();
    }

    public void updateLocalEvent(LocalEvent oldEvent, LocalEvent newEvent) {
        openWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MYEVENTS_NAME, newEvent.getName());
        cv.put(MYEVENTS_DESCRIPTION, newEvent.getDescription());
        cv.put(MYEVENTS_DAY, newEvent.getDayString());
        cv.put(MYEVENTS_ADDRESS, newEvent.getAddress());

        String where = MYEVENTS_ADDRESS + " = ? AND "+MYEVENTS_DAY+" = ?";
        String[] args = { oldEvent.getAddress(), oldEvent.getDayString() };

        db.update(MYEVENTS, cv, where, args );

        closeDB();
    }

    public void importEvents(ArrayList<LocalEvent> events) {
        openWritableDatabase();
        db.delete(MYEVENTS, null, null);

        for(LocalEvent event : events) {
            ContentValues cv = new ContentValues();
            cv.put(MYEVENTS_NAME, event.getName());
            cv.put(MYEVENTS_DESCRIPTION, event.getDescription());
            cv.put(MYEVENTS_DAY, String.valueOf(event.getDayString()));
            cv.put(MYEVENTS_ADDRESS, event.getAddress());

            db.insert(MYEVENTS, null, cv);
        }
        this.closeDB();
    }

    public void deleteMyEvents() {
        openWritableDatabase();
        db.delete(MYEVENTS, null,null);
        closeDB();
    }

    public void deleteFollowedEvents() {
        openWritableDatabase();
        db.delete(FOLLOWEDEVENTS, null,null);
        closeDB();
    }

    public void clearDB() {
        Log.d(TAG, "clearDB: ");
        openWritableDatabase();

        deleteEvents();
        deleteUser();
        deleteMyEvents();
        deleteFollowedEvents();

        closeDB();
    }

    public ArrayList<Event> getFollowedEvents() {
        openReadableDatabase();
        ArrayList<Event> events = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM "+ FOLLOWEDEVENTS+";", null);
            //TODO da sostituire con db.query()
            int i = 0;
            while(cursor.moveToNext()) {
                events.add(getFollowedEventToCursor(cursor, i++));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "getEvents: Exception: \n", e);
        }
        this.closeDB();

        return events;
    }

    private Event getFollowedEventToCursor(Cursor cursor, int id) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                return new Event(
                        id,
                        cursor.getString(cursor.getColumnIndex(FOLLOWEDEVENTS_NAME)),
                        cursor.getString(cursor.getColumnIndex(FOLLOWEDEVENTS_DESCRIPTION)),
                        Date.valueOf(cursor.getString(cursor.getColumnIndex(FOLLOWEDEVENTS_DAY))),
                        cursor.getString(cursor.getColumnIndex(FOLLOWEDEVENTS_ADDRESS)),
                        cursor.getDouble(cursor.getColumnIndex(FOLLOWEDEVENTS_LAT)),
                        cursor.getDouble(cursor.getColumnIndex(FOLLOWEDEVENTS_LON)),
                        cursor.getString(cursor.getColumnIndex(FOLLOWEDEVENTS_OWNER))
                );
            } catch (Exception e) {
                Log.e(TAG, "getEventToCursor: ",e );
                return null;
            }
        }
    }

    public void importFollowedEvents(ArrayList<Event> events) {
        openWritableDatabase();
        db.delete(FOLLOWEDEVENTS, null, null);

        for(Event event : events) {
            ContentValues cv = new ContentValues();
            cv.put(FOLLOWEDEVENTS_ID, event.getId());
            cv.put(FOLLOWEDEVENTS_NAME, event.getName());
            cv.put(FOLLOWEDEVENTS_DESCRIPTION, event.getDescription());
            cv.put(FOLLOWEDEVENTS_DAY, String.valueOf(event.getDay()));
            cv.put(FOLLOWEDEVENTS_ADDRESS, event.getAddress());
            cv.put(FOLLOWEDEVENTS_LAT, event.getLat());
            cv.put(FOLLOWEDEVENTS_LON, event.getLon());
            cv.put(FOLLOWEDEVENTS_OWNER, event.getOwner());

            db.insert(FOLLOWEDEVENTS, null, cv);
        }
        closeDB();
    }

    public void insertFollowedEvent(Event event) {
        openWritableDatabase();
        String[] args = {String.valueOf(event.getLat()), String.valueOf(event.getLon()), event.getDayString()};
        Cursor cursor = db.rawQuery("SELECT name FROM "+FOLLOWEDEVENTS+" WHERE "+FOLLOWEDEVENTS_LAT+" = ? AND "+FOLLOWEDEVENTS_LON +" = ? AND "+FOLLOWEDEVENTS_DAY+" = ?", args);
        if(cursor.getCount()>0) {
            closeDB();
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(FOLLOWEDEVENTS_ID, event.getId());
        cv.put(FOLLOWEDEVENTS_NAME, event.getName());
        cv.put(FOLLOWEDEVENTS_DESCRIPTION, event.getDescription());
        cv.put(FOLLOWEDEVENTS_DAY, String.valueOf(event.getDay()));
        cv.put(FOLLOWEDEVENTS_ADDRESS, event.getAddress());
        cv.put(FOLLOWEDEVENTS_LAT, event.getLat());
        cv.put(FOLLOWEDEVENTS_LON, event.getLon());
        cv.put(FOLLOWEDEVENTS_OWNER, event.getOwner());
        db.insert(FOLLOWEDEVENTS, null, cv);
        cursor.close();
        closeDB();
    }

    public void deleteFollowedEvent(Event event) {
        openWritableDatabase();
        String where = FOLLOWEDEVENTS_LAT+" = ? AND "+FOLLOWEDEVENTS_LON+" = ? AND "+FOLLOWEDEVENTS_DAY+" = ?";
        String [] args = {String.valueOf(event.getLat()), String.valueOf(event.getLon()), event.getDayString()};
        db.delete(FOLLOWEDEVENTS, where, args );
        closeDB();
    }

    public boolean thereAreEventsToday() {
        openReadableDatabase();
        Calendar calendar = Calendar.getInstance();
        String month;
        if(calendar.get(Calendar.MONTH)+1<10) {
            month = "0" + calendar.get(Calendar.MONTH) + 1;
        } else {
            month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        }
        String today = calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String[] args = {today};
        Cursor cursor = db.rawQuery("SELECT * FROM " + FOLLOWEDEVENTS + " WHERE " + FOLLOWEDEVENTS_DAY + " = ?", args);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        closeDB();
        return result;
    }

    public boolean isFollowed(Event event) {
        openReadableDatabase();
        boolean result = false;
        String[] args = {String.valueOf(event.getLat()), String.valueOf(event.getLon()), event.getDayString()};
        Cursor cursor = db.rawQuery("SELECT name FROM "+FOLLOWEDEVENTS+" WHERE "+FOLLOWEDEVENTS_LAT+" = ? AND "+FOLLOWEDEVENTS_LON +" = ? AND "+FOLLOWEDEVENTS_DAY+" = ?", args);
        if(cursor.getCount()>0)
            result = true;
        cursor.close();
        closeDB();
        return result;
    }
}
