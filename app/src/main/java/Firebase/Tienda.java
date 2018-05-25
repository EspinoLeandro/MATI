package Firebase;

/**
 * Created by Le_an_000 on 15/01/2018.
 */

public class Tienda {

    private String NombreEncargado;
    private String Mayor16;
    private String NombreNegocio;
    private String Calle;
    private String Colonia;
    private String Municipio;
    private String Estado;
    private String CodigoPostal;
    private String Telefono;
    private String Correo;
    private String Latitud;
    private String Longitud;
    private String Giro;

    public Tienda() {
    }

    public Tienda(String nombreEncargado, String mayor16, String nombreNegocio, String calle, String colonia, String municipio, String estado, String codigoPostal, String telefono, String correo, String latitud, String longitud,  String giro) {
        NombreEncargado = nombreEncargado;
        Mayor16 = mayor16;
        NombreNegocio = nombreNegocio;
        Calle = calle;
        Colonia = colonia;
        Municipio = municipio;
        Estado = estado;
        CodigoPostal = codigoPostal;
        Telefono = telefono;
        Correo = correo;
        Latitud = latitud;
        Longitud = longitud;
        Giro = giro;
    }

    public String getGiro() {
        return Giro;
    }

    public void setGiro(String giro) {
        Giro = giro;
    }

    public String getNombreEncargado() {
        return NombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        NombreEncargado = nombreEncargado;
    }

    public String getMayor16() {
        return Mayor16;
    }

    public void setMayor16(String mayor16) {
        Mayor16 = mayor16;
    }

    public String getNombreNegocio() {
        return NombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        NombreNegocio = nombreNegocio;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        CodigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }
}