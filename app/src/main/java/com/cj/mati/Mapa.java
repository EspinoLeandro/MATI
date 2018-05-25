package com.cj.mati;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Firebase.Referencias;
import Firebase.Tienda;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Tienda> tiendasList = getTiendas();
    private List<LatLng> pines = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventos();
    }


    private void eventos() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng pin;
        Double Longitud;
        Double Latitud;
        String titulo;

        for (Tienda obj : tiendasList) {
            Longitud = Double.parseDouble(obj.getLongitud());
            Latitud = Double.parseDouble(obj.getLatitud());
            titulo = obj.getNombreNegocio();
            pin = new LatLng(Latitud, Longitud);
            pines.add(pin);
            mMap.addMarker(new MarkerOptions().position(pin).title(titulo));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                String titulo = marker.getTitle();
                Intent intent;
                for (Tienda tienda: tiendasList) {
                    if(tienda.getNombreNegocio().equals(titulo)){
                        TiendaInfo.tienda=tienda;
                        intent = new Intent(getApplication(), TiendaInfo.class);
                        startActivity(intent);
                    }
                }
                return false;
            }
        });

        pin = new LatLng(19.708694,-100.517261);
        CameraPosition camera = new CameraPosition.Builder()
                .target(pin)
                .zoom(13)
                .bearing(0)
                .tilt(30)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
    }

    private List<Tienda> getTiendas() {
        final List<Tienda> tiendas = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        } catch (Exception e) {

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);


        myRef.child(Referencias.TIENDAS_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tiendas.clear();
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    Log.i("INFOs", snapshot.getValue().toString());
                    Tienda obj = snapshot.getValue(Tienda.class);
                    tiendas.add(obj);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tiendas;
    }

}
