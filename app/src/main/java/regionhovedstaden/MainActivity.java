package regionhovedstaden;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.HashMap;

import regionhovedstaden.Data.DataBehandling;
import regionhovedstaden.Data.App;
import regionhovedstaden.gcm.GcmSendBesked;
import regionhovedstaden.ui.HovedMenu;
import regionhovedstaden.ui.R;
import regionhovedstaden.ui.plejer.AcceptAfslaaKald;


public class MainActivity extends Activity implements BeaconConsumer{

    protected static final String TAG = "Beacon ID & AFSTAND: ";
    private BeaconManager beaconManager;
    private HashMap beaconMap = App.beaconMap;
    DataBehandling dataBehandling = new DataBehandling();

    String besked = "", patient = "", stue = "", kaldId = "", beaconId = "";
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            HovedMenu hovedMenu = new HovedMenu();
            fragmentTransaction.add(R.id.container, hovedMenu);
            fragmentTransaction.commit();

            beaconManager = BeaconManager.getInstanceForApplication(this);
            beaconManager.getBeaconParsers().add(new BeaconParser()
                    .setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));

            App.context = getApplicationContext();

            // Register to receive messages.
            // We are registering an observer (mMessageReceiver) to receive Intents
            // with actions named "custom-event-name".
            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                    new IntentFilter("custom-event-name"));

            getPatient();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //beaconManager.unbind(this);

    }

    /*@Override
    protected void onPause() {
        super.onPause();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }*/

    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            kaldId = intent.getStringExtra("kaldId");
            Log.d("receiver", "Got message: " + message);

            if (message.equals("hvorerdu")){
                System.out.println("-------------------------------------MESSAGE: hvorerdu------------------------------------");
                beaconId = intent.getStringExtra("beaconId");
                bindBeacon(message);

            }
            else if (message.equals("KanDuTageDen")){
                System.out.println("-------------------------------------MESSAGE: KanDuTageDen------------------------------------");
                String navn = intent.getStringExtra("navn");
                String stue = intent.getStringExtra("stue");
                String patientKald = intent.getStringExtra("patientKald");

                opretAcceptEllerAfslaa(navn, stue, patientKald, kaldId);
            }
            else if(message.equals("Confirmation")){
                System.out.println("-------------------------------------MESSAGE: Confirmation------------------------------------");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                for (Beacon beacon : beacons) {
                    Log.i(TAG, "Afstanden til Beacon: " + beacon.toString()
                            + " Er ca: " + beacon.getDistance());
                    beaconMap.put(beacon.toString(), beacon.getDistance());
                }

                if (i < 1) {

                    unBindBeacon();
                    Log.d(getClass().getName(), "Beacon Search DONE!");

                    if (besked.equals("hvorerdu")) {

                        sendPlejerSvar(kaldId);

                    }
                    else {
                        sendPatientKald(besked, patient, stue);
                    }
                    i++;
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(
                    new Region("myRangingUniqueId", null, null, null));
        }
        catch (RemoteException e) {
        }
    }

    public void unBindBeacon(){

        beaconManager.unbind(this);

    }

    public void bindBeacon(String message){

        besked = message;
        i = 0;
        beaconManager.bind(this);

        System.out.println("-----------------------------BEACON BOUND-------------------------------");

    }

    public void getPatient(){

        patient = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("patientNavn", "Intet Navn");

        stue = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("patientStue", "Ingen Stue");
    }

    public void sendPatientKald(String message, String patient, String stue) {

        String patientKald = dataBehandling.bygPatientKald(message, patient, stue);

        while(App.send == true) {

            new GcmSendBesked().execute(new Pair<Context, String>(this, patientKald));

            App.send = false;
        }
    }

    public void sendPlejerSvar(String kId){

        String kaldId = kId;
        String plejerSvar = dataBehandling.bygPlejerSvar(kaldId, beaconId);

        while(App.send == true){

            new GcmSendBesked().execute(new Pair<Context, String>(this, plejerSvar));

            App.send = false;
        }
    }

    public void opretAcceptEllerAfslaa(String navn, String stue, String patientKald, String kId){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle b = new Bundle();
        b.putString("navn", navn);
        b.putString("stue", stue);
        b.putString("patientKald", patientKald);
        b.putString("kaldId", kId);

        AcceptAfslaaKald acceptAfslaaKald = new AcceptAfslaaKald();
        acceptAfslaaKald.setArguments(b);

        fragmentTransaction.add(R.id.container, acceptAfslaaKald);
        fragmentTransaction.addToBackStack(null).commit();

    }

}
