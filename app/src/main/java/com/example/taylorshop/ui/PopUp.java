package com.example.taylorshop.ui;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.taylorshop.interfaces.PopupCallback;

public class PopUp{
    private static PopUp   objLogger;
    private PopUp(){
        //ToDo here
    }

    public void myDialog(
            Context context,
            PopupCallback customerView,
            String title,
            String message,
            int position) {
        int icon = -1;
        if (title.contains("Are you sure you want to update..")) {
            icon = R.drawable.checkbox_on_background;
        } else {
            icon = R.drawable.ic_dialog_alert;
        }

        new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                customerView.onClick(position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(icon)
                        .show();

    }

    public static PopUp getInstance()
    {
        if (objLogger == null)
        {
            objLogger = new PopUp();
        }
        return objLogger;
    }

}
