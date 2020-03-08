package com.leobrasileo.tp3ertrimestre;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.leobrasileo.tp3ertrimestre.Database.ProductosSQLiteHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ProductosSQLiteHelper pdbh;
    private Cursor cursor;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.listView1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = findViewById(R.id.floatingActionButton);
        setSupportActionBar(toolbar);
        pdbh = new ProductosSQLiteHelper(this, "MyDatabase.db", null, 1);
        db = pdbh.getWritableDatabase();

        ArrayList<Producto> productos = new ArrayList<Producto>();
        productos.add(new Producto("Nombre de producto:", "Cantidad:", "Precio unitario:"));
        productos.add(new Producto("", "", "")); //dejo un espacio por comodidad

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AgregarProducto.class);
                startActivity(intent);
            }
        });

        String[] campos = new String[] {"nombre", "cantidad", "precio"};
        Cursor cursor = db.query("Productos",campos,null,null,null,null,null);

        if (cursor.moveToFirst())
        {
            do {
                String nombre = cursor.getString(0);
                String cantidad = cursor.getString(1);
                String precio = cursor.getString(2);
                productos.add(new Producto(nombre, cantidad, precio));
            }while (cursor.moveToNext());
        }

        Adapter adapter = new Adapter(MainActivity.this,productos);
        listView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.action_stats:

                Intent intent = new Intent(MainActivity.this,Estadisticas.class);
                startActivity(intent);

                break;
        }

        return true;
    }

    class Adapter extends ArrayAdapter<Producto>
    {

        private Activity context;
        public ArrayList<Producto> listProductos;

        class ViewHolder
        {
            TextView txtNombre;
            TextView txtCantidad;
            TextView txtPrecio;
        }

        Adapter(Activity context,ArrayList<Producto> listProductos)
        {
            super(context, R.layout.item_materias, listProductos);
            this.context = context;
            this.listProductos = listProductos;
        }

        public ArrayList<Producto> getArrayList() {
            return listProductos;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View item = convertView;
            ViewHolder holder;

            if(item == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                item = inflater.inflate(R.layout.item_materias, null);

                holder = new ViewHolder();
                holder.txtNombre = item.findViewById(R.id.textViewNombre);
                holder.txtCantidad = item.findViewById(R.id.textViewCant);
                holder.txtPrecio= item.findViewById(R.id.textViewPrecio);
                item.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)item.getTag();
            }

            holder.txtNombre.setText(listProductos.get(position).getNombre());
            holder.txtPrecio.setText(listProductos.get(position).getPrecio());
            holder.txtCantidad.setText(listProductos.get(position).getCantidad());
            return(item);
        }
    }
}
