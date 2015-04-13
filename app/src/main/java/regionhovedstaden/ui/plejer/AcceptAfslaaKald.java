package regionhovedstaden.ui.plejer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import regionhovedstaden.gcm.GcmSendBesked;
import regionhovedstaden.ui.R;


public class AcceptAfslaaKald extends Fragment implements View.OnClickListener {

    TextView navn, stue, kald;
    Button accept, afslaa;
    String patientNavn="", patientStue = "", patientKald = "", kaldId = "", besked = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View acceptAfslaaKald = inflater.inflate(R.layout.fragment_accept_afslaa_kald, container, false);

        navn = (TextView)acceptAfslaaKald.findViewById(R.id.aa_navn_tw);
        stue = (TextView)acceptAfslaaKald.findViewById(R.id.aa_stue_tw);
        kald = (TextView)acceptAfslaaKald.findViewById(R.id.aa_kald_tw);

        accept = (Button)acceptAfslaaKald.findViewById(R.id.accept_knap);
        accept.setOnClickListener(this);
        afslaa = (Button)acceptAfslaaKald.findViewById(R.id.afslaa_knap);
        afslaa.setOnClickListener(this);

        Bundle extras = getArguments();

        patientNavn = extras.getString("navn").toUpperCase();
        patientStue = extras.getString("stue").toUpperCase();
        patientKald = extras.getString("patientKald").toUpperCase();
        kaldId = extras.getString("kaldId");

        navn.setText(patientNavn);
        stue.setText(patientStue);
        kald.setText(patientKald);


        return acceptAfslaaKald;
    }

    @Override
    public void onClick(View view) {

        if(view.equals(accept)){

            besked = "accept";

        }
        else{

            besked = "afslaa";

        }

        String msg = "sygeplejerAcceptDeny"+ "!" + kaldId + "!" + besked;

        new GcmSendBesked().execute(new Pair<Context, String>(getActivity(), msg));

        getActivity().getFragmentManager().popBackStack();

    }
}
