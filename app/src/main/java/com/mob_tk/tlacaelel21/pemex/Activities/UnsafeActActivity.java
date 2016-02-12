package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tlacaelel21 on 28/10/15.
 */
public class UnsafeActActivity extends ExpandableListActivity {
    TextView tvCab;
    SharedPreferences preferences;
    String calificaciones[][];

    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.unsafe_act_content);
            tvCab=(TextView) findViewById(R.id.grp_child_cab);
            preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);

            SimpleExpandableListAdapter expListAdapter =
                    new SimpleExpandableListAdapter(
                            this,
                            createGroupList(), 				// Creating group List.
                            R.layout.group_row,				// Group item layout XML.
                            new String[] { "group_ai" },	// the key of group item.
                            new int[] { R.id.row_name },	// ID of each group item.-Data under the key goes into this TextView.
                            createChildList(),				// childData describes second-level entries.
                            R.layout.child_row,				// Layout for sub-level entries(second level).
                            new String[] {"Item"},		    // Keys in childData maps to display.
                            new int[] { R.id.grp_child}	// Data under the keys above go into these TextViews.
                    );
            setListAdapter( expListAdapter );		// setting the adapter in the list.

        }catch(Exception e){
            System.out.println("Errrr +++ " + e.getMessage());
        }
        Button btnContinuar=(Button) findViewById(R.id.btn_audit_p2_continue);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(UnsafeActActivity.this, AuditSummary.class);
                startActivity(intent);
            }
        });
    }

    /* Creating the Hashmap for the row */
    @SuppressWarnings("unchecked")
    private List createGroupList() {
        String query="SELECT ai_id, ai_desc, ai_identificador  FROM actoinseguro " +
                " WHERE ai_id_padre =0 AND ai_status=1 ORDER BY ai_orden ";
        final ArrayList<HashMap<String, String>> dataQuery;
        dataQuery= Utils.exeLocalQuery(this, query);

        ArrayList result = new ArrayList();
        boolean paso=false;
        for( int i = 0 ; i < dataQuery.size() ; ++i ) {
            HashMap m = new HashMap();
                m.put("group_ai", " " + dataQuery.get(i).get("ai_identificador") + ". " +
                        dataQuery.get(i).get("ai_desc")); // the key and it's value.
            result.add( m );
        }
        return result;
    }
    /* creatin the HashMap for the children */
    @SuppressWarnings("unchecked")
    private List createChildList() {
        String query1="SELECT ai_id, ai_desc, ai_identificador  FROM actoinseguro " +
                " WHERE ai_id_padre =0 AND ai_status=1 ORDER BY ai_orden ";
        final ArrayList<HashMap<String, String>> dataQuery1;
        dataQuery1= Utils.exeLocalQuery(this, query1);

        ArrayList result = new ArrayList();
        for( int i = 0 ; i < dataQuery1.size() ; ++i ) { // this -15 is the number of groups(Here it's fifteen)
    	  /* each group need each HashMap-Here for each group we have 3 subgroups */
            String query="SELECT ai_id, ai_desc, ai_identificador  FROM actoinseguro " +
                    " WHERE ai_id_padre ="+dataQuery1.get(i).get("ai_id")+" AND ai_status=1 ORDER BY ai_orden ";
            final ArrayList<HashMap<String, String>> dataQuery;
            dataQuery= Utils.exeLocalQuery(this, query);
            ArrayList secList = new ArrayList();
            for( int n = 0; n < dataQuery.size(); n++ ) {
                HashMap child = new HashMap();
                child.put("Item", ""+dataQuery.get(n).get("ai_identificador")+". " +dataQuery.get(n).get("ai_desc"));
                secList.add( child );
            }
            result.add( secList );
        }
        return result;
    }
    public void  onContentChanged  () {
        Log.i("UNS", "onContentChanged");
        super.onContentChanged();
    }
    /* This function is called on each child click */
    public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {
       Log.i("UNS", "Inside onChildClick at groupPosition = " + groupPosition + " Child clicked at position " + childPosition);
        String ai_id="";
        String titulo=parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();
        titulo=titulo.replaceAll("\\{","");
        titulo=titulo.replaceAll("\\}","");
        titulo=titulo.replaceAll("Item=","");
        String partTits[]=titulo.split("\\.");
        /**Se realiza la consulta a la base de datos */
        String queryAI_id="SELECT ai_id  FROM actoinseguro  " +
                " WHERE ai_identificador='"+partTits[0]+"' AND ai_status=1 ORDER BY ai_orden";
        final ArrayList<HashMap<String, String>> resultsAI;
        resultsAI= Utils.exeLocalQuery(this, queryAI_id);

        for(int idx=0;idx<resultsAI.size();idx++){
            ai_id=resultsAI.get(idx).get("ai_id");
        }
        /** ******************************* */
        /*Intent intent =
                new Intent(UnsafeActActivity.this, PeopleQActivity.class);
        startActivity(intent);*/
        String array[];
        String idsEmpl="", idsContra="";
        if(null!=preferences.getString("nums", "")) {
            if(preferences.getString("nums", "").length()>0){
                int size = preferences.getInt("employ_size", 0);
                array = new String[size];
                for (int i = 0;i<size;i++) {
                    array[i] = preferences.getString("employ_" + i, null);
                    idsEmpl=idsEmpl+","+array[i];
                }
            }
        }
        if(null!=preferences.getString("conts", "")) {
            if(preferences.getString("conts", "").length()>0){
                int size = preferences.getInt("conts_size", 0);
                array = new String[size];
                for (int i = 0;i<size;i++) {
                    array[i] = preferences.getString("conts_" + i, null);
                    idsContra=idsContra+","+array[i];
                }
            }
        }
        idsEmpl=idsEmpl.substring(1,idsEmpl.length());
        idsContra=idsContra.substring(1,idsContra.length());
        //Log.i("employers", "" + idsEmpl);
        Utils.setValueSP(UnsafeActActivity.this, "titulo", titulo);
        Utils.setValueSP(UnsafeActActivity.this, "ai_id", ai_id);
        String queryEmpCont="SELECT emp_id,emp_nombre|| ' ' ||emp_app|| ' ' ||emp_apm AS nombre,emp_num_emp "+
                " FROM empleado WHERE emp_num_emp IN ("+idsEmpl+")"+
                " UNION "+
                " SELECT con_id,con_nombre AS nombre, con_contacto FROM contratista WHERE con_id IN ("+idsContra+")";
        Intent intent =
                new Intent(UnsafeActActivity.this, PeopleQActivity.class);
        intent.putExtra("query",queryEmpCont);
        intent.putExtra("queryContrac","SELECT con_id,con_nombre, con_contacto FROM contratista WHERE con_id IN ("+idsEmpl+")");
        intent.putExtra("campo","nombre");
        intent.putExtra("clave","emp_num_emp");
        //intent.putExtra("ai_id",""+ai_id);
        startActivity(intent);
        finish();
        return true;
    }

    /* This function is called on expansion of the group */
    public void  onGroupExpand  (int groupPosition) {
        /*try{
            Log.i("UNS", "Group exapanding Listener => groupPosition = " + groupPosition);
        }catch(Exception e){
            Log.i("UNS", " groupPosition Errrr +++ " + e.getMessage());
        }*/
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
