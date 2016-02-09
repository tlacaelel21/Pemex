package com.mob_tk.tlacaelel21.pemex.Utilities;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by tlacaelel21 on 20/10/15.
 */
public class Cifrado {
    private SecretKey key;
    private Cipher cipher;
    private String algoritmo = "AES";
    private int keysize = 16;

    public Cifrado(){
        addKey("2XEkqvcrbuc48cW");
        //addKey("7MYcEXgbg28mtQs");//DP

    }

    public void addKey(String value) {
        byte[] valuebytes = value.getBytes();
        key = new SecretKeySpec(Arrays.copyOf(valuebytes, keysize), algoritmo);
    }

    public String encriptar(String texto) {
        String value = "";
        try {
            cipher = Cipher.getInstance(algoritmo);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] textobytes = texto.getBytes();
            byte[] cipherbytes = cipher.doFinal(textobytes);
            //value = new BASE64Encoder().encode(cipherbytes);
            value = new String(Base64.encodeToString(cipherbytes, 0));
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.getMessage());
        } catch (NoSuchPaddingException ex) {
            System.err.println(ex.getMessage());
        } catch (InvalidKeyException ex) {
            System.err.println(ex.getMessage());
        } catch (IllegalBlockSizeException ex) {
            System.err.println(ex.getMessage());
        } catch (BadPaddingException ex) {
            System.err.println(ex.getMessage());
        }
        return value;
    }

    /**
     * Created by JoseRogelio on 17/08/2015.
     */
    /*public static class PonenteActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ponente);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            String idEvento = "1";

            com.vinidsl.navigationviewdemo.Tasks.PonentesTask task = new com.vinidsl.navigationviewdemo.Tasks.PonentesTask(this);
            task.execute(idEvento);

        }
    }*/
}

