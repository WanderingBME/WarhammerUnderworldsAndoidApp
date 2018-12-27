package com.wanderingbme.warhammerunderworldsdeckbuilder;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.wanderingbme.warhammerunderworldsdeckbuilder.dao.WarbandDao;
import com.wanderingbme.warhammerunderworldsdeckbuilder.database.AppDatabase;
import com.wanderingbme.warhammerunderworldsdeckbuilder.model.Warband;

import java.util.Comparator;
import java.util.List;

public class ChooseWarbandActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        setContentView(R.layout.activity_choose_warband);
        GridLayout layout = findViewById(R.id.main_layout);
        layout.setMinimumWidth(screenWidth);
        final WarbandDao warbandDao = db.warbandDao();
        List<Warband> warbands = warbandDao.getAll();
        warbands.sort(new Comparator<Warband>() {
            @Override
            public int compare(Warband o1, Warband o2) {
                if ((o1.getReleased() && o2.getReleased()) || (!o1.getReleased() && !o2.getReleased())) {
                    String name1 = o1.getName();
                    String name2 = o2.getName();
                    if (o1.getName().indexOf("The ") == 0) {
                        name1 = o1.getName().substring(4);
                    }
                    if (o2.getName().indexOf("The ") == 0) {
                        name2 = o2.getName().substring(4);
                    }
                    return name1.compareTo(name2);
                } else if (o1.getReleased()) {
                    return -1;
                } else if (o2.getReleased()) {
                    return 1;
                }
                throw new IllegalArgumentException();
            }
        });
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(layout.getLayoutParams());
        for(int i = 0; i < warbands.size(); i++) {
            Warband warband = warbands.get(i);
            Button btn = new Button(this);
            if (warband.getReleased()) {
                btn.setText(warband.getName());
            } else {
                btn.setText(String.format("%s (Unreleased)", warband.getName()));
            }
            if (warband.getIcon() != null) {
                Drawable icon = ContextCompat.getDrawable(this, getResources().getIdentifier(warband.getIcon(), "drawable", this.getPackageName()));
                BitmapDrawable test = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap((((BitmapDrawable) icon).getBitmap()), 150, 150, false));
                btn.setCompoundDrawablesWithIntrinsicBounds(null, test, null, null);
            }
            btn.setId(warband.getId());
            btn.setWidth(Math.round((float) screenWidth / 2) - 16);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buildWarband(warbandDao.getWarbandById(view.getId()));
                }
            });
            layout.addView(btn);
        }
    }

    public void buildWarband(Warband warband) {
        Intent intent = new Intent(this, BuildWarband.class);
        intent.putExtra("warband", warband);
        startActivity(intent);
    }
}
