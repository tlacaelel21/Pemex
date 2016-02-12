package com.mob_tk.tlacaelel21.pemex.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mob_tk.tlacaelel21.pemex.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tlacaelel21 on 13/01/16.
 */
public class Utils {
    public String converter(String dateToConvert){
        String formatedDate="";
        Long fechaLlega=Long.parseLong(dateToConvert);
        try{
            SimpleDateFormat originalFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
            Date date = new Date(fechaLlega);
            formatedDate = originalFormat.format(date);
        }catch (Exception e){}
        return  formatedDate;
    }


    public static ArrayList<HashMap<String, String>> exeLocalQuery (Context context, String query) {
        DataBaseHelper usdbh = null;
        ArrayList<HashMap<String, String>> arrayResult = new ArrayList<HashMap<String,String>>();
        try {
            usdbh = new DataBaseHelper(context);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            HashMap<String, String> data = null;
            String[] column_names = null;
            // If db connection was open right
            if(db != null) {
                Cursor cursor = db.rawQuery(query, null);
                if(cursor.moveToFirst()) {
                    do {
                        column_names = cursor.getColumnNames();
                        data = new HashMap<String, String>();
                        for(int i = 0; i < column_names.length; i++) {
                            data.put(cursor.getColumnName(i), ""+cursor.getString(i));
                        }
                        arrayResult.add(data);
                    } while(cursor.moveToNext());
                } else {
                    Log.i("Database error", "No hay resultados");
                }
                cursor.close();
                db.close();
            } else {
                Log.i("Database error", "No se abrio base de datos");
            }
        } catch (Exception e) {
            Log.e("---QRY LOCAL", query);
            e.printStackTrace();
        }
        return arrayResult;
    }
    public static boolean exeLocalInsQuery (Context context, String query) {
        DataBaseHelper usdbh = null;
        boolean result;
        try {
            usdbh = new DataBaseHelper(context);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            HashMap<String, String> data = null;
            String[] column_names = null;
            // If db connection was open right
            if(db != null) {
                db.execSQL(query);
                db.close();
                Log.i("Database", "Dato insertado");
                return true;
            } else {
                Log.i("Database error", "No se abrio base de datos");
                return false;
            }
        } catch (Exception e) {
            Log.e("---QRY LOCAL", query);
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<HashMap<String, String>> populateList(String JsonStr, List campos)
            throws JSONException {
        ArrayList<HashMap<String, String>> arrayResult = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> data = null;

        try {

            JSONArray mainArray = new JSONArray(JsonStr);
            //Utils converterDate = new Utils();

            for (int i = 0; i < mainArray.length(); i++) {
                JSONObject node = mainArray.getJSONObject(i);
                //String fecha = node.getString(campos.get(0).toString());
                String tabla = node.getString(campos.get(1).toString());
                data = new HashMap<String, String>();
                for(int idx=0; idx<campos.size();idx++) {
                    data.put( campos.get(idx).toString(), node.getString(campos.get(idx).toString()).replaceAll("'","") );
                }
                //data.put(campos.get(1).toString(), "" + tabla);
                arrayResult.add(data);
                /*Log.i("FECHA", fecha);
                Log.i("TABLA", "" + tabla);*/
            }

        } catch (JSONException e) {
            //Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return arrayResult;
    }

    public static ArrayList<HashMap<String, String>> exeRemoteQuery (Context mContext, String idServicio, List campos) {
        Log.i("IDSERV--->",idServicio);
        ArrayList<HashMap<String, String>> results = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        try {
            Cifrado c = new Cifrado();
            // Configurando parametros de conexión
            final String BASE_URL =
                    mContext.getString(R.string.base_url);
            final String QUERY_PARAM = "cod";
            String parametro = c.encriptar(idServicio);
            parametro = parametro.replaceAll("\\+", "%2B");
            parametro = parametro.replaceAll("\n+", "");

            String builtUri = BASE_URL + "" + parametro;
            Log.i("SERV", "" + builtUri);
            // Inicializando conexión
            URL url = new URL(builtUri.toString());
            // Estableciendo parametros de petición
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            // Conectando al servicio
            urlConnection.connect();

            // Leer respuesta de servidor
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            // conversion del JSON a una lista de objetos
            forecastJsonStr = buffer.toString();
            results=populateList(forecastJsonStr,campos);

        } catch (IOException e) {
            //Log.e(LOG_TAG, "Error ", e);
            return null;
        } catch (JSONException e) {
            //Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return results;
    }

    public static ArrayList<HashMap<String, String>> exeUncertaintyLocalQuery
            (Context context, String query) throws Exception {
        DataBaseHelper usdbh = null;
        Log.e("---QRY LOCAL", query);
        ArrayList<HashMap<String, String>> arrayResult = new ArrayList<HashMap<String,String>>();
        usdbh = new DataBaseHelper(context);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        HashMap<String, String> data = null;
        String[] column_names = null;
        // If db connection was open right
        if(db != null) {
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()) {
                do {
                    column_names = cursor.getColumnNames();
                    data = new HashMap<String, String>();
                    for(int i = 0; i < column_names.length; i++) {
                        data.put(cursor.getColumnName(i), ""+cursor.getString(i));
                    }
                    arrayResult.add(data);
                } while(cursor.moveToNext());
            } else {
                Log.i("Database error", "No hay resultados");
            }
            cursor.close();
            db.close();
        } else {
            Log.i("Database error", "No se abrio base de datos");
        }
        return arrayResult;
    }

    public static void setValueSP(Activity activity, String key, String value){
        SharedPreferences preferences;
        preferences = activity.getSharedPreferences("pemex_prefs", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
