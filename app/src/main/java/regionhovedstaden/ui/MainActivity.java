package regionhovedstaden.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){


            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LogInd logIn = new LogInd();
            fragmentTransaction.add(R.id.container, logIn);
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

    public void bygBesked(String message){

        String navn = PreferenceManager.getDefaultSharedPreferences(this).getString("patientNavn", "Intet Navn");
        String cpr = PreferenceManager.getDefaultSharedPreferences(this).getString("patientCpr", "Intet CPR");
        String stue = PreferenceManager.getDefaultSharedPreferences(this).getString("patientStue", "Ingen Stue");

        String besked = navn+";"+cpr+";"+stue+";"+message;

        System.out.println("BESKED: " + besked);

    }

}
