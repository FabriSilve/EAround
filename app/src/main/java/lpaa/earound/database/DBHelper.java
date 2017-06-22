package lpaa.earound.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static lpaa.earound.database.DBQuery.*;


class DBHelper extends SQLiteOpenHelper {

    private final String TAG = "DBHelper";

    DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(TAG, "DBHelper: costructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: start");

        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_USERDATA_TABLE);
        db.execSQL(CREATE_MYEVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: start");

        db.execSQL(DROP_EVENTS_TABLE);
        db.execSQL(DROP_USERDATA_TABLE);
        db.execSQL(DROP_MYEVENTS_TABLE);
        onCreate(db);
        /*TODO da sostituire con ALTERTABLE dopo fase di testing*/
    }
}
