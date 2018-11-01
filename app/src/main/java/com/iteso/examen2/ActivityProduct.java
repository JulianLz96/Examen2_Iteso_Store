package com.iteso.examen2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.iteso.examen2.beans.Store;
import com.iteso.pdm18_scrollabletabs.R;
import com.iteso.examen2.beans.ItemProduct;
import com.iteso.examen2.tools.Constant;

public class ActivityProduct extends AppCompatActivity {

    EditText title, location, name, phone;
    ImageView image;
    Button save, cancel;
    ItemProduct itemProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        image = findViewById(R.id.activity_product_image);
        title = findViewById(R.id.activity_product_title);
        location = findViewById(R.id.activity_product_location);
        name = findViewById(R.id.activity_product_store);
        phone = findViewById(R.id.activity_product_phone);
        save = findViewById(R.id.activity_product_save);
        cancel = findViewById(R.id.activity_product_cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Store store = new Store();

                store.setName(name.getText().toString());
                itemProduct.setTitle(title.getText().toString());
                itemProduct.setLocation(location.getText().toString());
                itemProduct.setStore(store);
                itemProduct.setPhone(phone.getText().toString());
                Intent intent = new Intent();
                intent.putExtra(Constant.EXTRA_PRODUCT, itemProduct);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        if (getIntent().getExtras() != null) {
            itemProduct = getIntent().getParcelableExtra(Constant.EXTRA_PRODUCT);
            if (itemProduct != null) {

                title.setText(itemProduct.getTitle());
                location.setText(itemProduct.getLocation());
                name.setText(itemProduct.getStore().getName());
                phone.setText(itemProduct.getPhone());
                switch (itemProduct.getImage()) {
                    case Constant.TYPE_MAC:
                        image.setImageResource(R.drawable.mac);
                        break;
                    case Constant.TYPE_ALIENWARE:
                        image.setImageResource(R.drawable.alienware);
                        break;
                    case Constant.TYPE_SHEETS:
                        image.setImageResource(R.drawable.sheets);
                        break;
                    case Constant.TYPE_PILLOW:
                        image.setImageResource(R.drawable.pillows);
                        break;
                    case Constant.TYPE_REFRIGERATOR:
                        image.setImageResource(R.drawable.refrigerator);
                        break;
                    case Constant.TYPE_MICRO:
                        image.setImageResource(R.drawable.micro);
                        break;
                }
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                image.setImageBitmap(bitmap);
            }
        }

    }
}
