package com.amador.los100montaditos;

import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnOk;
    private ListProductAdapter adapter;
    private FilterDialog dialog;
    private RelativeLayout parent;
    private static final String RECOVERY_KEY = "KEY";
    private FilterDialog.DialogListener listener = new FilterDialog.DialogListener() {
        @Override
        public void onSelection(int op) {

            switch (op){

                case 0:
                    adapter.orderBy(ListProductAdapter.ONLY_MONTADITOS);
                    break;
                case 1:
                    adapter.orderBy(ListProductAdapter.ONLY_BEBIDAS);
                    break;
                case 2:
                    adapter.orderBy(ListProductAdapter.All_PODUCT);
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        parent = (RelativeLayout)findViewById(R.id.activity_home);
        Preferences.getInstance(HomeActivity.this).load();
        listView = (ListView)findViewById(R.id.listView);
        btnOk = (Button)findViewById(R.id.btnOk);
        dialog = new FilterDialog();
        dialog.setDialogListener(listener);

        if(savedInstanceState != null){

            adapter = new ListProductAdapter(HomeActivity.this, savedInstanceState.getParcelableArrayList(RECOVERY_KEY));

        }else {

            adapter = new ListProductAdapter(HomeActivity.this);
        }

        listView.setAdapter(adapter);
        listView.setLongClickable(true);

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                dialog.show(getSupportFragmentManager(), null);
                return true;
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Preferences.getInstance(HomeActivity.this).save();
                Snackbar snackbar = Snackbar.make(parent, getString(R.string.save_msg), Snackbar.LENGTH_LONG);
                View rootView = snackbar.getView();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)rootView.getLayoutParams();
                params.gravity = Gravity.TOP;
                rootView.setLayoutParams(params);
                snackbar.show();
            }
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        ArrayList<Producto> estado = new ArrayList<Producto>();

        for(int i = 0; i < adapter.getCount(); i++){

            estado.add(adapter.getItem(i));
        }

        outState.putParcelableArrayList(RECOVERY_KEY, estado);
        super.onSaveInstanceState(outState);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_order_alf_asc:
                adapter.orderBy(ListProductAdapter.TYPE_ORDER_ASC);
                break;
            case R.id.action_order_alf_desc:
                adapter.orderBy(ListProductAdapter.TYPE_ORDER_DES);
                break;
            case R.id.action_order_beb:
                adapter.orderBy(ListProductAdapter.FIRST_BEBIDAS);
                break;
            case R.id.action_order_mon:
                adapter.orderBy(ListProductAdapter.FIRST_MONTADITOS);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);


        return super.onCreateOptionsMenu(menu);
    }



}
