package lpaa.earound.database;


class DBQuery {

    static final String DB_NAME = "earound.db";
    static final int DB_VERSION = 1;

    static final String EVENTS = "Events";
    private static final String EVENTS_ID = "id";
    static final String EVENTS_NAME = "name";
    static final String EVENTS_DESCRIPTION = "description";
    static final String EVENTS_DATE = "date";
    static final String EVENTS_LAT = "lat";
    static final String EVENTS_LON = "lon";

    static final String USERDATA = "UserData";
    static final String USERDATA_USERNAME = "username";


    static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE "+EVENTS + " ( " +
                    EVENTS_ID + " INTEGER PRIMARY KEY, "+
                    EVENTS_NAME + " TEXT," +
                    EVENTS_DESCRIPTION + " TEXT, " +
                    EVENTS_DATE + " TEXT, "+
                    EVENTS_LAT + " REAL," +
                    EVENTS_LON + " REAL" +
                    ");";

    static final String DELETE_EVENTS =
            "DELETE FROM " + EVENTS + ";";

    static final String DROP_EVENTS_TABLE =
            "DROP TABLE IF EXISTS " + EVENTS + ";";

    static final String CREATE_USERDATA_TABLE =
            "CREATE TABLE "+ USERDATA + " ( " + USERDATA_USERNAME + " PRIMARY KEY);";

    static final String DELETE_USERDATA =
            "DELETE FROM " + USERDATA + ";";

    static final String DROP_USERDATA_TABLE =
            "DROP TABLE IF EXISTS " + USERDATA + ";";
}
