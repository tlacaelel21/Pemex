package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.mob_tk.tlacaelel21.pemex.Adapter.DepartamentsAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.DepartamentsModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class AreasActivity extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_content);
        Context context=getApplicationContext();


        String[][] work_centers=new String[10][2];

        for(int i=0;i<10;i++){
            for(int j=0;j<2;j++){
                if(j==0)
                    work_centers[i][j]="Opción de area "+(i+1);
                else
                    work_centers[i][j]="Dato area relevante";
            }
        }

        List<DepartamentsModel> listaWP = new ArrayList<DepartamentsModel>();

        for (int i=0; i< work_centers.length; i++) {
            String tituloCampo = "", datoRelevante = "";
            tituloCampo = work_centers[i][0];
            datoRelevante = work_centers[i][1];
            DepartamentsModel objeto = new DepartamentsModel(tituloCampo, datoRelevante);
            listaWP.add(objeto);
        }

        ListView lista = (ListView) findViewById(R.id.list_areas);
        DepartamentsAdapter workCenterAdapter= new DepartamentsAdapter(context,
                R.layout.list_areas,listaWP );
        lista.setAdapter(workCenterAdapter);



    }


}
