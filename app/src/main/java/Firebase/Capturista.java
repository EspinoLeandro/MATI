package Firebase;

/**
 * Created by Le_an_000 on 25/12/2017.
 */

public class Capturista {

    String usuario;
    String correo;
    String password;
    String nombre;
    String paterno;
    String materno;
    String telefono;

    public Capturista() {
    }

    public Capturista(String correo, String password, String nombre, String paterno, String materno, String telefono, String usuario) {
        this.correo = correo;
        this.password = password;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.telefono = telefono;
        this.usuario = usuario;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
