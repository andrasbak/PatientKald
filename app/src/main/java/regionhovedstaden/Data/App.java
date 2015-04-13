package regionhovedstaden.Data;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;

/**
 * Created by Andras on 01-04-2015.
 */
public class App extends Application {

    public static final String SENDER_ID = "213052711483";

    public static HashMap beaconMap = new HashMap<String, Double>();

    public static boolean send = false;

    public static Context context;

    public static String regId = "";

    public static String rootUrl = "http://192.168.1.64:8080/_ah/api/";

    public static String brugerType = "";

    public static String beaconId = "";


}
