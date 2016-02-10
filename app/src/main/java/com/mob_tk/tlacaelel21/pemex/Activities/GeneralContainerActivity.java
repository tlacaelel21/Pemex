package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.Adapter.WorkCenterAdapter;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tlacaelel21 on 19/01/16.
 */
public class GeneralContainerActivity extends Activity {
    EditText buscador;
    private ArrayList<String> array_sort = new ArrayList<String>();
    int textlength = 0;
    ListView lista;
    TextView gralTitle;
    String titulo, query, campo, clave, tipo;
    int procede=0;
    ImageButton imgBack;

    private String listview_array[];
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_container);
        final Context context=getApplicationContext();
        lista = (ListView) findViewById(R.id.gral_list);
        buscador=(EditText)findViewById(R.id.txt_gral_search);
        gralTitle=(TextView)findViewById(R.id.gral_title);
        imgBack=(ImageButton) findViewById(R.id.imgBack);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            titulo=extras.getString("titulo");
            query=extras.getString("query");
            campo=extras.getString("campo");
            clave=extras.getString("clave");
            tipo=extras.getString("tipo");
            procede=Integer.parseInt(extras.getString("procede"));
        }

        gralTitle.setText(titulo);
        /**Se realiza la consulta a la base de datos */
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, query);
        //Log.i("TAM->",""+results.size());
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            listview_array[idx]=results.get(idx).get(campo);
            Log.i("CAMPO->",results.get(idx).get(campo));
        }
        /** ******************************* */

        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listview_array));
        buscador.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Abstract Method of TextWatcher Interface.
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Abstract Method of TextWatcher Interface.
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = buscador.getText().length();
                array_sort.clear();
                for (int i = 0; i < listview_array.length; i++) {
                    if (textlength <= listview_array[i].toString().length()) {
                        if (buscador.getText().toString().equalsIgnoreCase((String) listview_array[i].toString().subSequence(0, textlength))) {
                            array_sort.add(listview_array[i].toString());
                        }
                    }
                }
                lista.setAdapter(new ArrayAdapter<String>(GeneralContainerActivity.this, android.R.layout.simple_list_item_1, array_sort));
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //new AuditP1Activity().findViewById(R.id.boton_wc);
                SharedPreferences preferences;
                preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(tipo, "" + lista.getItemAtPosition(position));
                editor.putString(clave, "" + results.get(position).get(clave));
                editor.commit();
                if (procede == 1) {
                    Intent intent =
                            new Intent(GeneralContainerActivity.this, AuditP1Activity.class);
                    startActivity(intent);
                    finish();
                }
                if (procede == 2) {
                    Intent intent =
                            new Intent(GeneralContainerActivity.this, AuditP2Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (procede == 1) {
                    Intent intent =
                            new Intent(GeneralContainerActivity.this, AuditP1Activity.class);
                    startActivity(intent);
                    finish();
                }
                if (procede == 2) {
                    Intent intent =
                            new Intent(GeneralContainerActivity.this, AuditP2Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
