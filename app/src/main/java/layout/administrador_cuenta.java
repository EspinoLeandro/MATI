package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cj.mati.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class administrador_cuenta extends Fragment {


    public administrador_cuenta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administrador_cuenta, container, false);
    }

}
