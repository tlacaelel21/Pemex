package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mob_tk.tlacaelel21.pemex.Adapter.WorkCenterAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.WorkCenterModel;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tlacaelel21 on 22/10/15.
 */
public class WorkCentersActivity extends Activity {

    EditText buscador;
    WorkCenterAdapter workCenterAdapter;
    private ArrayList<String> array_sort = new ArrayList<String>();
    int textlength = 0;
    ListView lista;
    ImageButton imgBack;
    private SharedPreferences preferences;

    private String listview_array[];
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workplace_content);
        final Context context=getApplicationContext();
        lista = (ListView) findViewById(R.id.list_wc);
        buscador=(EditText)findViewById(R.id.txt_busca_wc);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        imgBack=(ImageButton) findViewById(R.id.imgBack);

        /**Se realiza la consulta a la base de datos */
        int reg_id=0;
        if(null!=preferences.getString("reg_id", "")) {
            if(preferences.getString("reg_id", "").length()>0)
                reg_id=Integer.parseInt(preferences.getString("reg_id", ""));
        }
        final ArrayList<HashMap<String, String>> results;
        results=Utils.exeLocalQuery(this, "select ctr_id, ctr_desc, reg_id from centrotrabajo  WHERE reg_id="+reg_id);
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            listview_array[idx]=results.get(idx).get("ctr_desc");
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
                lista.setAdapter(new ArrayAdapter<String>(WorkCentersActivity.this, android.R.layout.simple_list_item_1, array_sort));
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //new AuditP1Activity().findViewById(R.id.boton_wc);
                SharedPreferences preferences;
                preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("worc_center", "" + lista.getItemAtPosition(position));
                editor.putString("ctr_id", "" + results.get(position).get("ctr_id"));
                editor.commit();
                Intent intent =
                        new Intent(WorkCentersActivity.this, AuditP1Activity.class);
                startActivity(intent);
                finish();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(WorkCentersActivity.this, AuditP1Activity.class);
                startActivity(intent);
                finish();
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
