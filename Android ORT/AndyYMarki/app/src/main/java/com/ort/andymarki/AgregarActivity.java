package com.ort.andymarki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.andymarki.Database.ProductosSqliteHelper;

import static android.widget.Toast.LENGTH_SHORT;

public class AgregarActivity extends AppCompatActivity {

    private Button buttonAdd;
    private EditText editTextNombrej;
    private EditText editTextoCantidadj;
    private EditText editTextPrecioj;


    private SQLiteDatabase db;
    private ProductosSqliteHelper usdbh;
    private Cursor c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        buttonAdd =  findViewById(R.id.buttonAgregar);
        editTextNombrej =  findViewById(R.id.editTextNombre);
        editTextoCantidadj =  findViewById(R.id.editTextCantidad);
        editTextPrecioj =  findViewById(R.id.editTextPrecio);


        usdbh = new ProductosSqliteHelper(this, "MyDatabase.db", null, 1);
        db = usdbh.getWritableDatabase();

        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (editTextNombrej.getText().toString().isEmpty() || editTextoCantidadj.getText().toString().isEmpty() || editTextPrecioj.getText().toString().isEmpty()) {
                    Toast.makeText(getApplication(),"no puede quedar ningun campo vacio", LENGTH_SHORT).show();
                }
                else{
                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("NOMBRE", editTextNombrej.getText().toString());
                    nuevoRegistro.put("PRECIO", editTextPrecioj.getText().toString());
                    nuevoRegistro.put("CANTIDAD", editTextoCantidadj.getText().toString());
                    db.insert("PRODUCTOS", null, nuevoRegistro);
                    Intent IntentRetornoAdd = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(IntentRetornoAdd);
                }
            }
        });
    }
}
