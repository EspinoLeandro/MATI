package Firebase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Le_an_000 on 28/01/2018.
 */

public class Administrador {

    String correo;
    String password;
    String nombre;
    String paterno;
    String materno;
    String telefono;
    List<Capturista> capturistas = new ArrayList<>();

    public Administrador() {
    }

    public Administrador(String correo, String password, String nombre, String paterno, String materno, String telefono, List<Capturista> capturistas) {
        this.correo = correo;
        this.password = password;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.capturistas = capturistas;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Capturista> getCapturistas() {
        return capturistas;
    }

    public void setCapturistas(List<Capturista> capturistas) {
        this.capturistas = capturistas;
    }
}
