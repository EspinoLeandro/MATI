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
public class Administrador_Tienda extends Fragment {


    public Administrador_Tienda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentV
        View vista = inflater.inflate(R.layout.fragment_administrador__tienda, container, false);

        Fragment fragment = new administrador_tienda_recycle();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_tiendas,fragment).commit();

        return vista;
    }

}
