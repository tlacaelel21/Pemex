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
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.Adapter.WorkCenterAdapter;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gabo on 11/02/16.
 */
public class GeneralContainerActivity2 extends Activity {
    EditText buscador;
    private ArrayList<String> array_sort = new ArrayList<String>();
    int textlength = 0;
    ListView lista;
    TextView gralTitle;
    String titulo, query, campo, clave;


    private String listview_array[];
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_container);
        final Context context=getApplicationContext();
        lista = (ListView) findViewById(R.id.gral_list);
        buscador=(EditText)findViewById(R.id.txt_gral_search);
        gralTitle=(TextView)findViewById(R.id.gral_title);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            titulo=extras.getString("titulo");
            query=extras.getString("query");
            campo=extras.getString("campo");
            clave=extras.getString("clave");
        }

        gralTitle.setText(titulo);
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, query);
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            listview_array[idx]=results.get(idx).get(campo);
            //Log.i("CAMPO->",results.get(idx).get(campo));
        }

        lista.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listview_array));
        buscador.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
                lista.setAdapter(new ArrayAdapter<String>(GeneralContainerActivity2.this, android.R.layout.simple_list_item_1, array_sort));
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("clave",results.get(position).get(clave));
                intent.putExtra("desc",results.get(position).get(campo));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
