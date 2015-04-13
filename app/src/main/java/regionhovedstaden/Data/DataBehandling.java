package regionhovedstaden.Data;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Random;

/**
 * Created by Andras on 03-04-2015.
 */
public class DataBehandling{

    BeaconAfstande beaconAfstande = new BeaconAfstande();

    private String beaconId = "";
    private String navn = "";
    private String stue = "";
    private String patientKald = "";

    public String bygPatientKald(String kald, String navn, String stue){

        beaconId = beaconAfstande.findTaettesteBeacon();
        String id = random();

        String besked = kald + "!" + id + "!" + beaconId + "!" + navn + "!" + stue;

        Log.d("bygPatientKald()", besked);

        App.send = true;

        return besked;
    }

    public String bygPlejerSvar(String kId, String beaconId){

        String afstand = beaconAfstande.findBeaconAfstand(beaconId);
        String kaldId = kId;

        String svar = "sygeplejerBeacon"+ "!" + kaldId + "!" + afstand;
        System.out.println("------------------------------------------"+svar+"---------------------------------------------");

        App.send = true;

        return svar;
    }

    public String random() {
        final String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:%&/()?";
        Random rnd = new Random();
        int len = 10;

        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        System.out.println("RANDOM PASSWORD: " + sb.toString());
        return sb.toString();
    }

    public void svarBehandling(String svar){

        System.out.println("-----------------------SVAR BEHANDLES--------------------------");

        String[] response = svar.split("!");
        String besked = response[0];
        String kaldId = response[1];

        if(besked.equals("hvorerdu")){
            System.out.println("---------------------------------------"+kaldId+"---------------------------------");
            beaconId = response[2];

            sendMessage(besked, kaldId, beaconId);

        }
        else if(besked.equals("KanDuTageDen")){

            navn = response[2];
            stue = response[3];
            patientKald = response[4];

            sendMessage(besked, kaldId, beaconId);

        }

        else if(besked.equals("Confirmation")){

            String confirmation = response[1];

            sendMessage(besked, confirmation, beaconId);

        }

    }

    private void sendMessage(String beksed, String kId, String beaconId) {

        String msg = beksed;
        String kald = kId;

        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");

        // You can also include some extra data.
        if(msg.equals("hvorerdu")) {
            intent.putExtra("message", beksed).putExtra("kaldId", kald)
                    .putExtra("beaconId", beaconId);
        }
        else if(msg.equals("KanDuTageDen")) {
            intent.putExtra("message", beksed).putExtra("navn", navn).putExtra("stue", stue)
                    .putExtra("patientKald", patientKald).putExtra("kaldId", kald);
        }
        else if(msg.equals("Confirmation")){
            intent.putExtra("message", kald);
        }
        LocalBroadcastManager.getInstance(App.context).sendBroadcast(intent);

    }
}
