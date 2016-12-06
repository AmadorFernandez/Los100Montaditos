package com.amador.los100montaditos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by amador on 6/12/16.
 */

public class Preferences {

    private static Context context;
    private static SharedPreferences preferences;
    private static Preferences instance;

    public static Preferences getInstance(Context contextView){

        if(instance == null){

            context =contextView;
            instance = new Preferences();
        }

        return instance;
    }

    private Preferences(){

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private SharedPreferences.Editor getEditor(){

        return preferences.edit();
    }

    public void save(){

        SharedPreferences.Editor editor = getEditor();
        ListProductRepository repository = ListProductRepository.getInstace(context);

        for(Producto producto: repository){

            editor.putInt(producto.getNombre(), producto.getCantidad());
        }

        editor.commit();
    }

    public void load(){

        ListProductRepository repository = ListProductRepository.getInstace(context);
        Producto p;

        for(int i = 0; i < repository.size(); i++){

            p = repository.get(i);
            p.setCantidad(preferences.getInt(p.getNombre(), 0));
        }
    }
}
