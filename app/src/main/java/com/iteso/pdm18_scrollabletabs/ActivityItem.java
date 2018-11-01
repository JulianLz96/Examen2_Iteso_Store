package com.iteso.pdm18_scrollabletabs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.iteso.pdm18_scrollabletabs.beans.Category;
import com.iteso.pdm18_scrollabletabs.beans.ItemProduct;
import com.iteso.pdm18_scrollabletabs.beans.Store;
import com.iteso.pdm18_scrollabletabs.database.CategoryControl;
import com.iteso.pdm18_scrollabletabs.database.DataBaseHandler;
import com.iteso.pdm18_scrollabletabs.database.ItemProductControl;
import com.iteso.pdm18_scrollabletabs.database.StoreControl;

import java.util.ArrayList;

public class ActivityItem extends AppCompatActivity {
    StoreControl storeControl;
    CategoryControl categoryControl;
    DataBaseHandler dh;
    ItemProduct itemProduct;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        final Spinner spin_photo;
        final Spinner spin_store;
        final Spinner spin_category;
        final EditText item_name;
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
        final ArrayAdapter<Category> cats = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,categories);
        spin_category.setAdapter(cats);

        ArrayList<Store> Stores;
        Stores = storeControl.getStores(dh);
        ArrayAdapter<Store> stores = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,Stores);
        spin_store.setAdapter(stores);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item_name.getText().equals("")){
                    Toast.makeText(ActivityItem.this,"Insert Item Text", Toast.LENGTH_LONG).show();
                } else {

                    itemProduct.setImage((int) spin_photo.getSelectedItem());
                    itemProduct.setTitle(item_name.getText().toString());
                    itemProduct.setCategory((Category) spin_category.getSelectedItem());
                    itemProduct.setStore((Store) spin_store.getSelectedItem());

                    ItemProductControl itemProductControl = new ItemProductControl();
                    itemProductControl.addProduct(itemProduct, dh);
                }
            }
        });
    }
}
