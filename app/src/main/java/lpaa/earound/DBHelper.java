package lpaa.earound;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static lpaa.earound.DBQuery.*;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USERDATA_TABLE);
        //TODO inserire chreazione tabella eventi

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("DBHelper", "Upgrading db from version "+oldVersion+" to "+newVersion);

        db.execSQL(DROP_USERDATA_TABLE);
        //TODO inserire drop tabella eventi
        /*TODO da sostituire con ALTERTABLE dopo fase di testing*/

        onCreate(db);
    }


}
