package com.leobrasileo.tp3ertrimestre;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leobrasileo.tp3ertrimestre.Database.ProductosSQLiteHelper;

public class AgregarProducto extends AppCompatActivity
{
    private Toolbar toolbar;
    private EditText editNombre;
    private EditText editCantidad;
    private EditText editPrecio;
    private SQLiteDatabase db;
    private ProductosSQLiteHelper pdbh;
    private Cursor cursor;
    private Button button;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        toolbar = findViewById(R.id.toolbar2);
        editNombre = findViewById(R.id.editText);
        editCantidad = findViewById(R.id.editText2);
        editPrecio = findViewById(R.id.editText3);
        button = findViewById(R.id.button);
        pdbh = new ProductosSQLiteHelper(this, "MyDatabase.db", null, 1);
        db = pdbh.getWritableDatabase();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Agregar un producto");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregoProd(editNombre.getText().toString(),editCantidad.getText().toString(),editPrecio.getText().toString());
                Intent intent = new Intent(AgregarProducto.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AgregoProd(String nombre, String cantidad, String precio)
    {
        Producto producto = new Producto(nombre,cantidad,precio);
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre",nombre);
        contentValues.put("cantidad",cantidad);
        contentValues.put("precio",precio);
        db.insert("Productos",null,contentValues);
    }
}
