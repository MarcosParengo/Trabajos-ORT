package com.ort.andymarki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ort.andymarki.Database.ProductosSqliteHelper;
import com.ort.andymarki.models.Producto;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Object AdapterProductos;
    private ListView listViewProductos;
    private AdapterProductos adapterProductos;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    private boolean agregado=false;

    private SQLiteDatabase db;
    private ProductosSqliteHelper usdbh;
    private Cursor c;

    ArrayList<Producto> listaproductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =  findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        listViewProductos = findViewById(R.id.ListViewProductos);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // elimina el titulo por defecto de la actionbar
        toolbarTitle.setText("AnduYMarku");
        toolbar.setTitle("");

        usdbh=new ProductosSqliteHelper(getApplicationContext(),"bd productos",null,1);

        listaproductos=new ArrayList<Producto>();

        //listaproductos.add(new Producto("Producto","Cantidad","Precio"));

        adapterProductos = new AdapterProductos(this,listaproductos);
        listViewProductos.setAdapter(adapterProductos);

        usdbh = new ProductosSqliteHelper(this, "MyDatabase.db", null, 1);
        db = usdbh.getWritableDatabase();
        
        if(!agregado){
            String[] campos = new String[]{"NOMBRE", "CANTIDAD", "PRECIO"};

            Cursor c = db.query("PRODUCTOS", campos, null, null, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String nombre = c.getString(0);
                    String cantidad = c.getString(1);
                    String precio = c.getString(2);
                    listaproductos.add(new Producto(nombre, cantidad, precio));
                    adapterProductos.notifyDataSetChanged();
                } while (c.moveToNext());
            }
            agregado=true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.Estadisticas:
                Toast.makeText(this, "leeconomisté!!", Toast.LENGTH_SHORT).show();
                Intent intentEstadisticas = new Intent(getApplicationContext(),Estadisticas.class);
                startActivity(intentEstadisticas);
                agregado=false;
                break;
            case R.id.Agregar:
                Toast.makeText(this, "Adentro Pa!!", Toast.LENGTH_SHORT).show();
                Intent intentAgregat = new Intent(getApplicationContext(),AgregarActivity.class);
                startActivity(intentAgregat);
                agregado=false;
                break;
            case R.id.Eliminar:
                db.delete("PRODUCTOS", null, null);
                listaproductos.clear();
            break;
        }

        return true;
    }
    static class AdapterProductos extends ArrayAdapter<Producto>
    {

        private Activity context;
        private ArrayList<Producto> listProductos;

        class ViewHolder
        {
            TextView txtNombre;
            TextView txtCantidad;
            TextView txtPrecio;

        }

        AdapterProductos(Activity context,ArrayList<Producto> listProductos)
        {
            super(context, R.layout.itemproductos, listProductos);
            this.context = context;
            this.listProductos = listProductos;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View item = convertView;
            ViewHolder holder;
            if(item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.itemproductos, null);
                holder = new ViewHolder();
                holder.txtNombre = item.findViewById(R.id.txtNombre);
                holder.txtCantidad = item.findViewById(R.id.txtCantidad);
                holder.txtPrecio = item.findViewById(R.id.txtPrecio);
                item.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)item.getTag();
            }
            holder.txtNombre.setText(listProductos.get(position).getNombre());
            String precio = "$"+String.valueOf(listProductos.get(position).getPrecio());
            String cantidad = "X"+String.valueOf(listProductos.get(position).getCantidad());
            holder.txtPrecio.setText(precio);
            holder.txtCantidad.setText(cantidad);
            return(item);
        }
    }
}
