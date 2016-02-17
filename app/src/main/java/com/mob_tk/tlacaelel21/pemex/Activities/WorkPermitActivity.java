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
import android.widget.EditText;
import android.widget.ImageButton;

import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

/**
 * Created by tlacaelel21 on 16/02/16.
 */
public class WorkPermitActivity extends Activity {
    SharedPreferences preferences;
    EditText eTProc,eTFor,etFor,etAcc;
    Button btnCancel, btnCont;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_per_detail);
        //imageButtonClose=(ImageButton) findViewById(R.id.image_close);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        btnCancel=(Button)findViewById(R.id.btn_cancelar);
        btnCont=(Button)findViewById(R.id.btn_continuar);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(WorkPermitActivity.this, WorkPermits.class);
                startActivity(intent);
                finish();
            }
        });

        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                //variables prim pantalla
                editor.putString("worc_center", "");
                editor.putString("departament", "");
                editor.putString("tipoauditoria", "");
                editor.putString("region", "");
                editor.putString("area", "");
                editor.putString("dep_id", "");
                editor.putString("reg_id","");
                editor.putString("dep_id","");
                editor.putString("audit_date","");
                editor.putString("audit_week","");
                //variables seg pantalla
                editor.putString("instalaciones","");
                editor.putString("nums","");
                editor.putString("conts","");
                editor.putString("jefes","");
                editor.putString("actividad", "");
                editor.putString("employ_size","");
                editor.putString("conts_size","");
                editor.clear();
                editor.commit();
                clearCalif();

                Intent intent =
                        new Intent(WorkPermitActivity.this, AuditP1Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /** Agregando a la BDLocal las calificaciones*/
    public void clearCalif(){
        String localQuery = "DELETE FROM calificaciones";
        try {
            Log.i("CLEAR-->", "" + localQuery);
            Utils.exeLocalInsQuery(getBaseContext(), localQuery);
        } catch(Exception E) {

        }
    }
}
