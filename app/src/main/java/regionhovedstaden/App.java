package regionhovedstaden;

import android.app.Application;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 * Created by Andras on 01-04-2015.
 */
public class App extends Application {

    public static String BeaconID =
            "id1: f7826da6-4fa2-4e98-8024-bc5b71e0893e id2: 52301 id3: 58392";

    public static HashMap beaconMap = new HashMap<String, Double>();

    public static String patientNavn = "";

    public static String patientStue = "";

    public static String brugerType = "";

    public static boolean clear = false;
}
