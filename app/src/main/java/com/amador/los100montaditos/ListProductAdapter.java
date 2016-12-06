package com.amador.los100montaditos;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amador on 6/12/16.
 */

public class ListProductAdapter extends ArrayAdapter<Producto> {

    private Context context;
    public static final int TYPE_ORDER_ASC = 1;
    public static final int TYPE_ORDER_DES = 2;
    public static final int FIRST_MONTADITOS = 3;
    public static final int FIRST_BEBIDAS = 4;
    public static final int ONLY_BEBIDAS = 5;
    public static final int ONLY_MONTADITOS = 6;
    public static final int All_PODUCT = 7;



    public ListProductAdapter(Context context) {
        super(context, R.layout.item_list_product,
                new ArrayList<Producto>(ListProductRepository.getInstace(context)));
        this.context = context;
    }

    public ListProductAdapter(Context context, ArrayList<Parcelable> datas) {
        super(context, R.layout.item_list_product);
        this.context = context;

        for(Parcelable parcelable: datas){

            add((Producto)parcelable);
        }
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rootView = convertView;
        final ProductosHolder holder;

        if(rootView == null){

            rootView = LayoutInflater.from(this.context).inflate(R.layout.item_list_product, null);
            holder = new ProductosHolder();
            holder.edtCount = (EditText)rootView.findViewById(R.id.edtCount);
            holder.edtCount.setEnabled(false);
            holder.imvSum = (ImageView)rootView.findViewById(R.id.imvSum);
            holder.imvSub = (ImageView)rootView.findViewById(R.id.imvSub);
            holder.txvName = (TextView)rootView.findViewById(R.id.txvName);
            rootView.setTag(holder);

        }else {

            holder = (ProductosHolder)rootView.getTag();
        }



        holder.txvName.setText(getItem(position).getNombre());
        holder.edtCount.setText(String.valueOf(getItem(position).getCantidad()));

        holder.imvSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Producto p = getItem(position);
                int value = p.getCantidad();
                p.setCantidad(++value);
                holder.edtCount.setText(String.valueOf(p.getCantidad()));
            }
        });

        holder.imvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Producto p = getItem(position);
                int value = p.getCantidad();
                p.setCantidad(--value);
                holder.edtCount.setText(String.valueOf(p.getCantidad()));

            }
        });

        return rootView;
    }

    public ArrayList<Producto> getAllProduct(){

        ArrayList<Producto> actualState = new ArrayList<Producto>();

        for(int i = 0; i < getCount(); i++){

            actualState.add(getItem(i));
        }

        return actualState;
    }

    public void filterProduct(String textFiler){

        ListProductRepository list = ListProductRepository.getInstace(this.context);
        clear();

        for (Producto producto: list){

            if(producto.getNombre().toUpperCase().startsWith(textFiler.toUpperCase())){

                add(producto);
            }
        }
    }

    public void orderBy(int typeOrder){

        switch (typeOrder){

            case All_PODUCT:
                allProduct();
                break;
            case TYPE_ORDER_ASC:
                sort(Producto.ORDRBY_ASC);
                break;
            case TYPE_ORDER_DES:
                sort(Producto.ORDRBY_DES);
                break;
            case ONLY_BEBIDAS:
                onlyBebidas();
                break;
            case ONLY_MONTADITOS:
                onlyMontaditos();
                break;
            case FIRST_BEBIDAS:
            case FIRST_MONTADITOS:
                groupBy(typeOrder);
                break;
        }
    }

    private void groupBy(int tipeGroup){

        ListProductRepository list = ListProductRepository.getInstace(this.context);
        ArrayList<Producto> bebidas = new ArrayList<Producto>();
        ArrayList<Producto> montaditos = new ArrayList<Producto>();

        clear();

        for(Producto producto: list){

            if(producto.getTag().equals(Producto.TAG_BEBIDA)){

                bebidas.add(producto);

            }else {

                montaditos.add(producto);
            }
        }

        switch (tipeGroup){

            case FIRST_BEBIDAS:
                addAll(bebidas);
                addAll(montaditos);
                break;
            case FIRST_MONTADITOS:
                addAll(montaditos);
                addAll(bebidas);
                break;
        }

    }

    private void allProduct(){

        clear();
        addAll(ListProductRepository.getInstace(this.context));
    }

    private void onlyBebidas() {

        ListProductRepository list = ListProductRepository.getInstace(this.context);
        clear();

        for(Producto producto: list){

            if(producto.getTag().equals(Producto.TAG_BEBIDA)){

                add(producto);
            }
        }

    }

    private void onlyMontaditos(){

        ListProductRepository list = ListProductRepository.getInstace(this.context);
        clear();

        for(Producto producto: list){

            if(producto.getTag().equals(Producto.TAG_MONTADITO)){

                add(producto);
            }
        }

    }

    class ProductosHolder{

        ImageView imvSum, imvSub;
        TextView txvName;
        EditText edtCount;
    }
}
