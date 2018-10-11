package org.izv.aad.appwebview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActividadPreferencias extends AppCompatActivity {

    private android.widget.Button btPref;
    private android.widget.Button btShPref;
    private android.widget.Button btDeShPref;
    private Button btLeerPref;
    private Button btLeerShPref;
    private Button btLeerDeShaPref;
    private android.widget.TextView textView2;

    private void addEventsHandler(){
        btDeShPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventoDePref();
            }
        });
        btPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventoPref();
            }
        });
        btShPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Context context = ActividadPreferencias.this;
                SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.archivoSharedPreferences), Context.MODE_PRIVATE);*/
                eventoShPref();
            }
        });
        btLeerPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventoLeer2();
            }
        });
        btLeerDeShaPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventoLeer1();
            }
        });
        btLeerShPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventoLeer3();
            }
        });
    }

    private void eventoDePref(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(getString(R.string.clave3), getString(R.string.valor3));
        editor.apply();
        /*
        El archivo se guarda con el nombre del paquete, el acceso será desde el propio paquete*/
    }

    private void eventoPref(){
        SharedPreferences clavePref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = clavePref.edit();

        editor.putString(getString(R.string.clave2), getString(R.string.valor2));
        editor.apply();
        /*
        El archivo se guarda con le nombre de la clase, todo parece indicar que para acceder a este
        * archivo solo se puede hacer desde la PROPIA CLASE*/
    }

    private void eventoShPref(){
        SharedPreferences pref = getSharedPreferences(getString(R.string.archivoSharedPreferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(getString(R.string.clave1), getString(R.string.valor1));
        editor.apply();
        /*
        archivo con nombre propio
        */
    }

    private void eventoLeer1(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String v = pref.getString(getString(R.string.clave3),getString(R.string.valorPredeterminado));
        textView2.setText(v);
    }

    private void eventoLeer2(){
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        String v = pref.getString(getString(R.string.clave2), getString(R.string.valorPredeterminado));
        textView2.setText(v);
    }

    private void eventoLeer3(){
        SharedPreferences pref = getSharedPreferences(getString(R.string.archivoSharedPreferences), Context.MODE_PRIVATE);
        String v = pref.getString(getString(R.string.clave1), getString(R.string.valorPredeterminado));
        textView2.setText(v);
    }

    private void init(){
        this.btDeShPref = findViewById(R.id.btDeShPref);
        this.btShPref = findViewById(R.id.btShPref);
        this.btPref = findViewById(R.id.btPref);

        addEventsHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_preferencias);
        this.textView2 = findViewById(R.id.textView2);
        this.btLeerDeShaPref = findViewById(R.id.btLeerDeShaPref);
        this.btLeerShPref = findViewById(R.id.btLeerShPref);
        this.btLeerPref = (Button) findViewById(R.id.btLeerPref);
        this.btDeShPref = (Button) findViewById(R.id.btDeShPref);
        this.btShPref = (Button) findViewById(R.id.btShPref);
        this.btPref = (Button) findViewById(R.id.btPref);
        init();
    }
}
