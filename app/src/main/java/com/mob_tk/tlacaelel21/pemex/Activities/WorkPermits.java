package com.mob_tk.tlacaelel21.pemex.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mob_tk.tlacaelel21.pemex.Adapter.SeparatedListAdapter;
import com.mob_tk.tlacaelel21.pemex.R;
import com.mob_tk.tlacaelel21.pemex.Utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by tlacaelel21 on 15/02/16.
 */
public class WorkPermits extends Activity
{

    public final static String ITEM_TITLE = "title";
    public final static String ITEM_CAPTION = "caption";

    // SectionHeaders
    private final static String[] days = new String[]{"Enero", "Febrero", "Marzo", "Abril",
            "Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

    // Section Contents
    //private final static String[] valoresFebrero = new String[]{"Ate Breakfast", "Ran a Marathan ...yah really", "Slept all day"};
    String[] valoresFebrero,valoresMarzo,valoresAbril;

    // MENU - ListView
    private ListView addJournalEntryItem;

    // Adapter for ListView Contents
    private SeparatedListAdapter adapter;

    // ListView Contents
    private ListView journalListView;

    public Map<String, ?> createItem(String title, String caption)
    {
        Map<String, String> item = new HashMap<String, String>();
        item.put(ITEM_TITLE, title);
        item.put(ITEM_CAPTION, caption);
        return item;
    }

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // Sets the View Layer
        setContentView(R.layout.activity_work_permits);

        // Interactive Tools
        /*final ArrayAdapter<String> journalEntryAdapter = new ArrayAdapter<String>
                (this, R.layout.add_journalentry_menuitem, new String[]{"Add Journal Entry"});*/

        // AddJournalEntryItem
        //addJournalEntryItem = (ListView) this.findViewById(R.id.add_journalentry_menuitem);
        /*addJournalEntryItem.setAdapter(journalEntryAdapter);
        addJournalEntryItem.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
            {
                String item = journalEntryAdapter.getItem(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
            }
        });*/
        String query="SELECT prt_id,ctr_id,prt_fecha,prt_titulo,prt_status,prt_fecha_fin  FROM permisotrabajo";
        final ArrayList<HashMap<String, String>> results;
        results= Utils.exeLocalQuery(this, query);
        int ctaFeb=0;
        int ctaMzo=0;
        int ctaAbr=0;

        for(int i=0;i<results.size();i++){
            String mes=obtNombreMes(results.get(i).get("prt_fecha"));

            if( mes.trim().equals("Febrero")){
                ctaFeb++;
            }
        }
        for(int i=0;i<results.size();i++){
            String mes=obtNombreMes(results.get(i).get("prt_fecha"));

            if( mes.trim().equals("Marzo")){
                ctaMzo++;
            }
        }
        for(int i=0;i<results.size();i++){
            String mes=obtNombreMes(results.get(i).get("prt_fecha"));

            if( mes.trim().equals("Abril")){
                ctaAbr++;
            }
        }

        valoresFebrero=new String[ctaFeb];
        for(int i=0;i<results.size();i++){
            String mes=obtNombreMes(results.get(i).get("prt_fecha"));
            if(mes.trim().equals("Febrero"))
                for(int idx=0;idx<ctaFeb;idx++)
                    valoresFebrero[idx]=results.get(i).get("prt_titulo")+"             "+
                        results.get(i).get("prt_fecha_fin");
        }

        valoresMarzo=new String[ctaMzo];
        for(int i=0;i<results.size();i++){
            String mes=obtNombreMes(results.get(i).get("prt_fecha"));
            if(mes.trim().equals("Marzo"))
                for(int idx=0;idx<ctaMzo;idx++)
                valoresMarzo[idx]=results.get(i).get("prt_titulo")+"             "+
                        results.get(i).get("prt_fecha_fin");
        }

        valoresAbril=new String[ctaAbr];
        for(int i=0;i<results.size();i++){
            String mes=obtNombreMes(results.get(i).get("prt_fecha"));
            if(mes.trim().equals("Abril"))
                for(int idx=0;idx<ctaAbr;idx++)
                valoresAbril[idx]=results.get(i).get("prt_titulo")+"             "+
                        results.get(i).get("prt_fecha_fin");
        }


        // Create the ListView Adapter
        adapter = new SeparatedListAdapter(this);
        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, R.layout.list_item, valoresFebrero);
        ArrayAdapter<String> listadapterMzo = new ArrayAdapter<String>(this, R.layout.list_item, valoresMarzo);
        ArrayAdapter<String> listadapterAbr = new ArrayAdapter<String>(this, R.layout.list_item, valoresAbril);

        // Add Sections
        for (int i = 0; i < days.length; i++){
            if(i==1)
                adapter.addSection(days[1], listadapter);
            if(i==2)
                adapter.addSection(days[2], listadapterMzo);
            if(i==3)
                adapter.addSection(days[3], listadapterAbr);
        }

        // Get a reference to the ListView holder
        journalListView = (ListView) this.findViewById(R.id.list_journal);

        // Set the adapter on the ListView holder
        journalListView.setAdapter(adapter);

        // Listen for Click events
        journalListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
            {
                Log.i("TAM","ARR->"+results.size());
                Log.i("TAM","POS->"+position);
                Log.i("TAM", "ParPos->" + parent.getItemAtPosition(position));
                Log.i("TAM", "ItemrPos->" + parent.getSelectedItemPosition() );
                Intent intent =
                        new Intent(WorkPermits.this, WorkPermitActivity.class);
                startActivity(intent);
                finish();
                /*String item = results.get(position).get("prt_id");
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();*/
            }
        });
    }
    private String obtNombreMes(String fechaStr) {
        int mes = 1;
        // fechaYHORA = 2016-02-26 12:00:00 02-26-2016 12:00:00
        String[] fechaYHora = fechaStr.split(Pattern.quote(" "));
        // fecha = 2016-02-26  02-26-2016
        String[] fecha = fechaYHora[0].split(Pattern.quote("-"));
        mes = Integer.parseInt(fecha[0]);
        switch(mes) {
            case 1:
                return "Enero ";
            case 2:
                return "Febrero ";
            case 3:
                return "Marzo ";
            case 4:
                return "Abril ";
            case 5:
                return "Mayo ";
            case 6:
                return "Junio ";
            case 7:
                return "Julio ";
            case 8:
                return "Agosto ";
            case 9:
                return "Septiembre ";
            case 10:
                return "Octubre ";
            case 11:
                return "Noviembre ";
            case 12:
                return "Diciembre ";
            default:
                return "Enero ";
        }
    }

}
