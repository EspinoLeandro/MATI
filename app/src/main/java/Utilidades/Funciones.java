package Utilidades;

import Firebase.Capturista;


/**
 * Created by Le_an_000 on 21/01/2018.
 */

public class Funciones {

    public String generarPassword(Capturista capturista){
        int sizeName = capturista.getNombre().length();
        char par = (sizeName % 2 == 0)? 'p':'c';
        char last = capturista.getNombre().charAt(sizeName-1);
        char vocal = (last == 'a' | last == 'e' | last == 'i' | last =='o' | last == 'u') ? '&':'%';
        String iniciales = capturista.getNombre().charAt(0) +
                capturista.getPaterno().charAt(0) +
                capturista.getMaterno().charAt(0) + "";

        String pass = ""+ sizeName + par + last + vocal + iniciales;

        return pass;
    }

    public String generarUser(Capturista capturista){
        String nombre = capturista.getNombre();
        String paterno = capturista.getPaterno();

        if(nombre.contains("José ") | nombre.contains("María ")){
            nombre = nombre.substring(nombre.indexOf(" "));
        }

        String user = nombre.substring(0,3) + paterno.substring(0,3) + (int) Math.random()*99;

        return user;
    }

}
