package org.izv.aad.appwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class leerPref extends AppCompatActivity {

    private Button leer1;
    private Button leer2;
    private Button leer3;
    private TextView tvOutput;

    private void addEventsHandler(){
        leer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void init(){
        this.tvOutput = findViewById(R.id.tvOutput);
        this.leer3 = findViewById(R.id.leer3);
        this.leer2 = findViewById(R.id.leer2);
        this.leer1 = findViewById(R.id.leer1);

        addEventsHandler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_pref);
        init();
    }
}
