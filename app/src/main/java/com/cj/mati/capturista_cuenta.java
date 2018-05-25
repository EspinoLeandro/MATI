package com.cj.mati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Firebase.Referencias;

public class capturista_cuenta extends AppCompatActivity {

    String user;
    TextView nombre, apellidos, correo, telefono, usuario, pass, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturista_cuenta);
        setTitle("Cuenta Capturista");
        loadControls();
        user = getIntent().getStringExtra("user");
        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        getUsuer();
    }
    private void loadControls() {
        nombre = (TextView) findViewById(R.id.cap_cuenta_nombre);
        apellidos = (TextView) findViewById(R.id.cap_cuenta_apellidos);
        correo = (TextView) findViewById(R.id.cap_cuenta_correo);
        pass = (TextView) findViewById(R.id.cap_cuenta_password);
        telefono = (TextView) findViewById(R.id.cap_cuenta_telefono);
        usuario = (TextView) findViewById(R.id.cap_cuenta_usuario);
    }

    private boolean getUsuer() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        } catch (Exception e) {

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        myRef.child(Referencias.CAPTURISTAS_REFERENCE).child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("CUENTA", dataSnapshot.toString());
                Firebase.Capturista obj = dataSnapshot.getValue(Firebase.Capturista.class);
                showInfo(obj);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return  true;
    }

    private void showInfo(Firebase.Capturista capturista) {
        nombre.setText("Nombre: " + capturista.getNombre());
        apellidos.setText("Apellidos: "+capturista.getPaterno() + " " + capturista.getMaterno());

        correo.setText("E-mail: " + capturista.getCorreo());
        telefono.setText("Telefono: " + capturista.getTelefono());

        usuario.setText("Usuario: " + user);
        pass.setText("Contraseña: " + capturista.getPassword());

    }
}
