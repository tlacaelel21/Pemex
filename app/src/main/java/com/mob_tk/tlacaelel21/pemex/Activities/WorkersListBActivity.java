package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.mob_tk.tlacaelel21.pemex.Adapter.WorkersAdapter;
import com.mob_tk.tlacaelel21.pemex.Model.WorkersModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tlacaelel21 on 30/10/15.
 */
public class WorkersListBActivity extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workers_list_content);
        Context context=getApplicationContext();

        String[][] work_centers=new String[15][2];

        for(int i=0;i<15;i++){
            for(int j=0;j<2;j++){
                if(j==0)
                    work_centers[i][j]="Nombre trabajador ";
                else
                    work_centers[i][j]="9988776655-"+(i+1)+""+(i+3);
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

        ListView lista = (ListView) findViewById(R.id.list_workers_black);
        WorkersAdapter workersAdapter= new WorkersAdapter(context,
                R.layout.list_workers,listaWP,2 );
        lista.setAdapter(workersAdapter);
    }
}


