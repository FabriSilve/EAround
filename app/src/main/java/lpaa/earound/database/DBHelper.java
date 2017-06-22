package lpaa.earound.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class DBHelper extends SQLiteOpenHelper {

    private final String TAG = "DBHelper";

    DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(TAG, "DBHelper: costructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: start");

        db.execSQL(DBQuery.CREATE_EVENTS_TABLE);
        db.execSQL(DBQuery.CREATE_USERDATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: start");

        db.execSQL(DBQuery.DROP_EVENTS_TABLE);
        db.execSQL(DBQuery.DROP_USERDATA_TABLE);
        onCreate(db);
        /*TODO da sostituire con ALTERTABLE dopo fase di testing*/
    }
}
