package com.ort.casievaluacion;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private Button ingresar;
    private Button registrar;
    private EditText editMail;
    private EditText contrasena;
    private String email;
    private String pass;
    private CheckBox checkBox;
    private Personas persona;
    private FirebaseDatabase database;
    private DatabaseReference personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        ingresar = findViewById(R.id.btn1);
        editMail = findViewById(R.id.editMail);
        contrasena = findViewById(R.id.editPass);
        checkBox = findViewById(R.id.checkBox1);

        database = FirebaseDatabase.getInstance();
        personas = database.getReference("personas");
        //persona = new Personas("Leonel","Braginski","leobraginski@gmail.com","oliver");

        cargarpreferencias();

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signIn(editMail.getText().toString(), contrasena.getText().toString());
                signInFirebase(editMail.getText().toString(), contrasena.getText().toString());
            }
        });


    }

    private void signInFirebase(String mail, final String password)
    {

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Se debe ingresar un mail", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Se debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        final String mailsincom = mail.replace(".","");

        personas.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(mailsincom).exists())
                {
                    if (!mailsincom.isEmpty())
                    {
                        Personas login1 = dataSnapshot.child(mailsincom).getValue(Personas.class);
                        if (login1.getPass().equals(password))
                        {

                            Intent intent =
                                    new Intent(login.this,MainActivity.class);
                            Toast.makeText(login.this,"Logueado correctamente",Toast.LENGTH_LONG).show();
                            if (checkBox.isChecked())
                            {
                                SharedPreferences sharedPreferences = getSharedPreferences
                                        ("credenciales", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user", login1.getEmail());
                                editor.putString("pass", login1.getPass().toString());
                                editor.commit();
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("mailsincom",mailsincom);
                            bundle.putString("nombreuser",login1.getNombre());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(login.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }
                }else
                {
                    Toast.makeText(login.this, "Usuario no registrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(login.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cargarpreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);

        editMail.setText(sharedPreferences.getString("user", ""));
        contrasena.setText(sharedPreferences.getString("pass", ""));

        if (!editMail.equals("") && !contrasena.equals("")) {
            signInFirebase(editMail.getText().toString(), contrasena.getText().toString());
        }
    }

    private void signIn(final String email, final String password)
    {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un mail", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Se debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        if(persona.getEmail().equals(email) && persona.getPass().equals(password))
        {
            Intent intent =
                    new Intent(login.this,MainActivity.class);
            Toast.makeText(login.this,"Logueado correctamente",Toast.LENGTH_LONG).show();
            if (checkBox.isChecked())
            {
                SharedPreferences sharedPreferences = getSharedPreferences
                        ("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", persona.getEmail());
                editor.putString("pass", persona.getPass().toString());
                editor.commit();
            }
            startActivity(intent);
        }else
        {
            Toast.makeText(login.this,"Error al loguearse",Toast.LENGTH_LONG).show();
        }
    }

}
