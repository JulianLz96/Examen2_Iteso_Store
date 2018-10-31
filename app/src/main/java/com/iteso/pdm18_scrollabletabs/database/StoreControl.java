package com.iteso.pdm18_scrollabletabs.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.pdm18_scrollabletabs.beans.Store;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class StoreControl {

    public void addStore(Store store, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.STORE_NAME, store.getName());
        values.put(DataBaseHandler.STORE_PHONE, store.getPhone());
        values.put(DataBaseHandler.STORE_IDCITY, store.getCity().getId());
        values.put(DataBaseHandler.STORE_THUMBNAIL, store.getThumbnail());
        values.put(DataBaseHandler.STORE_LATITUDE, store.getLatitude());
        values.put(DataBaseHandler.STORE_LONGITUDE, store.getLongitude());

        db.insert(DataBaseHandler.TABLE_STORE, null, values);

        try {
            db.close();
        } catch (Exception e) {

        }
    }

    public ArrayList<Store> getStores(DataBaseHandler dh) {
        ArrayList<Store> stores = new ArrayList<>();
        SQLiteDatabase db = dh.getReadableDatabase();

        String select = "SELECT " + DataBaseHandler.STORE_ID + ","
                + DataBaseHandler.STORE_ID + ","
                + DataBaseHandler.STORE_NAME + ","
                + DataBaseHandler.STORE_PHONE + ","
                + DataBaseHandler.STORE_IDCITY + ","
                + DataBaseHandler.STORE_THUMBNAIL + ","
                + DataBaseHandler.STORE_LATITUDE + ","
                + DataBaseHandler.STORE_LONGITUDE
                + " FROM " + DataBaseHandler.TABLE_STORE;

        Cursor cursor = db.rawQuery(select, null);
        while (cursor.moveToNext()) {
            Store store = new Store();
            putStore(cursor, store);
            stores.add(store);
        }

        try {
            cursor.close();
            db.close();
        } catch (Exception e) {

        }

        return stores;
    }

    public Store getStoreById(int idStore, DataBaseHandler dh ) {
        SQLiteDatabase db = dh.getReadableDatabase();
        Store store = new Store();
        String select = "SELECT " + DataBaseHandler.STORE_ID + ","
                + DataBaseHandler.STORE_ID + ","
                + DataBaseHandler.STORE_NAME + ","
                + DataBaseHandler.STORE_PHONE + ","
                + DataBaseHandler.STORE_IDCITY + ","
                + DataBaseHandler.STORE_THUMBNAIL + ","
                + DataBaseHandler.STORE_LATITUDE + ","
                + DataBaseHandler.STORE_LONGITUDE
                + " FROM" + DataBaseHandler.TABLE_STORE
                + " WHERE " + DataBaseHandler.STORE_ID + " = "
                + idStore;
        Cursor cursor = db.rawQuery(select, null);
        while (cursor.moveToNext()) {
            putStore(cursor, store);
        }

        try {
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return store;
    }

    public void putStore(Cursor cursor, Store store) {
        store.setId(cursor.getInt(0));
        store.setName(cursor.getString(1));
        store.setPhone(cursor.getString(2));
        //store.setCity();//TODO implementar setCity
        store.setThumbnail(cursor.getInt(4));
        store.setLatitude(cursor.getDouble(5));
        store.setLongitude(cursor.getDouble(6));
    }

}
