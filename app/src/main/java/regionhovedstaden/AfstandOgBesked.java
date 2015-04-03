package regionhovedstaden;

import android.preference.PreferenceManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Andras on 03-04-2015.
 */
public class AfstandOgBesked {

    private HashMap beaconMap = App.beaconMap;
    private String beacon = App.BeaconID;
    private String navn = App.patientNavn;
    private String stue = App.patientStue;

    public String findTaettesteBeacon(){

        String str = "";
        updaterFields();
        boolean clear = App.clear;

        if(clear == true) {
            Object beaconValues = Collections.min(beaconMap.values());

            for (Object o : beaconMap.keySet()) {
                if (beaconMap.get(o).equals(beaconValues)) {

                    str = o.toString();
                }
            }

        }
        System.out.println("TÆTTESTE BEACON ER: " + str);
        return str;
    }

    public String findBeaconAfstand(){

        updaterFields();

        String afstand = null;

        if(beaconMap.get(beacon) == (null)){
            afstand = "IKKE SYNLIG!";
        }
        else {
            afstand = beaconMap.get(beacon).toString();
        }
        System.out.println("AFSTAND TIL BEACON: " + beacon + " ER: " + afstand);

        return afstand;
    }

    public void bygPatientKald(String kald){

        updaterFields();

        String id = random();

        String besked = kald + ":" + beacon + ":" + navn + ":" + stue + ":" + id;

        System.out.println("BESKED: " + besked);

    }

    public static String random() {
        final String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!%&/()?:";
        Random rnd = new Random();
        int len = 10;

        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        System.out.println("RANDOM PASSWORD: " + sb.toString());
        return sb.toString();

    }

    public void updaterFields(){

        beaconMap = App.beaconMap;
        beacon = App.BeaconID;
        navn = App.patientNavn;
        stue = App.patientStue;

    }

}
