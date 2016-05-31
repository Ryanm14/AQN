package me.ryanmiles.aqn.events.updates;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import me.ryanmiles.aqn.data.Data;

/**
 * Created by Ryan Miles on 5/27/2016.
 */
public class MainActivityDialog {

    public MainActivityDialog() {

    }

    public Dialog getDialog(Context context) {
        return new AlertDialogWrapper.Builder(context)
                .setTitle("Abandoned Mine")
                .setCancelable(false)
                .setMessage("Congratulations on coming back alive. \n\nYou found: \n\nTin:9\nWood:45\nStone:27")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Data.COPPER.addAmount(9);
                        Data.WOOD.addAmount(45);
                        Data.STONE.addAmount(27);
                    }
                }).show();
    }
}
