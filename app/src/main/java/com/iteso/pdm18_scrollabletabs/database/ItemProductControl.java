package com.iteso.pdm18_scrollabletabs.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ProgressBar;

import com.iteso.pdm18_scrollabletabs.beans.ItemProduct;

public class ItemProductControl {

    public void addProduct(ItemProduct itemProduct, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.PRODUCT_TITLE, itemProduct.getTitle());
        values.put(DataBaseHandler.PRODUCT_IMAGE, itemProduct.getImage());
        values.put(DataBaseHandler.PRODUCT_ID_CATEGORY, itemProduct.getCategory().getId());

        db.insert(DataBaseHandler.TABLE_CATEGORY, null, values);

        try {
            db.close();
        } catch (Exception e) {

        }
    }

}
