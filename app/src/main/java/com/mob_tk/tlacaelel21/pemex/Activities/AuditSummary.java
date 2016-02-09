package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mob_tk.tlacaelel21.pemex.R;

/**
 * Created by tlacaelel21 on 30/10/15.
 */
public class AuditSummary extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_summary);
        Button aButton = (Button)findViewById(R.id.boton_employ);
        Button btnRecCom = (Button)findViewById(R.id.boton_recom_com);
        Button btnSend = (Button)findViewById(R.id.btn_summary_send);


        aButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, WorkersListBActivity.class);
                startActivity(intent);
            }
        });
        btnRecCom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, WorkersListBActivity.class);
                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(AuditSummary.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}