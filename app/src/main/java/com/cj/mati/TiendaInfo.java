package com.cj.mati;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import Firebase.Tienda;

public class TiendaInfo extends AppCompatActivity {

    public static Tienda tienda;
    private final int PHONE_CALL_CODE = 100;

    WebView web;
    String url;

    private TextView txvEncargado, txvCorreo, txvTelefono, txvDireccion;
    ImageButton btnCall, btnMail, btnMapa;

    public TiendaInfo() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_info);
        setTitle(tienda.getNombreNegocio());
        cargarControles();
        cargarDatos();
        cargarEventos();
    }

    private void cargarEventos() {
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numphone = tienda.getTelefono();
                llamar(numphone);
            }
        });
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = tienda.getCorreo();
                Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+correo));
                startActivity(intentMail);
            }
        });
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap();
            }
        });
    }

    private void showMap() {
        url="https://www.google.com.mx/maps/@"+
                tienda.getLatitud()+","+
                tienda.getLongitud()+",166m/data=!3m1!1e3";
        WebSettings webSettings=web.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request) {
                return false;
            }
        });
        web.loadUrl(url);
    }

    private void llamar(String numphone) {
        if(numphone!=null){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                if(checkPermission(Manifest.permission.CALL_PHONE)) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                }
            }else{
                olderVersions(numphone);
            }
        }
    }

    private boolean checkPermission(String permiso) {
        int result=checkCallingOrSelfPermission(permiso);
        return result== PackageManager.PERMISSION_GRANTED;
    }

    private void olderVersions(String numphone) {
        Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numphone));
        if(checkPermission(Manifest.permission.CALL_PHONE)){
            startActivity(intentLlamada);
        }else{
            Toast.makeText(this, "No hay permisos", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos() {
        txvEncargado.setText(tienda.getNombreEncargado());
        txvCorreo.setText(tienda.getCorreo());
        txvTelefono.setText(tienda.getTelefono());
        txvDireccion.setText(tienda.getCalle() + ", "
                        + tienda.getColonia() + ", "
                        + tienda.getMunicipio() + ", "
                        + tienda.getEstado() + ".");
    }

    private void cargarControles() {
        txvEncargado = (TextView) findViewById(R.id.ti_txv_nombreEncargado);
        txvCorreo = (TextView) findViewById(R.id.ti_txv_correo);
        txvTelefono = (TextView) findViewById(R.id.ti_txv_telefono);
        txvDireccion = (TextView) findViewById(R.id.ti_txv_Direccion);
        btnCall = (ImageButton) findViewById(R.id.btn_call);
        btnMail = (ImageButton) findViewById(R.id.btn_email);
        btnMapa = (ImageButton) findViewById(R.id.btn_show_map);
        web = (WebView) findViewById(R.id.webview);
    }
}
