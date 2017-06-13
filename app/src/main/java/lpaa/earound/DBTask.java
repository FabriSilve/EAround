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

    public UserData getUser() {
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
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                Log.d("DBTask", "getUserToCursor: find user "+cursor.getString(cursor.getColumnIndex(USERDATA_USER)));
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
        openWritableDatabase();
        db.execSQL("DELETE FROM "+USERDATA+" WHERE 1=1");
        /*TODO provare preparestatement
            db.delete(USERDATA, null, null);
         */
        closeDB();
    }

    public void insertEvents(ArrayList<Event> events) {
        //TODO completare funzione inserimento eventi in locale
    }

    public void deleteEvents() {
        //TODO completare funzione cancellazione eventi in locale
    }

}
