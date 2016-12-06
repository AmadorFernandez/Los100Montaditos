package com.amador.los100montaditos;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by amador on 6/12/16.
 */

public class ListProductRepository extends ArrayList<Producto> {

    private static ListProductRepository instace;

    private ListProductRepository(Context context){

            cargarProductos(context);
    }

    private void cargarProductos(Context context){

        String[] montaditos = StreamIO.readLine(context.getResources().openRawResource(R.raw.montaditos)).split("\n");
        String[] bebidas = StreamIO.readLine(context.getResources().openRawResource(R.raw.bebidas)).split("\n");

        for(String tmp: montaditos){

            add(new Producto(tmp, Producto.TAG_MONTADITO));
        }

        for(String tmp: bebidas){

            add(new Producto(tmp, Producto.TAG_BEBIDA));
        }
    }

    public static ListProductRepository getInstace(Context context){

        if(instace == null){

            instace = new ListProductRepository(context);
        }

        return instace;
    }
}
