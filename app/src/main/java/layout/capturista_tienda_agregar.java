package layout;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.mati.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Firebase.Tienda;
import Firebase.Referencias;
import Utilidades.Funciones;

/**
 * A simple {@link Fragment} subclass.
 */
public class capturista_tienda_agregar extends Fragment implements LocationListener{

    View vista;

    ArrayList<String> estados = new ArrayList<>();
    ArrayList<String> municipios = new ArrayList<>();
    ArrayList<String> colonias = new ArrayList<>();
    ArrayList<String> giros = new ArrayList<>();

    LocationManager lm;

    EditText NombreTienda;
    EditText Calle;
    Spinner Colonia;
    Spinner Municipio;
    Spinner Estado;
    Spinner Giro;
    EditText CodigoPostal;
    EditText Telefono;
    EditText Email;
    EditText Encargado;
    EditText edadEncargado;
    ImageButton Ubicacion;
    Button AgregarTienda;
    Button Cancelar;
    TextView Longitud;
    TextView Latitud;

    TextInputLayout TINombreTienda, TICalle, TIColonia, TIMunicipio, TIEstado, TICodigoPostal,
            TITelefono, TIEmail, TIEncargado,TIedadEncargado;

    Tienda tienda;

    public capturista_tienda_agregar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       vista = inflater.inflate(R.layout.fragment_capturista_tienda_agregar, container, false);
       loadControls();
       loadLists();
       loadSpinners();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        }catch (Exception e){}

        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        Ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarGPS();
            }
        });

        AgregarTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validar()){
                    myRef.child(Referencias.TIENDAS_REFERENCE).child(tienda.getNombreNegocio()).setValue(tienda);
                    Limpiar();
                }
            }
        });

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Limpiar();
            }
        });

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE); //getSystemService(Context.LOCATION_SERVICE);
        return vista;
    }

    private void loadLists() {
        estados.add("Michoacán");
        //-----------------------------------------
        municipios.add("Hidalgo");
        //-----------------------------------------
        colonias.add("Rincon de Dolores");
        colonias.add("Centro");
        colonias.add("La mangana");
        colonias.add("La morita");
        colonias.add("La fabrica");
        colonias.add("El zapotito");
        //-----------------------------------------
        giros.add("Abarrotes");

    }

    private void loadControls() {
        NombreTienda = (EditText) vista.findViewById(R.id.capturista_tienda_agregar_nombre_edt);
        Calle = (EditText) vista.findViewById(R.id.capturista_tienda_agregar_direccion_calle);
        Colonia = (Spinner) vista.findViewById(R.id.capturista_tienda_agregar_direccion_colonia);
        Municipio = (Spinner) vista.findViewById(R.id.capturista_tienda_agregar_direccion_municipio);
        Estado = (Spinner) vista.findViewById(R.id.capturista_tienda_agregar_direccion_estado);
        Giro = (Spinner) vista.findViewById(R.id.capturista_tienda_agregar_giro);
        CodigoPostal = (EditText) vista.findViewById(R.id.capturista_tienda_agregar_direccion_cp);
        Telefono = (EditText) vista.findViewById(R.id.capturista_tienda_agregar_telefono_edt);
        Email = (EditText) vista.findViewById(R.id.capturista_tienda_agregar_email_edt);
        Encargado = (EditText) vista.findViewById(R.id.capturista_tienda_agregar_nombre_encargado_edt);
        edadEncargado = (EditText) vista.findViewById(R.id.capturista_tienda_agregar_edad_encargado);

        TINombreTienda = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_nombre);
        TICalle = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_direccionCalle);
        //TIColonia = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_direccionColonia);
        //TIMunicipio = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_direccionMunicipio);
        //TIEstado = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_direccionEstado);
        TICodigoPostal = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_direccionCP);
        TITelefono = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_telefono);
        TIEmail = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_email);
        TIEncargado = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_nombre_encargado);
        TIedadEncargado = (TextInputLayout) vista.findViewById(R.id.capturista_tienda_agregar_edadEncargado);

        Ubicacion = (ImageButton) vista.findViewById(R.id.capturista_tienda_agregar_ubicacion);
        AgregarTienda = (Button) vista.findViewById(R.id.capturista_tienda_agregar_guardar_btn);
        Cancelar = (Button) vista.findViewById(R.id.capturista_tienda_agregar_cancelar_btn);

        Longitud = (TextView) vista.findViewById(R.id.capturista_tienda_agregar_longitud);
        Latitud = (TextView) vista.findViewById(R.id.capturista_tienda_agregar_latitud);

    }

    private void loadSpinners() {
        ArrayAdapter Aestados = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, estados);
        Estado.setAdapter(Aestados);
        ArrayAdapter Amunicipios = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, municipios);
        Municipio.setAdapter(Amunicipios);
        ArrayAdapter Acolonias = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, colonias);
        Colonia.setAdapter(Acolonias);
        ArrayAdapter Agiros = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, giros);
        Giro.setAdapter(Agiros);
    }

    private void consultarGPS() {
        if (gpsStatus()) {
            //Significa que el gps esta activo
            long time = 500;//Intervalo de tiempo para la lectura
            float minDistancia = 10;//Distancia minima para la lectura de ubicacion
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, time, minDistancia, this);
        } else {//Sino esta activado, lanzamos un Alert para activar el GPS
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setTitle("GPS Desactivado");
            alerta.setCancelable(false);
            alerta.setPositiveButton("Activar GPS", new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new
                                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            dialogInterface.cancel();
                        }
                    });
            alerta.setNegativeButton("Cancelar", new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            alerta.create();
            alerta.show();
        }
    }

    public boolean gpsStatus() {//Metodo para saber si el gps esta activado
        ContentResolver content = getContext().getContentResolver();
        boolean gps = Settings.Secure.isLocationProviderEnabled(
                content, LocationManager.GPS_PROVIDER);
        if (gps) {
            return true;
        } else {
            return false;
        }
    }

    private void Limpiar() {
        NombreTienda.setText("");
        Calle.setText("");
        /*Colonia.setText("");
        Municipio.setText("");
        Estado.setText("");*/
        CodigoPostal.setText("");
        Telefono.setText("");
        Email.setText("");
        Encargado.setText("");
        edadEncargado.setText("");
        Longitud.setText("Longitud:");
        Latitud.setText("Latitud:");
    }

    private boolean Validar(){

        TINombreTienda.setError("");
        TICalle.setError("");
        TICodigoPostal.setError("");
        TITelefono.setError("");
        TIEmail.setError("");
        TIEncargado.setError("");
        TIedadEncargado.setError("");

        Boolean valid = true;

        if(NombreTienda.getText().toString().isEmpty()){
            TINombreTienda.setError("Ingrese Nombre de la Tienda");
            valid=false;
        }
        if(Calle.getText().toString().isEmpty()){
            TICalle.setError("Ingrese Calle");
            valid=false;
        }
        if(CodigoPostal.getText().toString().isEmpty()){
            TICodigoPostal.setError("Ingrese Codigo Postal");
            valid=false;
        }
        if(Telefono.getText().toString().isEmpty()){
            TITelefono.setError("Ingrese Telefono");
            valid=false;
        }else if(!Patterns.PHONE.matcher(Telefono.getText().toString()).matches()){
            TITelefono.setError("Telefono invalido");
            valid= false;
        }
        if(Email.getText().toString().isEmpty()){
            TIEmail.setError("Ingrese Email");
            valid=false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
            TIEmail.setError("Correo invalido");
            valid= false;
        }
        if(Encargado.getText().toString().isEmpty()){
            TIEncargado.setError("Ingrese Encargado");
            valid=false;
        }
        if(edadEncargado.getText().toString().isEmpty()){
            TIedadEncargado.setError("Ingrese Edad del Ennargado");
            valid=false;
        }
        if(Longitud.getText().toString().equals("Longitud:") || Latitud.getText().toString().equals("Latitud:")) {
            Toast.makeText(getActivity(), "Longitud o Latitud inválidas", Toast.LENGTH_SHORT).show();
            valid=false;
        }

        if (valid){
            tienda = new Tienda(
                    Encargado.getText().toString(),
                    edadEncargado.getText().toString(),
                    NombreTienda.getText().toString(),
                    Calle.getText().toString(),
                    Colonia.getSelectedItem().toString(),
                    Municipio.getSelectedItem().toString(),
                    Estado.getSelectedItem().toString(),
                    CodigoPostal.getText().toString(),
                    Telefono.getText().toString(),
                    Email.getText().toString(),
                    Latitud.getText().toString(),
                    Longitud.getText().toString(),
                    Giro.getSelectedItem().toString());
        }
        return valid;
    }

    @Override
    public void onPause() {
        super.onPause();
        lm.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Longitud.setText("");
        Latitud.setText("");
        Longitud.setText(String.valueOf(location.getLongitude()));
        Latitud.setText(String.valueOf(location.getLatitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
