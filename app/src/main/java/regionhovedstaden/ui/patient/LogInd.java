package regionhovedstaden.ui.patient;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import regionhovedstaden.MainActivity;
import regionhovedstaden.ui.R;


public class LogInd extends Fragment implements View.OnClickListener{

    Button knap;
    EditText indtastetNavn, intastetCpr, indtastetStue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View logInd = inflater.inflate(R.layout.fragment_log_in, container, false);

        knap = (Button) logInd.findViewById(R.id.logind_knap);
        knap.setOnClickListener(this);
        indtastetNavn = (EditText) logInd.findViewById(R.id.et_navn);
        intastetCpr = (EditText) logInd.findViewById(R.id.et_cpr);
        indtastetStue = (EditText) logInd.findViewById(R.id.et_stue);

        if(PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("patientNavn", "Intet Navn")!= "Intet Navn"){

            opretHovedMenu();

        }

        return logInd;
    }
    @Override
    public void onClick(View view) {
        if (view == knap){
            System.out.println("HEJ!");

            if (indtastetNavn.getText().toString().equals("")){
                toast();
            }
            else if (intastetCpr.getText().toString().equals("")){
                toast();
            }
            else if (indtastetStue.getText().toString().equals("")){
                toast();
            }
            else {
                gemData();
                sletTekst();

                opretHovedMenu();
            }

        }
    }
    /*
    Gemmer data i shared preferences.
     */
    private void gemData(){

             PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                     .putString("patientNavn", indtastetNavn.getText().toString())
                     .putString("patientCpr", intastetCpr.getText().toString())
                     .putString("patientStue", indtastetStue.getText().toString())
                     .commit();

    }
    private void toast(){

        Toast.makeText(getActivity(), "Udfyld alle informationer, Tak!",
                Toast.LENGTH_SHORT).show();
    }

    private void sletTekst(){

        indtastetNavn.setText("");
        intastetCpr.setText("");
        indtastetStue.setText("");

    }

    public void opretHovedMenu(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new PatientHovedMenu());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
