package com.mob_tk.tlacaelel21.pemex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.mob_tk.tlacaelel21.pemex.Model.AreasModel;
import com.mob_tk.tlacaelel21.pemex.R;

import java.util.List;

/**
 * Created by tlacaelel21 on 26/10/15.
 */
public class AreasAdapter extends ArrayAdapter<AreasModel> {
    AQuery aquery;

    public AreasAdapter(Context context, int resource, List<AreasModel> items) {
        super(context, resource,items);
        aquery = new AQuery(context);
    }

    public static class ViewHolder {
        TextView areaTitulo;
        TextView areaDato;

        public ViewHolder(View v) {
            areaTitulo = (TextView) v.findViewById(R.id.list_area_titulo);
            areaDato = (TextView) v.findViewById(R.id.list_area_dato);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_areas, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        AreasModel p=getItem(position);

        if (p != null) {
            holder.areaTitulo.setText(p.getAreaTitulo());
            holder.areaDato.setText(p.getAreaDato());
        }

        return v;
    }

}
