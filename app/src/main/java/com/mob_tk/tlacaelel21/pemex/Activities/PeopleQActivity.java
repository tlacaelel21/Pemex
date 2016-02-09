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

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by tlacaelel21 on 1/02/16.
 */
public class PeopleQActivity extends Activity {
    EditText buscador;
    private ArrayList<String> array_sort = new ArrayList<String>();
    int textlength = 0;
    ListView lista;
    TextView gralTitle;
    String titulo, query, campo, clave, tipo;
    int procede=0;


    private String listview_array[];
    int califs[];
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_people_to_qualify);
        final Context context=getApplicationContext();

        lista = (ListView) findViewById(R.id.list_people_qualify);
        /*buscador=(EditText)findViewById(R.id.txt_gral_search);
        gralTitle=(TextView)findViewById(R.id.gral_title);*/

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            titulo=extras.getString("titulo");
            query=extras.getString("query");
            campo=extras.getString("campo");
            clave=extras.getString("clave");
            tipo=extras.getString("tipo");
            procede=Integer.parseInt(extras.getString("procede"));
        }

        //gralTitle.setText(titulo);
        /**Se realiza la consulta a la base de datos */
        Log.i("QUERY_PQ",""+query);
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, query);
        califs=new int[results.size()];
        listview_array=new String[results.size()];

        for(int idx=0;idx<results.size();idx++){
            String query2="SELECT aa_calif, aa_desc FROM auditoriaai " +
                    "WHERE ae_id=(SELECT emp_id FROM empleado WHERE emp_num_emp="+results.get(idx).get("emp_num_emp")+")";
            final ArrayList<HashMap<String, String>> empCals;
            empCals= Utils.exeLocalQuery(this, query2);
            if(empCals.size()>0){
                Log.i("ENTRO","DATO");
                califs[idx]=Integer.parseInt(empCals.get(0).get("aa_calif"));
            }else {
                Log.i("ENTRO","Pone_0");
                califs[idx] = 0;
            }
            listview_array[idx]="    "+califs[idx]+"    "+results.get(idx).get(campo);
            Log.i("CAMPO->",results.get(idx).get(campo));
        }


        /********************************* */
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listview_array));
        /*buscador.addTextChangedListener(new TextWatcher() {
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
                lista.setAdapter(new ArrayAdapter<String>(PeopleQActivity.this, android.R.layout.simple_list_item_1, array_sort));
            }
        });*/

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
                            new Intent(PeopleQActivity.this, AssignUnsafeWorkersActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (procede == 2) {
                    Intent intent =
                            new Intent(PeopleQActivity.this, AssignUnsafeWorkersActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
