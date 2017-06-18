package lpaa.earound;


public class DBQuery {

    public static final String DB_NAME = "earoundDB.db";
    public static final int DB_VERSION = 3;

    public static final String USERDATA = "UserData";
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
                    USERDATA_USER+" TEXT PRIMARY KEY," +
                    USERDATA_CITY+" TEXT);";

    public static final String DROP_USERDATA_TABLE =
            "DROP TABLE IF EXISTS "+USERDATA+";";

    /*public static final String INIT_USERDATA_TABLE =
            "INSERT INTO "+USERDATA+" VALUES(1, 'faber', null);";*/

    public static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE "+EVENTS + " ( " +
                    EVENTS_ID + " INTEGER PRIMARY KEY," +
                    EVENTS_NAME + " TEXT," +
                    EVENTS_DESCRIPTION + " TEXT, " +
                    EVENTS_DATE + "TEXT, " +
                    EVENTS_ADDRESS + "TEXT," +
                    EVENTS_LAT + "BLOB," +
                    EVENTS_LON + "BLOB," +
                    EVENTS_IMAGE + "TEXT);";

    public static final String DROP_EVENTS_TABLE =
            "DROP TABLE IF EXISTS " + EVENTS + ";";

    public static final String test = "CREATE TABLE Pippo (name TEXT PRIMARY KEY );";

    //TODO utile?
    public static final String CREATE_DB = CREATE_USERDATA_TABLE + CREATE_EVENTS_TABLE;
}
