package com.iteso.pdm18_scrollabletabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iteso.pdm18_scrollabletabs.beans.City;
import com.iteso.pdm18_scrollabletabs.beans.Store;
import com.iteso.pdm18_scrollabletabs.database.DataBaseHandler;
import com.iteso.pdm18_scrollabletabs.database.StoreControl;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplashScreen extends AppCompatActivity {
    StoreControl storeControl;
    ArrayList<Store> stores = null;
    DataBaseHandler dh;

    Store st1;
    Store st2;
    Store st3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        City city1 = new City();
        city1.setId(0);
        city1.setName("Zapopan");

        City city2 = new City();
        city2.setId(1);
        city2.setName("Tlaquepaque");

        City city3 = new City();
        city3.setId(2);
        city3.setName("Guadalajara");

        st1.setId(0);
        st1.setName("Best Buy");
        st1.setPhone("12345678");
        st1.setThumbnail(0);
        st1.setCity(city1);

        st2.setId(1);
        st2.setName("Best Buy");
        st2.setPhone("87654321");
        st2.setThumbnail(0);
        st2.setCity(city2);

        st3.setId(0);
        st3.setName("Best Buy");
        st3.setPhone("23456098");
        st3.setThumbnail(0);
        st3.setCity(city3);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                stores = storeControl.getStores(dh);
                if (stores.size() == 0){
                    storeControl.addStore(st1, dh);
                    storeControl.addStore(st2,dh);
                    storeControl.addStore(st3,dh);
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityMain.class );
                    startActivity(intent);
                    finish();
                } else{
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityMain.class );
                    startActivity(intent);
                    finish();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }
}
