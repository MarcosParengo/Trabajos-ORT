package com.ort.casievaluacion;

import android.content.Intent;
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

public class EditarPersona extends AppCompatActivity
{
    private Button Registrar;
    private EditText editName;
    private EditText editSurname;
    private EditText editMail;
    private Toolbar toolbar;
    private FirebaseDatabase database;
    private DatabaseReference personas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);

        Registrar = findViewById(R.id.btnEditar);
        editName = findViewById(R.id.editNombre1);
        editSurname = findViewById(R.id.editApellido1);
        editMail = findViewById(R.id.editMail1);
        toolbar = findViewById(R.id.toolbar);

        database = FirebaseDatabase.getInstance();
        personas = database.getReference("personas");

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar Usuario");

        Bundle editar = this.getIntent().getExtras();
        final String maileditar = editar.getString("mail");
        final String nombreeditar = editar.getString("nombre");
        final String apellidoeditar = editar.getString("apellido");
        final String nombreviejo = editar.getString("nombreviejo");

        editName.setText(nombreeditar);
        editMail.setText(maileditar);
        editSurname.setText(apellidoeditar);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarUsuarioFirebase(editName.getText().toString(), editSurname.getText().toString(), editMail.getText().toString(),nombreviejo);
            }
        });
    }

    private void EditarUsuarioFirebase(final String nombre, final String apellido, final String mail,final String nombreviejo) {
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Indique un nuevo nombre", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(apellido)) {
            Toast.makeText(this, "Indique un nuevo apellido", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Indique un nuevo E-Mail", Toast.LENGTH_LONG).show();
            return;
        }
        final String mailsincom = mail.replace(".","");
        personas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Personas usuario = dataSnapshot.child(mailsincom).getValue(Personas.class);
                usuario.setEmail(mail);
                usuario.setNombre(nombre);
                usuario.setApelido(apellido);
                personas.child(mailsincom).setValue(usuario);
                Intent intent =
                        new Intent(EditarPersona.this,MainActivity.class);
                Bundle bundle = EditarPersona.this.getIntent().getExtras();
                bundle.putString("mailsincom",mailsincom);
                bundle.putString("nombreuser",nombreviejo);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
