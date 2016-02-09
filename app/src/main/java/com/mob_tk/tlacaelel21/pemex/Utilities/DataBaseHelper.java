package com.mob_tk.tlacaelel21.pemex.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tlacaelel21 on 13/01/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private String DB_PATH;// = "/data/data/st.android.chevez/databases/";
    private Context mycontext;

    //private String DB_PATH = mycontext.getApplicationContext().getPackageName()+"/databases/";
    private static String DB_NAME = "pemex.sqlite";//the extension may be .sqlite or .db
    public SQLiteDatabase myDataBase;
	/*private String DB_PATH = "/data/data/"
                        + mycontext.getApplicationContext().getPackageName()
                        + "/databases/";*/

    public DataBaseHelper(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.mycontext=context;
        DB_PATH = mycontext.getDatabasePath(DB_NAME).toString();
    }

    public void initializedatabase(){
        if (!checkdatabase()) {
            Log.i("Database doesn't exist", "Creando DB");
            createdatabase();
        }
    }

    private void createdatabase(){
        getReadableDatabase();
        try {
            copydatabase();
        } catch(IOException e) {
            Log.i("EX", e.getMessage().toString());
            e.printStackTrace();
            throw new Error("Error copying database");
        }
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH;// + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
            dbfile = null;
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        OutputStream myoutput = new FileOutputStream(DB_PATH);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public Cursor getQuery(String query){
        Cursor cursor=myDataBase.rawQuery(query, null);
        /*if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(0);
                nombre = cursor.getString(1);
            } while(cursor.moveToNext());
        }*/
        return cursor;
    }
    @Override
    public void onCreate(SQLiteDatabase arg0) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

}
