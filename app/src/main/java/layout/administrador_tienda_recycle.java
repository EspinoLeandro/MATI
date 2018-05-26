package layout;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cj.mati.R;
import com.cj.mati.TiendaInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adaptadores.TiendasAdaptador;
import Firebase.Referencias;
import Firebase.Tienda;

public class administrador_tienda_recycle extends Fragment implements TiendasAdaptador.OnTiendasListener {

    private RecyclerView tiendas;
    private List<Tienda> tiendasList = new ArrayList<>();
    private TiendasAdaptador adapter;

    public administrador_tienda_recycle() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_administrador_tienda_recycle, container, false);

        tiendas = (RecyclerView) view.findViewById(R.id.recycleTiendas);
        adapter = new TiendasAdaptador(tiendasList, getContext());
        adapter.setListener(this);
        tiendas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tiendas.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTiendas();
    }

    private void getTiendasFiltro() {
        String filtro = getArguments().getString("FILTRO");
        if (filtro.contains("GIRO")) {
            String giro = filtro.replace("GIRO", "");
            Tienda tienda;
            for (int i = 0; i<tiendasList.size(); i++) {
                tienda = tiendasList.get(i);
                if (!tienda.getGiro().equals(giro)) {
                    tiendasList.remove(i);
                    i--;
                }
            }
        }else{
            String lugar = filtro;
            Tienda tienda;
            for (int i = 0; i<tiendasList.size(); i++) {
                tienda = tiendasList.get(i);
                if (!tienda.getColonia().equals(lugar)) {
                    tiendasList.remove(i);
                    i--;
                }
            }
        }
    }

    private void getTiendas() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        } catch (Exception e) {

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        myRef.child(Referencias.TIENDAS_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tiendasList.clear();
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    Tienda obj = snapshot.getValue(Tienda.class);
                    tiendasList.add(obj);
                }
                try {
                    if(!getArguments().isEmpty()){
                        getTiendasFiltro();
                    }
                }catch (Exception e){
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClickItemTiendas(Tienda tienda) {
        TiendaInfo.tienda = tienda;
        Intent intent = new Intent(getContext(), TiendaInfo.class);
        startActivity(intent);
    }

    @Override
    public void onClickLongItemTiendas() {

    }
}
