package com.leobrasileo.tp3ertrimestre;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.leobrasileo.tp3ertrimestre.Database.ProductosSQLiteHelper;

public class Estadisticas extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textValtotProd;
    private TextView textStocktotProd;
    private TextView alarma;
    private SQLiteDatabase db;
    private ProductosSQLiteHelper pdbh;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Estadisticas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textValtotProd = findViewById(R.id.textView4);
        textStocktotProd = findViewById(R.id.textView5);
        alarma = findViewById(R.id.textView6);

        pdbh = new ProductosSQLiteHelper(this, "MyDatabase.db", null, 1);
        db = pdbh.getWritableDatabase();
        String[] campos = new String[] {"nombre", "cantidad", "precio"};
        cursor = db.query("Productos",campos,null,null,null,null,null);

        if (cursor.moveToFirst())
        {
            int valmax = cursor.getCount();
            int preciototal = 0;
            int preciototalsuma = 0;
            do {
                String cantidad = cursor.getString(1);
                String precio1 = cursor.getString(2);
                precio1 = precio1.replace("$","");
                preciototal = Integer.valueOf(precio1) * Integer.valueOf(cantidad);
                preciototalsuma = preciototalsuma + preciototal;
            }while (cursor.moveToNext());
            Integer precio34 = preciototalsuma;
            String precioString = precio34.toString();
            textValtotProd.setText("Valor total de todo: $" + precioString);
        }

        if (cursor.moveToFirst())
        {
            int valmax = cursor.getCount();
            int stocktotal = 0;
            do {
                String cantidad = cursor.getString(1);
                int cantint = Integer.valueOf(cantidad);
                stocktotal = stocktotal + cantint;
            }while (cursor.moveToNext());
            Integer sochhi = stocktotal;
            String stringstock = sochhi.toString();
            textStocktotProd.setText("Stock total: " + stringstock);
        }

        if (cursor.moveToFirst())
        {
            int valmax = cursor.getCount();
            int stocktotal = 0;
            String faltantes = "";
            do {
                String nombre = cursor.getString(0);
                String cantidad = cursor.getString(1);
                int cantint = Integer.valueOf(cantidad);
                stocktotal = stocktotal + cantint;
                if (cantint <= 1)
                {
                    faltantes = faltantes + " " + nombre;
                    alarma.setText("Hay escazes de:" + faltantes);
                }
            }while (cursor.moveToNext());
        }
    }
}
