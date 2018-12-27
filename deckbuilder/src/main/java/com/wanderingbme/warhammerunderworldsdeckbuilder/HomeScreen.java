package com.wanderingbme.warhammerunderworldsdeckbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wanderingbme.warhammerunderworldsdeckbuilder.database.AppDatabase;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        AppDatabase.getInstance(getApplicationContext());
        // TODO: Add Load Warband option
    }

    public void newWarband(View view) {
        Intent intent = new Intent(this, ChooseWarbandActivity.class);
        startActivity(intent);
    }

    public void viewLibrary(View view) {
        Intent intent = new Intent(this, LibraryActivity.class);
        startActivity(intent);
    }
}
