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
 * Created by tlacaelel21 on 21/10/15.
 */
public class RecoveryPswActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_psw);

        Button recovery= (Button)findViewById(R.id.button_recovery_ac);
        //Button back= (Button)findViewById(R.id.button_recovery_back);

       recovery.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               Intent intent =
                       new Intent(RecoveryPswActivity.this, MenuActivity.class);
               startActivity(intent);
           }
       });
        /*back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent =
                        new Intent(RecoveryPswActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
  }
}
