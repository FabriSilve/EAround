package lpaa.earound;

/**
 * Created by Fabrizio on 08/06/2017.
 */

public class DBQuery {

    public static final String DB_NAME = "around.db";
    public static final int DB_VERSION = 1;

    public static final String USERDATA = "UserData";
    public static final String USERDATA_KEEP = "keep";
    public static final String USERDATA_USER = "user";
    public static final String USERDATA_CITY = "city";

    public static final String CREATE_USERDATA_TABLE =
            "CREATE TABLE "+USERDATA+"( " +
                    USERDATA_KEEP+" INTEGER NOT NULL," +
                    USERDATA_USER+" TEXT PRIMARY KEY," +
                    USERDATA_CITY+" TEXT);";

    public static final String DROP_USERDATA_TABLE =
            "DROP TABLE IF EXIST "+USERDATA+";";

    /*public static final String INIT_USERDATA_TABLE =
            "INSERT INTO "+USERDATA+" VALUES(1, 'faber', null);";*/

    /*TODO creare query per tabella eventi da salvare in locale*/
    public static final String CREATE_EVENTS_TABLE =
            "";



}
