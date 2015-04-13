package regionhovedstaden.ui.plejer;

import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import regionhovedstaden.gcm.GcmUnRegistrationAsyncTask;
import regionhovedstaden.ui.R;

public class PlejerHovedMenu extends Fragment implements View.OnClickListener {

    Button assistance;
    TextView afdeling;
    String gemtAfdeling;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View plejerHovedMenu = inflater
                .inflate(R.layout.fragment_plejer_hoveh_menu, container, false);

        assistance = (Button)plejerHovedMenu.findViewById(R.id.assistance_knap);
        assistance.setOnClickListener(this);
        afdeling = (TextView)plejerHovedMenu.findViewById(R.id.plejer_afdeling_tv);

        hentAfdeling();

        return plejerHovedMenu;
    }


    @Override
    public void onClick(View view) {

        System.out.println("DU HAR TRYKKET ASSISTANCE!");

    }

    public void hentAfdeling(){

        gemtAfdeling = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("afdeling", "");

        afdeling.setText(gemtAfdeling);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        new GcmUnRegistrationAsyncTask(getActivity()).execute();

    }
}
