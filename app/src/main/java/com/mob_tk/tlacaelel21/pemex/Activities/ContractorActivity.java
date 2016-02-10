package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mob_tk.tlacaelel21.pemex.Adapter.WorkersAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.WorkersModel;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tlacaelel21 on 26/01/16.
 */
public class ContractorActivity extends Activity {
    EditText search;
    ListView lista;
    ImageButton close;
    ArrayAdapter<String> adapter;
    Button btnAddW;
    String[] employments ;
    String[] num_employ ;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workers_content);
        Context context=getApplicationContext();
        search=(EditText) findViewById(R.id.txt_busca_wc);
        lista = (ListView) findViewById(R.id.list_workers);
        close=(ImageButton) findViewById(R.id.image_close);
        btnAddW=(Button) findViewById(R.id.btn_add_workers);

        populateWorkers("SELECT con_id, con_nombre,con_contacto  FROM contratista WHERE con_status=1");

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    lista.setAdapter(null);
                    employments = null;
                    populateWorkers("SELECT con_id, con_nombre,con_contacto FROM contratista WHERE con_status=1 WHERE con_nombre like('%" + search.getText() + "%')");
                    View view = ContractorActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /** ********************************************* */
                SparseBooleanArray checked = lista.getCheckedItemPositions();
                ArrayList<String> selectedItems = new ArrayList<String>();
                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(adapter.getItem(position));
                }

                String[] outputStrArr = new String[selectedItems.size()];
                num_employ = new String[selectedItems.size()];
                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                    String tmp[] = (selectedItems.get(i)).split("\n");
                    num_employ[i] = tmp[1];
                }

                SharedPreferences preferences;
                preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("conts", "" + selectedItems.size());
                editor.putInt("conts_size", num_employ.length);
                for(int i=0;i<num_employ.length;i++)
                    editor.putString("conts_" + i, num_employ[i]);
                editor.commit();

                Intent intent =
                        new Intent(ContractorActivity.this, AuditP2Activity.class);
                startActivity(intent);
                finish();
                /** ****************************************************/
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

    public String[] populateWorkers(String query){
        String[] work_centers;

        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this,query);
        work_centers=new String[results.size()];
        num_employ=new String[results.size()];
        for(int i=0;i<results.size();i++){
            for(int j=0;j<2;j++){
                if(j==0)
                    work_centers[i]=results.get(i).get("con_nombre")+"\n"+
                            results.get(i).get("con_contacto");
                num_employ[i]=results.get(i).get("con_id");
            }
        }
        employments=work_centers;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, employments);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setAdapter(adapter);
        return work_centers;
    }
}

