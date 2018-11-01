package com.iteso.examen2.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.examen2.beans.Category;
import com.iteso.examen2.beans.ItemProduct;

import java.util.ArrayList;

public class ItemProductControl {

    public void addProduct(ItemProduct itemProduct, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.PRODUCT_TITLE, itemProduct.getTitle());
        values.put(DataBaseHandler.PRODUCT_IMAGE, itemProduct.getImage());
        values.put(DataBaseHandler.PRODUCT_ID_CATEGORY, itemProduct.getCategory().getId());

        db.insert(DataBaseHandler.TABLE_PRODUCT, null, values);

        try {
            db.close();
        } catch (Exception e) {

        }
    }


   public ArrayList<ItemProduct> getItemProductsByCategory(int idCategory, DataBaseHandler dh){
        ArrayList<ItemProduct> items = new ArrayList<>();
        SQLiteDatabase db = dh.getReadableDatabase();
       Category cat = new Category();
        String select = "SELECT " +
                DataBaseHandler.PRODUCT_TITLE +
                "," + DataBaseHandler.PRODUCT_IMAGE +
                "," + DataBaseHandler.PRODUCT_ID_CATEGORY
                + " FROM " + DataBaseHandler.TABLE_PRODUCT +
                " WHERE " + DataBaseHandler.PRODUCT_ID_CATEGORY + " = " + idCategory;

        Cursor cursor = db.rawQuery(select, null);
        while (cursor.moveToNext()) {
            ItemProduct itemProduct = new ItemProduct();
            putItem(cursor, itemProduct,cat);
            items.add(itemProduct);
        }

        try {
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return items;
    }

    public void putItem(Cursor cursor, ItemProduct itemProduct, Category category) {

        category.setId(cursor.getInt(3));

        itemProduct.setTitle(cursor.getString(0));
        itemProduct.setImage(cursor.getInt(1));
        itemProduct.setCategory(category);

    }
}
