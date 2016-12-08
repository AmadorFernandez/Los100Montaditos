package com.amador.los100montaditos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by amador on 6/12/16.
 */

public class Producto implements Comparable<Producto>, Parcelable {

    private String nombre;
    private int cantidad;
    private String tag;
    public static final String TAG_MONTADITO = "montadito";
    public static final String TAG_BEBIDA = "bebida";

    public static final Comparator<Producto> ORDRBY_ASC = new Comparator<Producto>() {
        @Override
        public int compare(Producto producto, Producto t1) {
            return producto.getNombre().compareToIgnoreCase(t1.getNombre());
        }
    };
    public static final Comparator<Producto> ORDRBY_DES = new Comparator<Producto>() {
        @Override
        public int compare(Producto producto, Producto t1) {
            return t1.getNombre().compareToIgnoreCase(producto.getNombre());
        }
    };

    public String getTag(){

        return this.tag;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {

        if(cantidad > 10){

            cantidad = 10;

        }else if(cantidad < 0){

            cantidad = 0;
        }

        this.cantidad = cantidad;
    }

    public Producto(String nombre, String tag){

        this.nombre = nombre;
        this.tag = tag;
        this.cantidad = 0;

    }

    @Override
    public String toString() {

        return this.nombre;
    }

    @Override
    public boolean equals(Object obj) {

        boolean result = false;

        if(obj != null){

            if(obj instanceof Producto){

                result = ((Producto)obj).getNombre().equalsIgnoreCase(this.nombre);
            }
        }

        return result;
    }

    @Override
    public int compareTo(Producto producto) {
        return this.getNombre().compareToIgnoreCase(producto.getNombre());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeInt(this.cantidad);
        dest.writeString(this.tag);
    }

    protected Producto(Parcel in) {
        this.nombre = in.readString();
        this.cantidad = in.readInt();
        this.tag = in.readString();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel source) {
            return new Producto(source);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}
