package lpaa.earound;


public class DBQuery {

    public static final String DB_NAME = "around.db";
    public static final int DB_VERSION = 1;

    public static final String USERDATA = "UserData";
    public static final String USERDATA_KEEP = "keep";
    public static final String USERDATA_USER = "user";
    public static final String USERDATA_CITY = "city";

    public static final String EVENTS = "Events";
    public static final String EVENTS_ID = "eventID";
    public static final String EVENTS_NAME = "name";
    public static final String EVENTS_DESCRIPTION = "description";
    public static final String EVENTS_DATE = "date";
    public static final String EVENTS_ADDRESS = "address";
    public static final String EVENTS_LAT = "lat";
    public static final String EVENTS_LON = "lon";
    public static final String EVENTS_IMAGE = "image";

    public static final String CREATE_USERDATA_TABLE =
            "CREATE TABLE "+USERDATA+"( " +
                    USERDATA_KEEP+" INTEGER NOT NULL," +
                    USERDATA_USER+" TEXT PRIMARY KEY," +
                    USERDATA_CITY+" TEXT);";

    public static final String DROP_USERDATA_TABLE =
            "DROP TABLE IF EXISTS "+USERDATA+";";

    /*public static final String INIT_USERDATA_TABLE =
            "INSERT INTO "+USERDATA+" VALUES(1, 'faber', null);";*/

    /*TODO creare query per tabella eventi da salvare in locale*/
    public static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE "+EVENTS + " ( " +
                    EVENTS_ID + " INTEGER NOT NULL PRIMARY KEY," +
                    EVENTS_NAME + " TEXT," +
                    EVENTS_DESCRIPTION + " TEXT, " +
                    EVENTS_DATE + "TEXT, " +
                    EVENTS_ADDRESS + "TEXT," +
                    EVENTS_LAT + "FLOAT," +
                    EVENTS_LON + "FLOAT," +
                    EVENTS_IMAGE + "TEXT);";

    public static final String DROP_EVENTS_TABLE =
            "DROP TABLE IF EXISTS " + EVENTS + ";";

}
