package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mob_tk.tlacaelel21.pemex.R;

/**
 * Created by tlacaelel21 on 27/10/15.
 */
public class AuditP2Activity extends Activity implements View.OnClickListener {
    SharedPreferences preferences;
    int numEmp=0, numCon=0;
    String numEmpls[];
    String numContra[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_p2);

        
        Button btnWorkers=(Button) findViewById(R.id.btn_workers);
        Button btnContinue=(Button) findViewById(R.id.btn_audit_p2_continue);
        Button btnInst=(Button) findViewById(R.id.btn_instal);
        Button btnContr=(Button) findViewById(R.id.btn_contractor);
        Button btnBoss=(Button) findViewById(R.id.btn_boss);


        //Button btnWorkers=(Button) findViewById(R.id.btn_workers);
        /*Button btnAreas=(Button) findViewById(R.id.boton_area);*/

        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        if(null!=preferences.getString("instalaciones", "")) {
            if(preferences.getString("instalaciones", "").length()>0){
                btnInst.setText(preferences.getString("instalaciones", ""));
            }
        }

        if(null!=preferences.getString("nums", "")) {
            if(preferences.getString("nums", "").length()>0){
                /*int size = preferences.getInt("employ_size", 0);
                String array[] = new String[size];
                for (int i = 0;i<size;i++) {
                    array[i] = preferences.getString("employ_" + i, null);
                    Log.i("employ",""+array[i]);
                }*/
                btnWorkers.setText("Trabajadores("+preferences.getString("nums", "")+")");
            }
        }

        if(null!=preferences.getString("conts", "")) {
            if(preferences.getString("conts", "").length()>0){
                btnContr.setText("Contratistas("+preferences.getString("conts", "")+")");
            }
        }
        if(null!=preferences.getString("jefes", "")) {
            if(preferences.getString("jefes", "").length()>0){
                btnBoss.setText(preferences.getString("jefes", ""));
            }
        }



        /*Bundle extras = getIntent().getExtras();
        if(extras != null){
            numEmpls=extras.getStringArray("numEmpl");
            if(numEmpls!=null)
                numEmp=numEmpls.length;
            numContra=extras.getStringArray("numCont");
            if(numContra!=null)
                numCon=numContra.length;
        }
        if(numEmp>0){
            btnWorkers.setText("Trabajadores("+numEmp+")");
        }
        if(numCon>0){
            btnContr.setText("Contratistas("+numCon+")");
        }*/

        btnInst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditP2Activity.this, GeneralContainerActivity.class);
                intent.putExtra("query","SELECT inst_id, inst_desc FROM instalacion WHERE ctr_id=18");
                intent.putExtra("titulo","Instalaciones");
                intent.putExtra("campo","inst_desc");
                intent.putExtra("clave","inst_id");
                intent.putExtra("tipo","instalaciones");
                intent.putExtra("procede","2");
                startActivity(intent);
                finish();
            }
        });
        //Trabajadores
        btnWorkers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditP2Activity.this, WorkersActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Contratistas
        btnContr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditP2Activity.this, ContractorActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnBoss.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Log.i("BOTON","ENTRO AL BOSS");
                Intent intent =
                        new Intent(AuditP2Activity.this, GeneralContainerActivity.class);
                intent.putExtra("query","SELECT emp_nombre|| ' ' ||emp_app|| ' ' ||emp_apm AS nombre,emp_num_emp FROM empleado WHERE emp_puesto like ('%jefe%')");
                intent.putExtra("titulo","Jefes");
                intent.putExtra("campo","nombre");
                intent.putExtra("clave","emp_num_emp");
                intent.putExtra("tipo","jefes");
                intent.putExtra("procede","2");
                startActivity(intent);
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditP2Activity.this, UnsafeActActivity.class);
                startActivity(intent);
                finish();
            }
        });





        SharedPreferences preferences;
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        //if(null!=preferences.getString("audit_date", "")) {
            //if(preferences.getString("audit_date", "").length()>0){
                Log.i("FECHA",""+preferences.getString("audit_date", ""));
                Log.i("SEMANA",""+preferences.getString("audit_week", ""));
            //}
        //}
    }
    @Override
    public void onClick(View v) {
        showDialog(0);
    }
}
