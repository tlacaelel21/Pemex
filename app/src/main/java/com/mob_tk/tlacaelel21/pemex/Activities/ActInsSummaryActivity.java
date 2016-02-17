package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tlacaelel21 on 11/02/16.
 */
public class ActInsSummaryActivity extends Activity {
    ImageButton imageButtonClose,iBAdd,iBQuest;
    ListView lista;
    private String listview_array[];
    SharedPreferences preferences;
    int tipo=0;
    String id_persona="";

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actos_inseg_summary);
        Context context=getApplicationContext();
        imageButtonClose=(ImageButton) findViewById(R.id.imageButtonClose);
        iBAdd=(ImageButton) findViewById(R.id.iBAdd);
        iBQuest=(ImageButton) findViewById(R.id.iBQuest);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            tipo=Integer.parseInt(extras.getString("tipo"));
            id_persona=extras.getString("id_persona");

        }

        if(tipo==1)
            iBAdd.setVisibility(View.INVISIBLE);
        else
            iBQuest.setVisibility(View.INVISIBLE);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);

        lista = (ListView) findViewById(R.id.list_ai);

        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo==1){
                    Intent intent =
                            new Intent(ActInsSummaryActivity.this, AuditSummary.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent =
                            new Intent(ActInsSummaryActivity.this, WorkersListBActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });
        /**Llenamos la lsita con los actos inseguros*/
        String queryEmp="";
        if(tipo==1){
            queryEmp="SELECT ai_id,ai_identificador, ai_desc FROM actoinseguro "+
                " WHERE ai_id IN (SELECT id_acto_inseguro FROM calificaciones)";
        }else{
            queryEmp="SELECT ai_id,ai_identificador, ai_desc FROM actoinseguro "+
                    " WHERE ai_id IN (SELECT id_acto_inseguro FROM calificaciones WHERE id_emp='"+id_persona+"')";
        }
        Log.i("QRY","q= "+queryEmp);
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, queryEmp);
        Log.i("SZE","s= "+results.size());
        listview_array=new String[results.size()];
        for(int idx=0;idx<results.size();idx++){
            listview_array[idx]="  "+results.get(idx).get("ai_identificador")+
                    "  "+results.get(idx).get("ai_desc");
        }
        /********************************* */
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listview_array));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(tipo!=1){
                    Intent intent =
                            new Intent(ActInsSummaryActivity.this, NewCorrectiveActivity.class);
                    intent.putExtra("id_emp",""+id_persona);
                    intent.putExtra("ai_id", results.get(position).get("ai_id"));
                    startActivity(intent);
                }
            }
        });

    }
}



