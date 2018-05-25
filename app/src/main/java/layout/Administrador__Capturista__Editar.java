package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class Administrador__Capturista__Editar extends Fragment {

    private Capturista capturista;
    private EditText nombre, paterno, materno, telefono, correo;
    private Button cancelar, editar;
    private TextView password, usuario;
    private TextInputLayout textNombre, textPaterno, textMaterno, textTelefono, textCorreo;

    public Administrador__Capturista__Editar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_administrador__capturista__editar, container, false);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        try {
            database.setPersistenceEnabled(true);
        } catch (Exception e) {

        }
        final DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

        nombre = (EditText) vista.findViewById(R.id.administrador_capturista_editar_nombre_edt);
        paterno = (EditText) vista.findViewById(R.id.administrador_capturista_editar_AParterno_edt);
        materno = (EditText) vista.findViewById(R.id.administrador_capturista_editar_AMarterno_edt);
        telefono = (EditText) vista.findViewById(R.id.administrador_capturista_editar_Telefono_edt);
        correo = (EditText) vista.findViewById(R.id.administrador_capturista_editar_email_edt);
        usuario = (TextView) vista.findViewById(R.id.administrador_capturista_editar_usuario_txt);
        password = (TextView) vista.findViewById(R.id.administrador_capturista_editar_password_txt);

        textNombre = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_editar_nombre);
        textPaterno = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_editar_APaterno);
        textMaterno = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_editar_AMaterno);
        textTelefono = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_editar_Telefono);
        textCorreo = (TextInputLayout) vista.findViewById(R.id.administrador_capturista_editar_email);

        cancelar = (Button) vista.findViewById(R.id.administrador_capturista_editar_cancelar_btn);
        editar = (Button) vista.findViewById(R.id.administrador_capturista_editar_editar_btn);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
                getActivity().onBackPressed();
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validForm()) {
                    clearError(textNombre);
                    clearError(textPaterno);
                    clearError(textMaterno);
                    clearError(textCorreo);
                    clearError(textTelefono);

                    String email = correo.getText().toString();
                    email = email.replace('.', '*');

                    Capturista capturista = new Capturista(
                            email,
                            password.getText().toString(),
                            nombre.getText().toString(),
                            paterno.getText().toString(),
                            materno.getText().toString(),
                            telefono.getText().toString(),
                            usuario.getText().toString());

                    myRef.child(Referencias.CAPTURISTAS_REFERENCE).child(usuario.getText().toString()).setValue(capturista);
                    Toast.makeText(getContext(), "capturista Editado :)", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }
        });

        return vista;
    }

    private void limpiar() {
        nombre.setText("");
        paterno.setText("");
        materno.setText("");
        telefono.setText("");
        correo.setText("");
        password.setText("");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        usuario.setText(capturista.getUsuario());
        nombre.setText(capturista.getNombre());
        paterno.setText(capturista.getPaterno());
        materno.setText(capturista.getMaterno());
        telefono.setText(capturista.getTelefono());
        correo.setText(capturista.getCorreo());
        password.setText(capturista.getPassword());

    }

    private boolean validForm() {
        boolean valid = true;

        if (nombre.getText().toString().isEmpty()) {
            valid = false;
            textNombre.setError(getString(R.string.add_capturista_text_error_empty_nombre));
        }
        if (paterno.getText().toString().isEmpty()) {
            valid = false;
            textPaterno.setError(getString(R.string.add_capturista_text_error_empty_paterno));
        }
        if (materno.getText().toString().isEmpty()) {
            valid = false;
            textMaterno.setError(getString(R.string.add_capturista_text_error_empty_materno));
        }
        if (telefono.getText().toString().isEmpty()) {
            valid = false;
            textTelefono.setError(getString(R.string.add_capturista_text_error_empty_telefono));
        } else if (!Patterns.PHONE.matcher(telefono.getText().toString()).matches()) {
            valid = false;
            textTelefono.setError(getString(R.string.add_capturista_text_error_incorrect_telefono));
        }
        if (correo.getText().toString().isEmpty()) {
            valid = false;
            textCorreo.setError(getString(R.string.add_capturista_text_error_empty_correo));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches()) {
            valid = false;
            textCorreo.setError(getString(R.string.add_capturista_text_error_incorrect_correo));
        }

        return valid;
    }

    private void clearError(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
    }

    private void clearText(EditText edt) {
        edt.setText("");
    }

    public void setCapturista(Capturista capturista) {
        this.capturista = capturista;
    }
}
