package com.ort.andymarki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ort.andymarki.Database.ProductosSqliteHelper;
import com.ort.andymarki.models.Producto;

import java.util.ArrayList;

public class Estadisticas extends AppCompatActivity {

    private boolean Calculos;
    private int precioInt;
    private int precioTotal;
    private int cantidadTotal;
    private int cantidadInt;
    private Button volver;
    private TextView Texto;
    private TextView AlarmaTexto;
    private ListView AlarmasListView;

    private SQLiteDatabase db;
    private ProductosSqliteHelper usdbh;
    private Cursor c;
    ArrayList<Producto> listAlarma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        volver = findViewById(R.id.Volver);
        Texto= findViewById(R.id.CantidadTotalProductos);
        AlarmasListView= findViewById(R.id.listViewAlarma);
        AlarmaTexto= findViewById(R.id.TxtAlarma);
        AlarmaTexto.setTextColor(Color.RED);

        usdbh = new ProductosSqliteHelper(this, "MyDatabase.db", null, 1);
        db = usdbh.getWritableDatabase();

        listAlarma=new ArrayList<Producto>();

        MainActivity.AdapterProductos = new MainActivity.AdapterProductos(this,listAlarma);
        AlarmasListView.setAdapter((ListAdapter) MainActivity.AdapterProductos);
        AlarmasListView.setVisibility(View.INVISIBLE);

        if(!Calculos){
            String[] campos = new String[]{"NOMBRE", "CANTIDAD", "PRECIO"};

            Cursor c = db.query("PRODUCTOS", campos, null, null, null, null, null);

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String nombre = c.getString(0);
                    String cantidad = c.getString(1);
                    String precio = c.getString(2);

                    cantidadInt = Integer.parseInt(cantidad);
                    precioInt = Integer.parseInt(precio);
                    cantidadTotal = cantidadTotal + cantidadInt;
                    precioTotal = precioTotal + precioInt * cantidadInt;

                    if (cantidadInt < 5)
                    {
                        listAlarma.add(new Producto(nombre,precio,cantidad));
                        MainActivity.AdapterProductos = new MainActivity.AdapterProductos(this,listAlarma);
                        AlarmasListView.setAdapter((ListAdapter) MainActivity.AdapterProductos);
                        AlarmasListView.setVisibility(View.VISIBLE);
                        AlarmaTexto.setVisibility(View.VISIBLE);
                    }
                } while (c.moveToNext());
                String frase= "En total hay "+Integer.toString(cantidadTotal)+" productos y su valor total es de "+Integer.toString(precioTotal);
                Texto.setText(frase);
                Calculos=true;
            }
            else{
                Texto.setText("Su Catalogo esta vacio :/");
            }
        }

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentRetornoStats= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(IntentRetornoStats);
            }
        });
        }
    }

