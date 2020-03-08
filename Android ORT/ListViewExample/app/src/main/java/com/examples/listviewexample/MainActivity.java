package com.examples.listviewexample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;




import com.examples.listviewexample.models.Producto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private ListView listViewProductos;
    private AdapterProductos adapterProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewProductos = findViewById(R.id.list_productos);
        ArrayList<Producto> productos = new ArrayList<Producto>();


        productos.add(new Producto("Manzana",10.0,50.0));
        productos.add(new Producto("Pera",5.0,60.0));
        productos.add(new Producto("Banana",7.0,70.0));
        productos.add(new Producto("Naranja",8.0,80.0));

        adapterProductos = new AdapterProductos(this,productos);

        listViewProductos.setAdapter(adapterProductos);

        /*Ejemplo para agregar obejos y quitarlos, se muestran por consola */

//        for (Producto productoActual : productos)
//        {
//            Log.d("lista", productoActual.getNombre());
//        }
//
//        productos.remove(1);
//
//        for (Producto productoActual : productos)
//        {
//            Log.d("lista", productoActual.getNombre());
//        }
//
//        productos.add(1,new Producto("kiwi",10.0,35.0));
//
//        for (Producto productoActual : productos)
//        {
//            Log.d("lista", productoActual.getNombre());
//        }

    }

    class AdapterProductos extends ArrayAdapter<Producto>
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
            super(context, R.layout.item_producto, listProductos);
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
                item = inflater.inflate(R.layout.item_producto, null);

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
            holder.txtPrecio.setText(String.valueOf(listProductos.get(position).getPrecio()));
            holder.txtCantidad.setText(String.valueOf(listProductos.get(position).getCantidad()));
            return(item);
        }
    }

}

