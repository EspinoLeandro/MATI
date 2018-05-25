package com.cj.mati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Firebase.Referencias;
import Utilidades.Funciones;

public class InicioSesion extends AppCompatActivity {

    TextInputLayout tilpass, tiluser;
    EditText etpassword, etusuario;
    Button btnMapa, btnLogin;
    String Tipo, Usuario, password;
    boolean valido;

    private List<String> administradores = new ArrayList<>();
    private List<String> capturistas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        loadControls();
        loadEvents();
        getAdministradores();
        getCapturistas();
    }

    private void loadEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMapa();
            }
        });
    }

    private void loadControls() {
        etpassword = (EditText) findViewById(R.id.inicio_sesion_edt_password);
        etusuario = (EditText) findViewById(R.id.inicio_sesion_edt_usuario);
        btnMapa = (Button) findViewById(R.id.inicio_sesion_btn_vermapa);
        btnLogin = (Button) findViewById(R.id.inicio_sesion_btn_iniciar_sesion);
        tiluser = (TextInputLayout) findViewById(R.id.text_usuario);
        tilpass = (TextInputLayout) findViewById(R.id.text_password);
    }

    void abrirMapa() {
        Intent intent = new Intent(this, Mapa.class);
        startActivity(intent);
    }

    void iniciarSesion() {
        validar();
    }

    private void validar() {
        valido = false;
        Funciones obj = new Funciones();
        //Validar usuario
        tiluser.setError("");
        tilpass.setError("");
        if (!etusuario.getText().toString().isEmpty()) {
            if (!etpassword.getText().toString().isEmpty()) {
                Usuario = etusuario.getText().toString();
                password = etpassword.getText().toString();
                if (administradores.contains(Usuario)) {
                    Tipo = "ADMINISTRADOR";
                    getPassword(Referencias.ADMINISTRADORES_REFERENCE);
                } else if (capturistas.contains(Usuario)) {
                    Tipo = "CAPTURISTA";
                    getPassword(Referencias.CAPTURISTAS_REFERENCE);
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } else {
                tilpass.setError("Porfavor ingrese su contraseña");
            }
        }else {
            tiluser.setError("Porfavor ingrese su nombre de usuario");
        }
    }

    private void getPassword(String ref) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        }catch (Exception e){}

        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        myRef.child(ref).child(Usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pass = dataSnapshot.child("password").getValue().toString();
                if(password.equals(pass)){

                    SharedPreferences sharedPreferences = getSharedPreferences("Credenciales", MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("sesion", "on");
                    editor.putString("tipo", Tipo);
                    editor.putString("user", Usuario);

                    editor.commit();

                    Intent intent;
                    if (Tipo.equals("ADMINISTRADOR")) {
                        intent = new Intent(getApplicationContext(), Administrador.class);
                    } else {
                        intent = new Intent(getApplicationContext(), capturista.class);
                    }
                    intent.putExtra("user", Usuario);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getCapturistas() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        }catch (Exception e){

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        myRef.child(Referencias.CAPTURISTAS_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                capturistas.clear();
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String cap = snapshot.getKey().toString();
                    capturistas.add(cap);
                    Log.i("CAP",cap);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAdministradores() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        }catch (Exception e){

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        myRef.child(Referencias.ADMINISTRADORES_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                administradores.clear();
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String admin = snapshot.getKey().toString();
                    administradores.add(admin);
                    Log.i("ADMIN",admin);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
