package layout;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.mati.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Firebase.Capturista;
import Firebase.Referencias;
import Utilidades.Funciones;

public class Administrador__Capturista__Agregar extends Fragment {

    View vista;
    EditText nombre, paterno, materno, telefono, correo;
    TextInputLayout TInombre, TIpaterno, TImaterno, TItelefono, TIcorreo;
    Button generar, cancelar, guardar;
    TextView password, usuario;
    Capturista capturista;
    String userCap, PassCap;

    public Administrador__Capturista__Agregar() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_administrador__capturista__agregar, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        }catch (Exception e){

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        loadControls();
        loadEvents();
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    myRef.child(Referencias.CAPTURISTAS_REFERENCE).child(userCap).setValue(capturista);
                    Toast.makeText(getContext(), "Capturista Agregado", Toast.LENGTH_SHORT).show();
                    limpiar();
                }
            }
        });

        return vista;
    }

    private void loadEvents() {
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    Funciones obj = new Funciones();
                    userCap = obj.generarUser(capturista);
                    usuario.setText(userCap);
                    PassCap = obj.generarPassword(capturista);
                    password.setText(PassCap);
                    guardar.setEnabled(true);
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });
    }

    private void loadControls() {
        nombre = (EditText) vista.findViewById(R.id.administrador_capturista_agregar_nombre_edt);
        paterno = (EditText) vista.findViewById(R.id.administrador_capturista_agregar_AParterno_edt);
        materno = (EditText) vista.findViewById(R.id.administrador_capturista_agregar_AMarterno_edt);
        telefono = (EditText) vista.findViewById(R.id.administrador_capturista_agregar_Telefono_edt);
        correo = (EditText) vista.findViewById(R.id.administrador_capturista_agregar_email_edt);

        generar = (Button) vista.findViewById(R.id.administrador_capturista_agreager_generar_btn);
        cancelar = (Button) vista.findViewById(R.id.administrador_capturista_agregar_cancelar_btn);
        guardar = (Button) vista.findViewById(R.id.administrador_capturista_agregar_guardar_btn);
        guardar.setEnabled(false);

        password = (TextView) vista.findViewById(R.id.administrador_capturista_agregar_password_txt);
        usuario = (TextView) vista.findViewById(R.id.administrador_capturista_agregar_usuario_txt);

        TInombre = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_agregar_nombre);
        TIpaterno = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_agregar_APaterno);
        TImaterno = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_agregar_AMaterno);
        TItelefono = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_agregar_Telefono);
        TIcorreo = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_agregar_email);
    }

    private boolean validar() {
        /*validar correo
        * nombre, paterno, materno
        * telefono
        * */
        TInombre.setError("");
        TIpaterno.setError("");
        TImaterno.setError("");
        TItelefono.setError("");
        TIcorreo.setError("");

        Boolean valid = true;
        if(nombre.getText().toString().isEmpty()){
            TInombre.setError("Ingrese Nombre");
            valid= false;
        }
        if(paterno.getText().toString().isEmpty()){
            TIpaterno.setError("Ingrese Apellido Paterno");
            valid= false;
        }
        if(materno.getText().toString().isEmpty()){
            TImaterno.setError("Ingrese Apellido Materno");
            valid= false;
        }
        if(correo.getText().toString().isEmpty()){
            TIcorreo.setError("Ingrese Correo");
            valid= false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches()){
            TIcorreo.setError("Correo invalido");
            valid= false;
        }
        if (telefono.getText().toString().isEmpty()){
            TItelefono.setError("Ingrese Numero de Telefono");
            valid= false;
        }else if(!Patterns.PHONE.matcher(telefono.getText().toString()).matches()){
            TItelefono.setError("Telefono invalido");
            valid= false;
        }

        if (valid){
            capturista = new Capturista(
                    correo.getText().toString(),
                    password.getText().toString(),
                    nombre.getText().toString(),
                    paterno.getText().toString(),
                    materno.getText().toString(),
                    telefono.getText().toString(),
                    usuario.getText().toString());
        }
        return valid;
    }

    private void limpiar() {
        nombre.setText("");
        paterno.setText("");
        materno.setText("");
        telefono.setText("");
        correo.setText("");
        password.setText("");
        usuario.setText("");
        guardar.setEnabled(false);
    }

}
