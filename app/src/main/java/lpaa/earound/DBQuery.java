package lpaa.earound;


public class DBQuery {

    public static final String DB_NAME = "earound.db";
    public static final int DB_VERSION = 1;

    public static final String EVENTS = "Events";
    public static final String EVENTS_NAME = "name";
    public static final String EVENTS_DESCRIPTION = "description";
    public static final String EVENTS_DATE = "date";

    public static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE "+EVENTS + " ( " +
                    EVENTS_NAME + " TEXT," +
                    EVENTS_DESCRIPTION + " TEXT, " +
                    EVENTS_DATE + "TEXT, " +
                    "PRIMARY KEY("+ EVENTS_NAME + ","+EVENTS_DATE + ");";

    public static final String DELETE_EVENTS =
            "DELETE * FROM " + EVENTS;

    public static final String DROP_EVENTS_TABLE =
            "DROP TABLE IF EXISTS " + EVENTS + ";";
}
