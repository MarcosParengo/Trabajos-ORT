package com.example.android.aleprofe2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class inicio2 extends AppCompatActivity {

    public TextView txtInvitado;
    public String txtRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio2);

        txtInvitado = findViewById(R.id.txt2);

        txtRecibido = getIntent().getStringExtra("TEXTOINGRESADO");
        txtInvitado.setText("Bienvenido " + txtRecibido);

    }
}
