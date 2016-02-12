package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;


import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

/**
 * Created by tlacaelel21 on 11/02/16.
 */
public class ProcedureActionActivity extends Activity {
    ImageButton imageButtonClose;
    SharedPreferences preferences;
    EditText eTProc,eTFor,etFor,etAcc;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proced_ac);
        imageButtonClose=(ImageButton) findViewById(R.id.image_close);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);

        eTProc=(EditText) findViewById(R.id.eTPro);
        eTFor=(EditText) findViewById(R.id.eTFor);
        etAcc=(EditText) findViewById(R.id.eTAcc);



        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setValueSP(ProcedureActionActivity.this, "pa_proc", eTProc.getText().toString());
                Utils.setValueSP(ProcedureActionActivity.this, "pa_for", eTFor.getText().toString());
                Utils.setValueSP(ProcedureActionActivity.this, "pa_acc", etAcc.getText().toString());
                Intent intent =
                        new Intent(ProcedureActionActivity.this, AuditSummary.class);
                startActivity(intent);
                finish();
            }

        });


    }
}



