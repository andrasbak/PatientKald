package regionhovedstaden.ui;


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

import regionhovedstaden.Data.App;
import regionhovedstaden.gcm.GcmRegistrationAsyncTask;
import regionhovedstaden.ui.patient.LogInd;
import regionhovedstaden.ui.plejer.PlejerHovedMenu;


public class HovedMenu extends Fragment implements View.OnClickListener {

    Button patient, plejer;
    String brugerType;
    EditText afdeling;
    String gemtAfdeling;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hovedMenu = inflater.inflate(R.layout.fragment_hoved_menu, container, false);

        patient = (Button)hovedMenu.findViewById(R.id.menu_patient_knap);
        patient.setOnClickListener(this);
        plejer = (Button)hovedMenu.findViewById(R.id.menu_plejer_knap);
        plejer.setOnClickListener(this);
        afdeling = (EditText)hovedMenu.findViewById(R.id.afdeling_et);
        afdeling.setOnClickListener(this);

        hentAfdeling();

        return hovedMenu;
    }

    @Override
    public void onClick(View view) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(view.equals(patient)){

            fragmentTransaction.replace(R.id.container, new LogInd());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            brugerType = "patient";
            registrer();

        }
        else if(view.equals(plejer)) {

            fragmentTransaction.replace(R.id.container, new PlejerHovedMenu());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            brugerType = "plejer";
            registrer();

        }

        else{

            afdeling.setText("");

        }

        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putString("brugerType", brugerType)
                .putString("afdeling", afdeling.getText().toString()).commit();

        App.brugerType = brugerType;
    }

    public void hentAfdeling(){

        gemtAfdeling = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("afdeling", "");

        afdeling.setText(gemtAfdeling);

    }

    public void registrer(){

        new GcmRegistrationAsyncTask(getActivity()).execute();


    }
}
