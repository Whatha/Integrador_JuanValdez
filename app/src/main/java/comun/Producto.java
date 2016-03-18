package comun;

import java.io.Serializable;

public class Producto implements Serializable{

    String tipo;
    String [] producto;
    int [] cantidad;

    public Producto(String tipo,String [] producto,int [] cantidad){
        this.tipo=tipo;
        this.producto=producto;
        this.cantidad=cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public String [] getProducto() {
        return producto;
    }

    public int [] getCantidad() {
        return cantidad;
    }

}