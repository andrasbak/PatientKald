package regionhovedstaden.patientkald;

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


public class LogIn extends Fragment implements View.OnClickListener{

    Button knap;
    EditText indtastetNavn, intastetCpr, indtastetStue;
    String patientNavn, patientCpr, patientStue;

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

        return logInd;
    }
    @Override
    public void onClick(View view) {
        if (view == knap){
            System.out.println("HEJ!");
            //gemData();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new HovedMenu());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
    /*
    Tjekker om data er indtastet i alle felter og gemmer data'en.
     */
    private void gemData(){

        if (indtastetNavn.getText().toString().equals("")){
            toast();
        }
        else if (intastetCpr.getText().toString().equals("")){
            toast();
        }
        else if (indtastetStue.getText().toString().equals("")){
            toast();
        }
        else{
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putString(patientNavn, indtastetNavn.getText().toString())
                .putString(patientCpr, intastetCpr.getText().toString())
                .putString(patientStue, indtastetStue.getText().toString()).commit();
        }
    }
    private void toast(){

        Toast.makeText(getActivity(), "Udfyld alle informationer, Tak!",
                Toast.LENGTH_SHORT).show();
    }
}
