package layout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cj.mati.R;

public class Administrador_Capturista extends Fragment{

    BottomNavigationView  menu;
    Fragment fragment;

    public Administrador_Capturista() {
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id=item.getItemId();
            boolean b = false;

            switch (id){
                case R.id.menu_capturistas_agregar:
                    fragment=new Administrador__Capturista__Agregar();
                    b = true;
                    break;
                case R.id.menu_capturistas_editar:
                    fragment=new administrador_capturista_recycle().newInstance(true, false);
                    b = true;
                    break;
                case R.id.menu_capturistas_eliminar:
                    fragment=new administrador_capturista_recycle().newInstance(false,true);
                    b = true;
                    break;
            }

            if(b){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_capturistas,fragment).commit();
                return true;
            }else{
                return false;
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_capturistas,new Administrador__Capturista__Agregar()).commit();
        View view= inflater.inflate(R.layout.fragment_administrador__capturista, container, false);
        menu=(BottomNavigationView) view.findViewById(R.id.menu_administrador_capturistas);
        menu.setOnNavigationItemSelectedListener(listener);
        return view;
    }

}
