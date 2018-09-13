package com.example.diego.note_app.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.diego.note_app.R;
import com.example.diego.note_app.adapters.MyAdapter;

import com.example.diego.note_app.models.Nota;


import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainGridActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Nota>> {

    private MyAdapter adapter;
    private GridView gridView;
    final Context context = this;
    String notas, notas2;
    int posicion;
    int aux_color;
    String aux_nota;


    private Realm realm;
    private RealmResults<Nota> nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        gridView = (GridView) findViewById(R.id.gridView);

        nota = getAllNota();
        nota.addChangeListener(this);

        adapter = new MyAdapter(nota, R.layout.grid_item, this);

        gridView.setAdapter(adapter);
        registerForContextMenu(gridView);

    }

    @Override
    public void onChange(RealmResults<Nota> element) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.nota.get(info.position).getId() + "");
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_item:
                realm.beginTransaction();
                nota.deleteFromRealm(info.position); // App crash
                realm.commitTransaction();
                return true;
            case R.id.change_item: {
                // get prompts.xml view
                posicion = info.position;
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        notas2 = String.valueOf(userInput.getText());

                                        realm.beginTransaction();
                                        nota.get(posicion).setNota(notas2);
                                        realm.commitTransaction();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


                return true;
            }
            case R.id.color_item:
                posicion = info.position;

                return true;

            case R.id.change_item_position:
                posicion = info.position;
                return true;
            case R.id.move_firts:
                realm.beginTransaction();
                aux_color = nota.get(0).getColor();
                aux_nota = nota.get(0).getNota();
                nota.get(0).setColor(nota.get(posicion).getColor());
                nota.get(0).setNota(nota.get(posicion).getNota());
                nota.get(posicion).setColor(aux_color);
                nota.get(posicion).setNota(aux_nota);
                realm.commitTransaction();
                return true;

            case R.id.move_last:
                realm.beginTransaction();
                aux_color = nota.get(nota.size() - 1).getColor();
                aux_nota = nota.get(nota.size() - 1).getNota();
                nota.get(nota.size() - 1).setColor(nota.get(posicion).getColor());
                nota.get(nota.size() - 1).setNota(nota.get(posicion).getNota());
                nota.get(posicion).setColor(aux_color);
                nota.get(posicion).setNota(aux_nota);
                //int aux_color = nota.get(0).getColor();
                //String aux_nota = nota.get(0).getNota();
                // nota.get(0).setColor(nota.get(info.position).getColor());
                // nota.get(0).setNota(nota.get(info.position).getNota());
                // nota.get(info.position).setColor(aux_color);
                //nota.get(info.position).setNota(aux_nota);
                realm.commitTransaction();
                return true;

            case R.id.change_item_azul:
                realm.beginTransaction();
                nota.get(posicion).setColor(1); // App crash
                realm.commitTransaction();
                return true;
            case R.id.change_item_verde:
                realm.beginTransaction();
                nota.get(posicion).setColor(2); // App crash
                realm.commitTransaction();
                return true;
            case R.id.change_item_rosa:
                realm.beginTransaction();
                nota.get(posicion).setColor(3); // App crash
                realm.commitTransaction();
                return true;
            case R.id.change_item_blanco:
                realm.beginTransaction();
                nota.get(posicion).setColor(0); // App crash
                realm.commitTransaction();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_add:

                addNota();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private RealmResults<Nota> getAllNota() {
        return realm.where(Nota.class).findAll();
    }


    private void addNota() {
        // get prompts.xml view

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                notas = String.valueOf(userInput.getText());

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {


                                        Nota n1 = new Nota(notas);


                                        realm.copyToRealmOrUpdate(n1);


                                        nota = getAllNota();
                                    }
                                });
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }
}