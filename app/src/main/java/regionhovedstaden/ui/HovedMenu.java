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

import regionhovedstaden.ui.patient.LogInd;
import regionhovedstaden.ui.patient.PatientHovedMenu;
import regionhovedstaden.ui.plejer.PlejerHovedMenu;


public class HovedMenu extends Fragment implements View.OnClickListener {

    Button patient, plejer;
    String brugerType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hovedMenu = inflater.inflate(R.layout.fragment_hoved_menu, container, false);

        patient = (Button)hovedMenu.findViewById(R.id.menu_patient_knap);
        patient.setOnClickListener(this);
        plejer = (Button)hovedMenu.findViewById(R.id.menu_plejer_knap);
        plejer.setOnClickListener(this);

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

        }
        else {

            fragmentTransaction.replace(R.id.container, new PlejerHovedMenu());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            brugerType = "plejer";

        }

        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putString("brugerType", brugerType).commit();

    }
}
