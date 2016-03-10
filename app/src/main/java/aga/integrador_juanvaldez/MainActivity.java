package aga.integrador_juanvaldez;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;



    public class MainActivity extends Activity implements Observer {

        private static final String TAG = "Comunicacion";

        Button registro;

        public TextView prueba;

        EditText nombre;
        EditText pass;


        String r="";
        String g="";
        String j="";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            prueba=(TextView)findViewById(R.id.juan);
            nombre=(EditText)findViewById(R.id.user);
            pass=(EditText)findViewById(R.id.pass);


            registro=(Button)findViewById(R.id.registro);
            Button inicio=(Button)findViewById(R.id.ingresar);

            Comunicacion.getInstance().addObserver(this);





            inicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enviar();
                }
            });

            registro.setOnClickListener(new View.OnClickListener() {
                // Se pasa a la pantalla de Registro

                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this,Registrarse.class);
                    startActivity(intent);
                }
            });

            Intent intent = getIntent();
            String message = intent.getStringExtra(Registrarse.EXTRA_MESSAGE);
            if(message!=null) {
                String[] separar = message.split(":");
                nombre.setText(separar[0]);
                pass.setText(separar[1]);
            }


            Comunicacion.getInstance().addObserver(this);
        }

        public void enviar(){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Comunicacion.getInstance().enviar(nombre.getText().toString(),pass.getText().toString(),"log");
                    Log.d(TAG, "[ Mensaje enviado]");
                }
            }).start();

        }


        public class tarea extends AsyncTask<String,Integer,String> {

            @Override
            protected String doInBackground(String... params) {

                DatagramSocket ds=null;
                InetAddress ip=null;

                if(ds==null&&ip==null){
                    try {
                        ds=new DatagramSocket();
                        ip=InetAddress.getByName("192.168.0.29");
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                byte datos[]=params[0].getBytes();

                DatagramPacket dp=new DatagramPacket(datos,datos.length,ip,5000);
                try {
                    ds.send(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }
        }




        /**
         * This method is called if the specified {@code Observable} object's
         * {@code notifyObservers} method is called (because the {@code Observable}
         * object has been updated.
         *
         * @param observable the {@link Observable} object.
         * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
         */
        @Override
        public void update(Observable observable, Object data) {
            String mensaje=(String)data;

            switch (mensaje){
                case "login_ok":
                    Intent i= new Intent(MainActivity.this,Inicio.class);
                    String user=nombre.getText().toString();
                    i.putExtra("usuario_log",user);
                    startActivity(i);
                    break;
            }

        }

    }
