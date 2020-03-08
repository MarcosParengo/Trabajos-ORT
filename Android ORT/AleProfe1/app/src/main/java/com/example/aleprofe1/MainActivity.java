package com.example.aleprofe1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.awt.font.TextAttribute;

public class MainActivity extends AppCompatActivity {

    private TextView Nombre;
    private TextView Apellido;
    private TextView Dni;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Nombre = findViewById(R.id.textNombre);
        Apellido = findViewById(R.id.textApellido);
        Dni = findViewById(R.id.textDni);

        usuario = new Usuario("Adolfo", "Teper", "69420420");

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        Nombre.setText(usuario.getNombre());
        Apellido.setText(usuario.getApellido());
        Dni.setText(usuario.getDni());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre("Leonel");
                usuario.setApellido("Braginski");
                usuario.setDni("44159126");

                Nombre.setText(usuario.getNombre());
                Apellido.setText(usuario.getApellido());
                Dni.setText(usuario.getDni());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre("Andres");
                usuario.setApellido("Guinsburg");
                usuario.setDni("43448452");

                Nombre.setText(usuario.getNombre());
                Apellido.setText(usuario.getApellido());
                Dni.setText(usuario.getDni());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre("Marcos");
                usuario.setApellido("Sancho");
                usuario.setDni("44159009");

                Nombre.setText(usuario.getNombre());
                Apellido.setText(usuario.getApellido());
                Dni.setText(usuario.getDni());
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre("Adriel");
                usuario.setApellido("Hitler");
                usuario.setDni("666666666");

                Nombre.setText(usuario.getNombre());
                Apellido.setText(usuario.getApellido());
                Dni.setText(usuario.getDni());
            }
        });
    }
}
