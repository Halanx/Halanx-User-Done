package com.halanx.tript.userapp.Activities;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.halanx.tript.userapp.R;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You have no favorites!");
        builder.setMessage("Products added to favorites will be displayed here");
        builder.setCancelable(false);
        builder.setNegativeButton("Go back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dial = builder.create();
        dial.show();

    }
}
