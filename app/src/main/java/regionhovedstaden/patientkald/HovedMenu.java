package regionhovedstaden.patientkald;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HovedMenu extends Fragment implements View.OnClickListener {

    Button logud, service, akut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hovedMenu = inflater.inflate(R.layout.fragment_hoved_menu, container, false);

        logud = (Button)hovedMenu.findViewById(R.id.logud_knap);
        logud.setOnClickListener(this);
        service = (Button)hovedMenu.findViewById(R.id.service_knap);
        service.setOnClickListener(this);
        akut = (Button)hovedMenu.findViewById(R.id.akut_knap);
        akut.setOnClickListener(this);

        return hovedMenu;
    }

    @Override
    public void onClick(View view) {

        if (view.equals(logud)){

            clearSharedPreferences();
            closeFragment();

        }

        else if (view.equals(service)){

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ServiceMenu());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            System.out.println("DU HAR TRYKKET SERVICE!");

        }
        else {

            System.out.println("DU HAR TRYKKET AKUT!");

        }

    }

    public void closeFragment(){

        getActivity().getFragmentManager().popBackStack();

    }

    public void clearSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        sharedPreferences.edit().clear().commit();

    }
}
