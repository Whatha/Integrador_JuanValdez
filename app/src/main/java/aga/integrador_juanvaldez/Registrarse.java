package aga.integrador_juanvaldez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Ricardo Andres on 9/03/2016.
 */
public class Registrarse extends Activity {

    EditText e1;
    EditText e2;
    Button boton;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse);

        e1=(EditText)findViewById(R.id.rNombre);
        e2=(EditText)findViewById(R.id.rPass);

        boton=(Button)findViewById(R.id.aceptar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarRegistro();


                // Para mandar informacion de una actividad a otra

                Intent intent = new Intent(Registrarse.this, MainActivity.class);
                String message = e1.getText().toString();
                String message2=e2.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message+":"+message2);
                startActivity(intent);
            }
        });

    }


    public void enviarRegistro(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Comunicacion.getInstance().enviar(e1.getText().toString(),e2.getText().toString(),"reg");
            }
        }).start();
    }



}
