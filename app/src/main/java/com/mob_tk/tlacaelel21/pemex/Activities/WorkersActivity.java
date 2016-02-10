package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.Adapter.WorkersAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.WorkersModel;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by tlacaelel21 on 27/10/15.
 */
public class WorkersActivity extends Activity {
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

        //searchWorkers("SELECT emp_nombre|| ' ' ||emp_app|| ' ' ||emp_apm AS nombre,emp_num_emp FROM empleado");
        populateWorkers("SELECT emp_nombre|| ' ' ||emp_app|| ' ' ||emp_apm AS nombre,emp_num_emp FROM empleado");

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    lista.setAdapter(null);
                    employments=null;
                    populateWorkers("SELECT emp_nombre|| ' ' ||emp_app|| ' ' ||emp_apm AS nombre,emp_num_emp FROM empleado WHERE nombre like('%" + search.getText() + "%')");
                    View view = WorkersActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
                num_employ= new String[selectedItems.size()];
                for (int i = 0; i < selectedItems.size(); i++) {
                    outputStrArr[i] = selectedItems.get(i);
                    String tmp[]=(selectedItems.get(i)).split("\n");
                    num_employ[i]=tmp[1];
                }
                SharedPreferences preferences;
                preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("nums", "" + selectedItems.size());
                editor.putInt("employ_size", num_employ.length);
                for(int i=0;i<num_employ.length;i++)
                    editor.putString("employ_" + i, num_employ[i]);
                editor.commit();

                Intent intent =
                        new Intent(WorkersActivity.this, AuditP2Activity.class);
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


    public String [][] searchWorkers(String query){
        String[][] work_centers;

        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this,query);
        work_centers=new String[results.size()][2];
        for(int i=0;i<results.size();i++){
            for(int j=0;j<2;j++){
                if(j==0)
                    work_centers[i][j]=results.get(i).get("nombre");
                else
                    work_centers[i][j]=results.get(i).get("emp_num_emp");
            }
        }
        List<WorkersModel> listaWP = new ArrayList<WorkersModel>();

        for (int i=0; i< work_centers.length; i++) {
            String nameWorker = "", numberWorker = "";
            nameWorker = work_centers[i][0];
            numberWorker = work_centers[i][1];
            WorkersModel objeto = new WorkersModel(nameWorker, numberWorker);
            listaWP.add(objeto);
        }

        WorkersAdapter workersAdapter= new WorkersAdapter(WorkersActivity.this,
                R.layout.list_workers,listaWP ,1);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setAdapter(workersAdapter);
        return work_centers;
    }

    public String [] populateWorkers(String query){
        String[] work_centers;
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this,query);
        work_centers=new String[results.size()];
        num_employ=new String[results.size()];
        for(int i=0;i<results.size();i++){
            for(int j=0;j<2;j++){
                if(j==0)
                    work_centers[i]=results.get(i).get("nombre")+"\n"+
                            results.get(i).get("emp_num_emp");
                num_employ[i]=results.get(i).get("emp_num_emp");
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

