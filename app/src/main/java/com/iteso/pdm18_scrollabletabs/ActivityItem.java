package com.iteso.pdm18_scrollabletabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.iteso.pdm18_scrollabletabs.beans.Category;
import com.iteso.pdm18_scrollabletabs.beans.Store;
import com.iteso.pdm18_scrollabletabs.database.CategoryControl;
import com.iteso.pdm18_scrollabletabs.database.DataBaseHandler;
import com.iteso.pdm18_scrollabletabs.database.StoreControl;

import java.util.ArrayList;

public class ActivityItem extends AppCompatActivity {
    StoreControl storeControl;
    CategoryControl categoryControl;
    DataBaseHandler dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Spinner spin_photo;
        Spinner spin_store;
        Spinner spin_category;
        EditText item_name;
        Button save;

        spin_photo = findViewById(R.id.item_photo_spinner);
        spin_store = findViewById(R.id.item_store_spinner);
        spin_category= findViewById(R.id.item_category_spinner);
        item_name = findViewById(R.id.item_title_text);
        save = findViewById(R.id.item_save_new);

        ArrayAdapter<CharSequence> photos = ArrayAdapter.createFromResource(this, R.array.item_photos,
                android.R.layout.simple_spinner_item);
            photos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin_photo.setAdapter(photos);


        ArrayList<Category> categories;
        categories = categoryControl.getCategory(dh);
        ArrayAdapter<Category> cats = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,categories);
        spin_category.setAdapter(cats);

        ArrayList<Store> Stores;
        Stores = storeControl.getStores(dh);
        ArrayAdapter<Store> stores = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,Stores);
        spin_store.setAdapter(stores);


    }
}
