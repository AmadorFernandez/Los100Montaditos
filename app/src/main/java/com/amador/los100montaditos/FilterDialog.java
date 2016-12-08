package com.amador.los100montaditos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by amador on 6/12/16.
 */

public class FilterDialog extends DialogFragment {

    private int op;
    private DialogListener listener;


    public interface DialogListener{

        void onSelection(int op);
    }

    public void setDialogListener(DialogListener listener){

        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_dialog);
        op = 0;

        String[] datos = getResources().getStringArray(R.array.selection);
        builder.setSingleChoiceItems(datos,0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                op = i;
            }
        }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(listener != null){

                    listener.onSelection(op);
                }
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });


        return builder.create();

    }


}
