package aga.integrador_juanvaldez;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;

import comun.Registro;

/**
 * Created by Ricardo Andres on 9/03/2016.
 */
public class Comunicacion extends Observable implements Runnable {

    private static final String TAG = "Comunicacion";

    private static  Comunicacion com;

    private DatagramSocket socket=null;
    private InetAddress ip=null;
    private  int port=5000;
    private boolean corriendo;
    private boolean conectando;
    private boolean reset;

    private boolean notificoError;

    String text="";

    private Comunicacion(){

        socket = null;
        corriendo = true;
        conectando = true;
        reset=false;


    }
    private boolean conectar(){
        if(socket==null&&ip==null) {
            try {
                socket = new DatagramSocket(port);
                ip=InetAddress.getByName("192.168.0.29");//Direccion del up del smartphone o localhost: 10.0.2.2
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static Comunicacion getInstance(){
        if(com==null){
            com=new Comunicacion();
            Thread t=new Thread(com);
            t.start();
        }
        return com;
    }
    public void run() {
        Log.d(TAG, "[ HILO DE COMUNICACIÓN INICIADO ]");
        while (corriendo) {
            try {
                if (conectando) {
                    if (reset) {
                        if (socket != null) {
                            try {
                                socket.close();
                            } finally {
                                socket = null;
                            }
                        }
                        reset = false;
                    }
                    conectando = !conectar();
                } else {
                    if (socket != null) {
                        recibir();
                    }
                }
                Thread.sleep(500);
            } catch (IOException e) {
                Log.d(TAG, "[ SE PERDIÓ LA CONEXIÓN CON EL SERVIDOR ]");
                reintentar();
            } catch (InterruptedException e) {

                Log.d(TAG, "[ INTERRUPCIÓN ]");
            }
        }

        try {
            socket.close();
        }  finally {
            socket = null;
        }
    }

    public void reintentar() {
        conectando = true;
        reset = true;
        notificoError = false;
    }
    public void enviar(String nombre, String pass,String estado){

        if(socket!=null){
            Registro r= new Registro(nombre,pass,estado);
            byte[]datos=seralizar(r);
            DatagramPacket dp=new DatagramPacket(datos,datos.length,ip,port);

            try {
                socket.send(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void recibir() throws IOException {



        byte[]datos=new byte[1000];
        DatagramPacket dp=new DatagramPacket(datos,datos.length);
        socket.receive(dp);
        Object c=deserializar(datos);

        if(c instanceof Registro){
            String estado=((Registro)c).getEstado();
            Log.d(TAG, estado);
            manejarLogin(estado);

        }

    }

    public void manejarLogin(String recibido){
        if(recibido.contains("log_resp")){
            String []separar=recibido.split(":");
            int resultado=Integer.parseInt(separar[1]);

            setChanged();
            switch (resultado){
                case 0:

                    break;
                case 1:
                    notifyObservers("login_ok");
                    break;
            }
            clearChanged();
        }

    }




    public  byte[] seralizar(Object c){

        ByteArrayOutputStream bs=new ByteArrayOutputStream();
        ObjectOutputStream os;
        try {
            os=new ObjectOutputStream(bs);
            os.writeObject(c);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  bs.toByteArray();
    }
    public Object deserializar(byte[] datos){

        Object c=null;
        ByteArrayInputStream bs= new ByteArrayInputStream(datos);
        ObjectInputStream oi;
        try {
            oi=new ObjectInputStream(bs);
            c=(Object)oi.readObject();
            oi.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return c;
    }

}
