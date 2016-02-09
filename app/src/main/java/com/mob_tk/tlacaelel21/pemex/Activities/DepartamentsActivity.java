package com.mob_tk.tlacaelel21.pemex.Activities;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.mob_tk.tlacaelel21.pemex.Adapter.DepartamentsAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.DepartamentsModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class DepartamentsActivity extends Activity{

    private String listview_array[] = {"Superintendencia","SIPA","Departamento Zona Norte",
            "Mantenimiento","Administración/USRZC","Operación","Recursos Humanos","USIPA"};

    EditText buscador;
    ListView lista;
    private ArrayList<String> array_sort = new ArrayList<String>();
    int textlength = 0;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departaments_content);
        Context context=getApplicationContext();

        lista = (ListView) findViewById(R.id.list_departaments);
        buscador=(EditText)findViewById(R.id.txt_busca_department);
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listview_array));
        
        //        R.layout.list_departaments,listaWP );
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
                lista.setAdapter(new ArrayAdapter<String>(DepartamentsActivity.this, android.R.layout.simple_list_item_1, array_sort));
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //new AuditP1Activity().findViewById(R.id.boton_wc);
                SharedPreferences preferences;
                preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("departament", "" + lista.getItemAtPosition(position));
                editor.commit();
                Intent intent =
                        new Intent(DepartamentsActivity.this, AuditP1Activity.class);
                startActivity(intent);
                finish();
            }
        });
        

    }
}
