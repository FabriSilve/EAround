package lpaa.earound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import static lpaa.earound.DBQuery.*;

/**
 * Created by Fabrizio on 08/06/2017.
 */

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

        String where = USERDATA_KEEP +" = ?";
        String[] whereArgs = { "1" };

        this.openReadableDatabase();
        Cursor cursor = db.query(USERDATA, null, where, whereArgs, null, null, null);

        ArrayList<UserData> users = new ArrayList<UserData>();
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
                        cursor.getInt(cursor.getColumnIndex(USERDATA_KEEP)),
                        cursor.getString(cursor.getColumnIndex(USERDATA_USER)),
                        cursor.getString(cursor.getColumnIndex(USERDATA_CITY))
                );
                return user;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public long insertUser(UserData user) {
        Log.d(TAG, "insertUser: start");
        ContentValues cv = new ContentValues();
        cv.put(USERDATA_KEEP, user.getKeep());
        cv.put(USERDATA_USER, user.getUsername());
        cv.put(USERDATA_CITY, user.getCity());

        openWritableDatabase();

        long rowID = db.insert(USERDATA, null, cv);
        this.closeDB();

        return rowID;
    }

    public void deleteUser() {
        Log.d(TAG, "deleteUser: start");
        openWritableDatabase();
        db.execSQL("DELETE FROM "+USERDATA+" WHERE 1=1");
        /*TODO provare preparestatement
            db.delete(USERDATA, null, null);
         */
        closeDB();
    }

    public void insertEvents(ArrayList<Event> events) {
        Log.d(TAG, "insertEvents: start");
        //TODO completare funzione inserimento eventi in locale
    }

    public void deleteEvents() {
        Log.d(TAG, "deleteEvents: start");
        //TODO completare funzione cancellazione eventi in locale
    }
}
