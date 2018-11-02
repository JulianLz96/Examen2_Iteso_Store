package com.iteso.examen2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iteso.examen2.beans.Category;
import com.iteso.examen2.beans.Store;
import com.iteso.pdm18_scrollabletabs.R;
import com.iteso.examen2.beans.ItemProduct;
import com.iteso.examen2.database.CategoryControl;
import com.iteso.examen2.database.DataBaseHandler;
import com.iteso.examen2.database.ItemProductControl;
import com.iteso.examen2.database.StoreControl;

import java.util.ArrayList;

public class ActivityItem extends AppCompatActivity {
    StoreControl storeControl = new StoreControl();
    CategoryControl categoryControl = new CategoryControl();
    DataBaseHandler dh = DataBaseHandler.getInstance(ActivityItem.this);
    ItemProduct itemProduct = new ItemProduct();
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


        final ArrayList<Category> categories_obj;
        categories_obj = categoryControl.getCategory(dh);
        Log.e("Tech", "getCategory: " + categories_obj.get(0).getName());
        final ArrayList<String> categories = new ArrayList<>();
        categories.add(categories_obj.get(0).getName());
        categories.add(categories_obj.get(1).getName());
        categories.add(categories_obj.get(2).getName());
        //Log.e("Stores", categories.get(0).getName(), null);
        final ArrayAdapter<String> cats = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,categories);
        spin_category.setAdapter(cats);

        final ArrayList<Store> Stores_obj;
        Stores_obj = storeControl.getStores(dh);
        ArrayList<String> stores = new ArrayList<>();
        stores.add(Stores_obj.get(0).getName());
        stores.add(Stores_obj.get(1).getName());
        stores.add(Stores_obj.get(2).getName());
       final ArrayAdapter<String> storesa = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,stores);
        spin_store.setAdapter(storesa);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item_name.getText().toString().equals("")){
                    Toast.makeText(ActivityItem.this,"Insert Item Text", Toast.LENGTH_LONG).show();
                } else {
                int i;
                i = spin_category.getSelectedItemPosition();
                Log.e("Tech", Integer.toString(i) + categories_obj.get(0).toString());
                    itemProduct.setImage( spin_photo.getSelectedItemPosition());
                    itemProduct.setTitle(item_name.getText().toString());
                    itemProduct.setCategory(categories_obj.get(i));
                    itemProduct.setStore((Stores_obj.get(spin_store.getSelectedItemPosition())));
                    Log.e("Tech",(Stores_obj.get(spin_store.getSelectedItemPosition())).toString());

                    ItemProductControl itemProductControl = new ItemProductControl();
                    itemProductControl.addProduct(itemProduct, dh);
                    Log.e("Tech", "itemProdcut agregar: " + itemProduct.toString());
                    Intent intent = new Intent(ActivityItem.this, ActivityMain.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
