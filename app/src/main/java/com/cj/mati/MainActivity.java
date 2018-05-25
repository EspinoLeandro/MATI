package com.cj.mati;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cargarPreferencias();
                finish();
            }
        }, 2000);
    }



    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("Credenciales", MODE_PRIVATE);

        String sesion = preferences.getString("sesion", "null").toString();
        String Usuario = preferences.getString("user", "null").toString();
        String tipo = preferences.getString("tipo", "null").toString();
        Intent intent;

        if(sesion.equals("on")){//SI EXISTE SESION
            if(tipo.equals("ADMINISTRADOR")){//SI ES ADMINISTRADOR
                intent = new Intent(this, Administrador.class);
                intent.putExtra("user", Usuario);
                startActivity(intent);
            }else if(tipo.equals("CAPTURISTA")){//SI ES CAPTURISTA
                intent = new Intent(this, capturista.class);
                startActivity(intent);
            }
        }else if(sesion.equals("off")) {//SI NO EXISTE SESION
            intent = new Intent(this, InicioSesion.class);
            startActivity(intent);
        }else {
            intent = new Intent(this, InicioSesion.class);
            startActivity(intent);
        }
    }
}
