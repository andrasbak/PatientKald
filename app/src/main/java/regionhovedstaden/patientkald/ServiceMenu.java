package regionhovedstaden.patientkald;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class ServiceMenu extends Fragment implements View.OnClickListener {

    ImageButton vand,mad,wc,avis;
    String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View serviceMenu = inflater.inflate(R.layout.fragment_service_menu, container, false);

        vand = (ImageButton)serviceMenu.findViewById(R.id.vand_knap);
        vand.setOnClickListener(this);
        mad = (ImageButton)serviceMenu.findViewById(R.id.mad_knap);
        mad.setOnClickListener(this);
        wc = (ImageButton)serviceMenu.findViewById(R.id.wc_knap);
        wc.setOnClickListener(this);
        avis = (ImageButton)serviceMenu.findViewById(R.id.avis_knap);
        avis.setOnClickListener(this);

        return serviceMenu;
    }

    @Override
    public void onClick(View view) {

        if (view.equals(vand)){
            System.out.println("DU HAR TRYKKET VAND!");
            message = "vand";
        }
        else if (view.equals(mad)){
            System.out.println("DU HAR TRYKKET MAD!");
            message = "mad";
        }
        else if(view.equals(wc)){
            System.out.println("DU HAR TRYKKET WC!");
            message = "wc";
        }
        else{
            System.out.println("DU HAR TRYKKET AVIS!");
            message = "avis";
        }
        closeFragment();
    }

    public void closeFragment(){

        getActivity().getFragmentManager().popBackStack();

    }
}
