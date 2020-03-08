package com.example.android.aleprofe2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class inicio extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private Button dato;
    private String nombre;
    private String contraseña;
    private Usuarios user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        name = findViewById(R.id.editName);
        dato = findViewById(R.id.btn1);
        pass = findViewById(R.id.editPass);

        user = new Usuarios("cacona","comadrejaovera");


        dato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = name.getText().toString();
                contraseña = pass.getText().toString();

                if(user.getUsername().equals(nombre) && user.getPassword().equals(contraseña)) {


                        Toast.makeText(inicio.this, "Entraste capo", Toast.LENGTH_LONG).show();

                        Intent intent2 = new Intent(getApplicationContext(), inicio2.class);

                        intent2.putExtra("TEXTOINGRESADO", nombre);

                        startActivity(intent2);


                    }else
                    {
                        Toast.makeText(inicio.this, "burooo, douuu", Toast.LENGTH_LONG).show();
                    }
                }


        });
    }
}
