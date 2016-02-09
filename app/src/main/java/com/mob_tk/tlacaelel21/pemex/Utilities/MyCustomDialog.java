package com.mob_tk.tlacaelel21.pemex.Utilities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mob_tk.tlacaelel21.pemex.R;

/**
 * Created by tlacaelel21 on 3/11/15.
 */

public class MyCustomDialog extends DialogFragment {
    Button mButton;
    EditText mEditText;
    public onSubmitListener mListener;
    public String text = "";

    public interface onSubmitListener {
        void setOnSubmitListener(String arg);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.DKGRAY));
        dialog.show();

        return dialog;
    }
}

