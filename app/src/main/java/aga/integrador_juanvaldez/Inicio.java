package aga.integrador_juanvaldez;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ricardo Andres on 9/03/2016.
 */
public class Inicio extends Activity {

    TextView usuario;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        usuario=(TextView)findViewById(R.id.iNombre);

        Intent i=getIntent();
        String logueado=i.getStringExtra("usuario_log");
        usuario.setText(logueado);



    }
    public void cafe(View v){
        Intent intent = new Intent(Inicio.this, Cafe.class);
        startActivity(intent);

    }

    public void postres(View v){

        Intent intent = new Intent(Inicio.this, Postres.class);
        startActivity(intent);

    }
}