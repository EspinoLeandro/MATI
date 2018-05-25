package com.cj.mati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import Firebase.Tienda;

public class TiendaInfo extends AppCompatActivity {

    public static Tienda tienda;

    private TextView txvNombreNegocio, txvEncargado, txvCorreo, txvTelefono, txvDireccion;

    public TiendaInfo() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_info);
        cargarControles();
        cargarDatos();
    }

    private void cargarDatos() {
        txvNombreNegocio.setText(tienda.getNombreNegocio());
        txvEncargado.setText(tienda.getNombreEncargado());
        txvCorreo.setText(tienda.getCorreo());
        txvTelefono.setText(tienda.getTelefono());
        txvDireccion.setText("Calle: " + tienda.getCalle() + "\n" +
                            "Colonia: " + tienda.getColonia()+ "\n" +
                            "Municipio: " + tienda.getMunicipio()+ "\n" +
                            "Estado: " + tienda.getEstado());
    }


    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    private void cargarControles() {
        txvNombreNegocio = (TextView) findViewById(R.id.ti_txv_nombreNegocio);
        txvEncargado = (TextView) findViewById(R.id.ti_txv_nombreEncargado);
        txvCorreo = (TextView) findViewById(R.id.ti_txv_correo);
        txvTelefono = (TextView) findViewById(R.id.ti_txv_telefono);
        txvDireccion = (TextView) findViewById(R.id.ti_txv_Direccion);

    }
}
