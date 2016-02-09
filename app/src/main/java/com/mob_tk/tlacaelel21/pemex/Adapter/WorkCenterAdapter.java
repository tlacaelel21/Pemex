package com.mob_tk.tlacaelel21.pemex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mob_tk.tlacaelel21.pemex.Model.WorkCenterModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class WorkCenterAdapter extends ArrayAdapter<WorkCenterModel>{
    AQuery aquery;

    public WorkCenterAdapter(Context context, int resource,List<WorkCenterModel> items) {
        super(context, resource,items);
        aquery = new AQuery(context);
    }

    public static class ViewHolder {
        TextView nombreWC;
        TextView ubicacionWC;
        TextView distanciaWc;

        public ViewHolder(View v) {
            nombreWC = (TextView) v.findViewById(R.id.list_wc_titulo);
            ubicacionWC = (TextView) v.findViewById(R.id.list_wc_ubicacion);
            distanciaWc = (TextView) v.findViewById(R.id.list_wc_distancia);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_workplace, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        WorkCenterModel p=getItem(position);

        if (p != null) {
            //final String urlLink=p.getUrlExterno();
            holder.nombreWC.setText(p.getNombreWC());
            holder.ubicacionWC.setText(p.getUbicacionWC());
            holder.distanciaWc.setText(p.getDistanciaWc());
        }
        return v;
    }
}
