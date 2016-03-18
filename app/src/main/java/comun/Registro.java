package comun;

import java.io.Serializable;

public class Registro implements Serializable {

    String nombre;
    String pass;
    String estado;

    public Registro(String nombre,String pass,String estado){
        this.nombre=nombre;
        this.pass=pass;
        this.estado=estado;
    }

    public String getNombre(){
        return nombre;
    }
    public String getPass(){
        return pass;
    }
    public String getEstado(){
        return estado;
    }


}