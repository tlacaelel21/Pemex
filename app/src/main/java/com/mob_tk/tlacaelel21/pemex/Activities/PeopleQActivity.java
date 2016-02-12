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
import android.widget.ImageButton;
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
    TextView titleQ;
    String titulo, query, campo, clave, ai_id;
    int procede=0;
    ImageButton imageButtonClose;
    String valoresTitulo[];
    private SharedPreferences preferences;

    private String listview_array[];
    int califs[];
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);

        setContentView(R.layout.activity_people_to_qualify);
        final Context context=getApplicationContext();

        lista = (ListView) findViewById(R.id.list_people_qualify);
        imageButtonClose=(ImageButton) findViewById(R.id.imageButtonClose);
        titleQ=(TextView)findViewById(R.id.tv_title_qualify);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //titulo=extras.getString("titulo");
            query=extras.getString("query");
            campo=extras.getString("campo");
            clave=extras.getString("clave");
            //ai_id=extras.getString("ai_id");
            //procede=Integer.parseInt(extras.getString("procede"));
        }
        if(null!=preferences.getString("titulo", "")) {
            if(preferences.getString("titulo", "").length()>0){
                titulo=(preferences.getString("titulo", ""));
                titleQ.setText(preferences.getString("titulo", ""));
            }
        }
        if(null!=preferences.getString("ai_id", "")) {
            if(preferences.getString("ai_id", "").length()>0){
                ai_id=(preferences.getString("ai_id", ""));
            }
        }

        //Log.i("CLAVE","->"+ai_id);
        /**Se realiza la consulta a la base de datos para empleados y contratistas*/
        Log.i("QUERY_PQ",""+query);
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, query);
        califs=new int[results.size()];
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            String query2="SELECT calif FROM calificaciones " +
                    "WHERE num_emp='"+results.get(idx).get("emp_num_emp")+"' AND id_acto_inseguro='"+ai_id+"'";
            Log.i("QUERY_PQ_CAL",""+query2);
            final ArrayList<HashMap<String, String>> empCals;
            empCals= Utils.exeLocalQuery(this, query2);
            if(empCals.size()>0){
                califs[idx]=Integer.parseInt(empCals.get(0).get("calif"));
                listview_array[idx]="    "+califs[idx]+"    "+results.get(idx).get(campo);
            }else {
                califs[idx] = 0;
                listview_array[idx]="    "+califs[idx]+"    "+results.get(idx).get(campo);
            }
        }

        /********************************* */
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listview_array));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =
                        new Intent(PeopleQActivity.this, AssignUnsafeWorkersActivity.class);
                intent.putExtra("query", "" + query);
                intent.putExtra("campo", "" + campo);
                intent.putExtra("nombre", "" + results.get(position).get(campo));
                intent.putExtra("emp_num_emp", "" + results.get(position).get(clave));
                intent.putExtra("emp_id", "" + results.get(position).get("emp_id"));
                intent.putExtra("ai_id", "" + ai_id);
                intent.putExtra("titulo", "" + titulo);
                startActivity(intent);
                finish();
            }
        });
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(PeopleQActivity.this, UnsafeActActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
