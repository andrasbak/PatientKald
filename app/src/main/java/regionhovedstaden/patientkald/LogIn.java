package regionhovedstaden.patientkald;

import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LogIn extends Fragment implements View.OnClickListener{

    Button knap;
    TextView navn, cpr, stue;
    EditText inavn, icpr, istue;
    ImageView logo;
    String pnavn, pcpr, pstue;

    //hej

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View logInd = inflater.inflate(R.layout.fragment_log_in, container, false);

        knap = (Button) logInd.findViewById(R.id.logind_knap);
        knap.setOnClickListener(this);

        inavn = (EditText) logInd.findViewById(R.id.et_navn);
        icpr = (EditText) logInd.findViewById(R.id.et_cpr);
        istue = (EditText) logInd.findViewById(R.id.et_stue);




        return logInd;
    }
    @Override
    public void onClick(View view) {

        if (view == knap){

            System.out.println("HEJ!");
            gemData();



        }

    }

    private void gemData(){

        if (inavn.getText().toString().equals("")){
            toast();
        }

        else if (icpr.getText().toString().equals("")){
            toast();
        }

        else if (istue.getText().toString().equals("")){
            toast();
        }

        else{
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putString(pnavn, inavn.getText().toString())
                .putString(pcpr, icpr.getText().toString())
                .putString(pstue, istue.getText().toString()).commit();
        }

    }

    private void toast(){

        Toast.makeText(getActivity(), "Udfyld alle informationer, Tak!",
                Toast.LENGTH_SHORT).show();

    }
}
