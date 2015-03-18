package regionhovedstaden;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLOutput;

import regionhovedstaden.netvaerk.SendBesked;
import regionhovedstaden.ui.HovedMenu;
import regionhovedstaden.ui.R;
import regionhovedstaden.ui.patient.LogInd;
import regionhovedstaden.ui.patient.PatientHovedMenu;
import regionhovedstaden.ui.plejer.PlejerHovedMenu;


public class MainActivity extends Activity {

    SendBesked sendBesked = new SendBesked();

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

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void bygBesked(String message, String check){

        String navn = PreferenceManager.getDefaultSharedPreferences(this).getString("patientNavn", "Intet Navn");
        String cpr = PreferenceManager.getDefaultSharedPreferences(this).getString("patientCpr", "Intet CPR");
        String stue = PreferenceManager.getDefaultSharedPreferences(this).getString("patientStue", "Ingen Stue");
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        String beacon = "BEACON";

        String besked = navn+";"+cpr+";"+stue+";"+message+";"+ip+";"+beacon+";"+check;
        System.out.println("Besked: "+besked);

        sendBesked.send(besked);

    }

}
