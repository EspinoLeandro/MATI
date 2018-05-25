package com.cj.mati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Firebase.*;

public class Administrador_cuenta extends AppCompatActivity {

    String user;
    TextView nombre, apellidos, correo, telefono, usuario, pass, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_cuenta);
        setTitle("Cuenta Administrador");
        loadControls();
        user = getIntent().getStringExtra("user");
        getUsuer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void loadControls() {
        nombre = (TextView) findViewById(R.id.admin_cuenta_nombre);
        apellidos = (TextView) findViewById(R.id.admin_cuenta_apellidos);
        correo = (TextView) findViewById(R.id.admin_cuenta_correo);
        pass = (TextView) findViewById(R.id.admin_cuenta_password);
        telefono = (TextView) findViewById(R.id.admin_cuenta_telefono);
        usuario = (TextView) findViewById(R.id.admin_cuenta_usuario);
    }

    private void showInfo(Firebase.Administrador administrador) {
        nombre.setText("Nombre: " + administrador.getNombre());
        apellidos.setText("Apellidos: "+administrador.getPaterno() + " " + administrador.getMaterno());

        correo.setText("E-mail: " + administrador.getCorreo());
        telefono.setText("Telefono: " + administrador.getTelefono());

        usuario.setText("Usuario: " + user);
        pass.setText("Contraseña: " + administrador.getPassword());

    }

    private boolean getUsuer() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        } catch (Exception e) {

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        myRef.child(Referencias.ADMINISTRADORES_REFERENCE).child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Firebase.Administrador obj = dataSnapshot.getValue(Firebase.Administrador.class);
                showInfo(obj);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return  true;
    }

}
