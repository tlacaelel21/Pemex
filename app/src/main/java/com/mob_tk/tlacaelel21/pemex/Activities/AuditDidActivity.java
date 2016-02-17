package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.mob_tk.tlacaelel21.pemex.R;

/**
 * Created by tlacaelel21 on 17/02/16.
 */
public class AuditDidActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences preferences;
        preferences = getSharedPreferences("pemex_prefs", MODE_PRIVATE);
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_realizadas);
    }
}
