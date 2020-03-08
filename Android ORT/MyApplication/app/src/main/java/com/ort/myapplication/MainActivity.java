package com.ort.myapplication;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Formattable;

public class MainActivity extends AppCompatActivity
{
    private EditText editNombre;
    private EditText editSurname;
    private EditText editDni;
    private Button sumar;
    private Usuario usuario1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editDni = findViewById(R.id.editDNI);
        editNombre = findViewById(R.id.editName);
        editSurname = findViewById(R.id.editSurname);
        sumar = findViewById(R.id.button);
        final Usuario usuario1 = new Usuario();
        final ArrayList<Usuario> list1 = new ArrayList<Usuario>();
        sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario1.setDni(editDni.getText().toString());
                usuario1.setApellido(editSurname.getText().toString());
                usuario1.setNombre(editNombre.getText().toString());
                list1.add(usuario1);
                for (Usuario Actual : list1)
                {
                    Log.i("Test", usuario1.getNombre());
                    Log.i("Test", usuario1.getApellido());
                    Log.i("Test", usuario1.getDni());
                }
            }
        });

        /*for (Usuario Actual : list1)
        {
            Log.i("Test", usuario.getNombre());
            Log.i("Test", usuario.getApellido());
            Log.i("Test", usuario.getDni());
        }*/
    }
}