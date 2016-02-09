package com.mob_tk.tlacaelel21.pemex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mob_tk.tlacaelel21.pemex.Model.AreasModel;
import com.mob_tk.tlacaelel21.pemex.Model.WorkersModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 27/10/15.
 */
public class WorkersAdapter extends ArrayAdapter<WorkersModel> {

    AQuery aquery;
    static int tipo;

    public WorkersAdapter(Context context, int resource, List<WorkersModel> items, int tipo) {
        super(context, resource,items);
        aquery = new AQuery(context);
        this.tipo=tipo;
    }
    public static class ViewHolder {
        TextView workerName;
        TextView workerNumber;
         CheckBox workerChk;

        public ViewHolder(View v) {
            workerName = (TextView) v.findViewById(R.id.list_worker_name);
            workerNumber = (TextView) v.findViewById(R.id.list_worker_number);
            if(tipo==1)
                workerChk=(CheckBox)v.findViewById(R.id.chk_worker);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_workers, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        WorkersModel p=getItem(position);

        if (p != null) {
            holder.workerName.setText(p.getWorkerName());
            holder.workerNumber.setText(p.getWorkerNumber());
            if(tipo==1)
              holder.workerChk.setVisibility(v.VISIBLE);
        }

        return v;
    }
}
