package lpaa.earound.database;


class DBQuery {

    static final String DB_NAME = "earound.db";
    static final int DB_VERSION = 9;

    static final String EVENTS = "Events";
    private static final String EVENTS_ID = "id";
    static final String EVENTS_NAME = "name";
    static final String EVENTS_DESCRIPTION = "description";
    static final String EVENTS_DAY = "day";
    static final String EVENTS_ADDRESS = "address";
    static final String EVENTS_LAT = "lat";
    static final String EVENTS_LON = "lon";
    static final String EVENTS_OWNER = "owner";

    static final String USERDATA = "UserData";
    static final String USERDATA_USERNAME = "username";

    static final String MYEVENTS = "MyEvent";
    static final String MYEVENTS_NAME = "name";
    static final String MYEVENTS_DESCRIPTION = "description";
    static final String MYEVENTS_DAY = "day";
    static final String MYEVENTS_ADDRESS = "address";

    static final String FOLLOWEDEVENTS = "FollowedEvents";
    private static final String FOLLOWEDEVENTS_ID = "id";
    static final String FOLLOWEDEVENTS_NAME = "name";
    static final String FOLLOWEDEVENTS_DESCRIPTION = "description";
    static final String FOLLOWEDEVENTS_DAY = "day";
    static final String FOLLOWEDEVENTS_ADDRESS = "address";
    static final String FOLLOWEDEVENTS_LAT = "lat";
    static final String FOLLOWEDEVENTS_LON = "lon";
    static final String FOLLOWEDEVENTS_OWNER = "owner";


    static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE "+EVENTS + " ( " +
                    EVENTS_ID + " INTEGER PRIMARY KEY, "+
                    EVENTS_NAME + " TEXT," +
                    EVENTS_DESCRIPTION + " TEXT, " +
                    EVENTS_DAY + " TEXT, "+
                    EVENTS_ADDRESS + " TEXT, " +
                    EVENTS_LAT + " REAL," +
                    EVENTS_LON + " REAL," +
                    EVENTS_OWNER + " TEXT" +
                    ");";


    static final String DROP_EVENTS_TABLE =
            "DROP TABLE IF EXISTS " + EVENTS + ";";


    static final String CREATE_USERDATA_TABLE =
            "CREATE TABLE "+ USERDATA + " ( " + USERDATA_USERNAME + " PRIMARY KEY);";

    static final String DELETE_USERDATA =
            "DELETE FROM " + USERDATA + ";";

    static final String DROP_USERDATA_TABLE =
            "DROP TABLE IF EXISTS " + USERDATA + ";";


    static final String CREATE_MYEVENTS_TABLE =
            "CREATE TABLE "+MYEVENTS + " ( " +
                    MYEVENTS_NAME + " TEXT," +
                    MYEVENTS_DESCRIPTION + " TEXT, " +
                    MYEVENTS_DAY + " TEXT, "+
                    MYEVENTS_ADDRESS + " TEXT," +
                    "PRIMARY KEY ("+ MYEVENTS_DAY +", "+MYEVENTS_ADDRESS+")"+
                    ");";

    static final String DROP_MYEVENTS_TABLE =
            "DROP TABLE IF EXISTS " + MYEVENTS + ";";

    static final String CREATE_FOLLOWEDEVENTS_TABLE =
            "CREATE TABLE "+FOLLOWEDEVENTS + " ( " +
                    FOLLOWEDEVENTS_ID + " INTEGER PRIMARY KEY, "+
                    FOLLOWEDEVENTS_NAME + " TEXT," +
                    FOLLOWEDEVENTS_DESCRIPTION + " TEXT, " +
                    FOLLOWEDEVENTS_DAY + " TEXT, "+
                    FOLLOWEDEVENTS_ADDRESS + " TEXT, " +
                    FOLLOWEDEVENTS_LAT + " REAL," +
                    FOLLOWEDEVENTS_LON + " REAL," +
                    FOLLOWEDEVENTS_OWNER + " TEXT" +
                    ");";

    static final String DROP_FOLLOWEDEVENTS_TABLE =
            "DROP TABLE IF EXISTS " + FOLLOWEDEVENTS + ";";
}
