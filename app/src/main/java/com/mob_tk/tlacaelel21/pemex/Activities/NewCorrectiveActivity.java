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
 * Created by tlacaelel21 on 12/02/16.
 */
public class NewCorrectiveActivity extends Activity {
    ImageButton imageButtonClose;
    SharedPreferences preferences;
    EditText eTProc,eTFor,etFor,etAcc;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_corrective);
        imageButtonClose=(ImageButton) findViewById(R.id.image_close);
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);

        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(NewCorrectiveActivity.this, AuditSummary.class);
                startActivity(intent);
                finish();
            }

        });
    }
}
