package regionhovedstaden.Data;

import android.util.Log;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Andras on 07-04-2015.
 */
public class BeaconAfstande {

    private HashMap beaconMap = App.beaconMap;

    public String findTaettesteBeacon(){

        String str = "";

        Object beaconValues = Collections.min(beaconMap.values());

        for (Object o : beaconMap.keySet()) {
            if (beaconMap.get(o).equals(beaconValues)) {

                str = o.toString();
            }
        }

        Log.d("findTaettesteBeacon()", "Tætteste Beacon er: " + str);
        return str;
    }

    public String findBeaconAfstand(String beaconId){

        String afstand = null;
        if(beaconMap.get(beaconId) == null){
            afstand = "999999";
        }
        else {
            afstand = beaconMap.get(beaconId).toString();
        }
        System.out.println("AFSTAND TIL BEACON: " + beaconId + " ER: " + afstand);

        return afstand;
    }

}
