package com.ort.casievaluacion;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ort.casievaluacion.Utilidades.Utilidades;

public class RegistrarPersona extends AppCompatActivity {
    private Button Registrar;
    private EditText editName;
    private EditText editSurname;
    private EditText editMail;
    private EditText editPass;
    private Toolbar toolbar;
    private FirebaseDatabase database;
    private DatabaseReference personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_persona);

        Registrar = findViewById(R.id.btnRegistrar);
        editName = findViewById(R.id.editNombre1);
        editSurname = findViewById(R.id.editApellido1);
        editMail = findViewById(R.id.editMail1);
        editPass = findViewById(R.id.editPass1);
        toolbar = findViewById(R.id.toolbar);

        database = FirebaseDatabase.getInstance();
        personas = database.getReference("personas");

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrar a un nuevo usuario");

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registrarUsuario();
                //registrarUsuarioSQL();
                registrarUsuarioFirebase(editName.getText().toString(),editSurname.getText().toString(),
                        editMail.getText().toString(),editPass.getText().toString());
            }
        });
    }

    private void registrarUsuarioFirebase(final String nombre, final String apellido, final String mail, final String password) {
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Indique su nombre", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(apellido)) {
            Toast.makeText(this, "Indique su apellido", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Se debe ingresar una contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Indique su E-Mail", Toast.LENGTH_LONG).show();
            return;
        }
        final String mailsincom = mail.replace(".","");
        personas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Personas usuario = new Personas(nombre, apellido, mail, password);
                personas.child(String.valueOf(mailsincom)).setValue(usuario);
                Intent intent =
                        new Intent(RegistrarPersona.this,MainActivity.class);
                Bundle bundle = RegistrarPersona.this.getIntent().getExtras();
                bundle.putString("mailsincom",mailsincom);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void registrarUsuarioSQL()
    {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this,"bd_personas",null,1);
        SQLiteDatabase database = conexionSQLiteHelper.getWritableDatabase();

        String insert = "INSERT INTO " + Utilidades.TABLA+ " ("+Utilidades.CAMPO_NOMBRE+","+Utilidades.CAMPO_APELLIDO+","+Utilidades.CAMPO_MAIL+","+Utilidades.CAMPO_PASSWORD+ ")" +
        " VALUES ('"+editName.getText().toString()+"','"+editSurname.getText().toString()+"','"
                +editMail.getText().toString() + "','" + editPass.getText().toString()+"')";

        database.execSQL(insert);

        database.close();
    }

    private void registrarUsuario()
    {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this,"bd_personas",null,1);
        SQLiteDatabase database = conexionSQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE,editName.getText().toString());
        values.put(Utilidades.CAMPO_APELLIDO,editSurname.getText().toString());
        values.put(Utilidades.CAMPO_MAIL,editMail.getText().toString());
        values.put(Utilidades.CAMPO_PASSWORD,editPass.getText().toString());

        long nombreFinal = database.insert("usuarios","nombre",values);

        Toast.makeText(getApplicationContext(),""+nombreFinal,Toast.LENGTH_LONG).show();

        database.close();
    }

}
