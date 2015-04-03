package regionhovedstaden;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.HashMap;

import regionhovedstaden.ui.HovedMenu;
import regionhovedstaden.ui.R;



public class MainActivity extends Activity implements BeaconConsumer{

    protected static final String TAG = "Beacon ID & AFSTAND: ";
    private BeaconManager beaconManager;
    private HashMap beaconMap = App.beaconMap;

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

            setPatient();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {

            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                for (Beacon beacon : beacons) {
                    Log.i(TAG, "Afstanden til Beacn: " + beacon.toString()
                            + " Er ca: " + beacon.getDistance());
                    beaconMap.put(beacon.toString(), beacon.getDistance());

                }
                App.clear = true;
                unBindBeacon();

            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(
                    new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }

    }

    private void unBindBeacon(){

        beaconManager.unbind(this);

    }

    public void bindBeacon(){

        beaconManager.bind(this);

    }

    public void setPatient(){

        App.patientNavn = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("patientNavn", "Intet Navn");

        App.patientStue = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("patientStue", "Ingen Stue");

    }

}
