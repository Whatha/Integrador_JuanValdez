package aga.integrador_juanvaldez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

import comun.Registro;

public class Cafe extends AppCompatActivity implements Observer{

    TextView Latte = (TextView) findViewById(R.id.latte);
    TextView Capuccino = (TextView) findViewById(R.id.capuccino);
    TextView Expresso = (TextView) findViewById(R.id.expresso);
    TextView Leche = (TextView) findViewById(R.id.leche);
    TextView Mocca = (TextView) findViewById(R.id.mocca);

    TextView mc = (TextView) findViewById(R.id.contadorMocca);
    TextView ex = (TextView) findViewById(R.id.contadorExpresso);
    TextView lt = (TextView) findViewById(R.id.contadorLatte);
    TextView lch = (TextView) findViewById(R.id.contadorLeche);
    TextView cpc = (TextView) findViewById(R.id.contadorCapuccino);

    int variableL=0;
    int variableC=0;
    int variableE=0;
    int variableLch=0;
    int variableM=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);
        Comunicacion.getInstance().addObserver(this);

        Latte.setText("Latte");
        Capuccino.setText("Capuccino");
        Expresso.setText("Expresso");
        Leche.setText("Con Leche");
        Mocca.setText("Mocca");


        lt.setText(variableL);
        cpc.setText(variableC);
        ex.setText(variableE);
        lch.setText(variableLch);
        mc.setText(variableM);

    }

        public void comprarCafe(View v){
            final String [] producto= new String[0];
            producto[0]= Latte.getText().toString();
            producto[1]= Capuccino.getText().toString();
            producto[2]= Expresso.getText().toString();
            producto[3]= Leche.getText().toString();
            producto[4]= Mocca.getText().toString();

            final int [] cantidad= new int[5];
            cantidad[0]= Integer.parseInt(lt.getText().toString());
            cantidad[1]= Integer.parseInt(cpc.getText().toString());
            cantidad[2]= Integer.parseInt(ex.getText().toString());
            cantidad[3]= Integer.parseInt(lch.getText().toString());
            cantidad[4]= Integer.parseInt(mc.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Comunicacion.getInstance().enviar("Cafe",producto,cantidad);
            }
        }).start();
    }

    public void latteIzq(View v){
        if(variableL>0){
            variableL-=1;
        }
        lt.setText(String.valueOf(variableL));
    }
    public void latteDer(View v){
        if(variableL<5){
            variableL+=1;
        }
        lt.setText(String.valueOf(variableL));
    }
    public void cappuccinoIzq(View v){
        if(variableL>0){
            variableL-=1;
        }
        lt.setText(String.valueOf(variableL));
    }
    public void capuccinoDer(View v){
        if(variableC<5){
            variableC+=1;
        }
        cpc.setText(String.valueOf(variableC));
    }
    public void expresoIzq(View v){
        if(variableE>0){
            variableE-=1;
        }
        ex.setText(String.valueOf(variableE));
    }
    public void expresoDer(View v){
        if(variableE<5){
            variableE+=1;
        }
        ex.setText(String.valueOf(variableE));
    }
    public void lecheIzq(View v){
        if(variableLch>0){
            variableLch-=1;
        }
        lch.setText(String.valueOf(variableLch));
    }
    public void lecheDer(View v){
        if(variableLch<5){
            variableLch+=1;
        }
        lch.setText(String.valueOf(variableLch));
    }
    public void moccaIzq(View v){
        if(variableM>0){
            variableM-=1;
        }
        mc.setText(String.valueOf(variableM));
    }
    public void moccaDer(View v){
        if(variableM<5){
            variableM+=1;
        }
        mc.setText(String.valueOf(variableM));
    }


    public void update(Observable observable, Object data) {
        TextView a = (TextView) findViewById(R.id.precio);
        String b= ((Registro) data).getEstado();
        a.setText(b);
        Intent intent = new Intent(Cafe.this, Final.class);
        startActivity(intent);
    }
}
