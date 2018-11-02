package com.iteso.examen2.database;

import android.app.admin.DeviceAdminInfo;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.iteso.examen2.beans.Category;
import com.iteso.examen2.beans.ItemProduct;
import com.iteso.examen2.beans.Store;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemProductControl extends ContentProvider{
    SQLiteDatabase db;
    static final String PROVIDER_NAME = "com.iteso.examen2.database.ItemProductControl";
    static final String URL = "content://" + PROVIDER_NAME + "/Products";
    static final Uri CONTENT_URI = Uri.parse(URL);

    private static HashMap<String, String> PRODUCTS_PROJECTION_MAP;

    static final int PRODUCTS = 1;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Products", PRODUCTS);
        //uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
    }


    public void addProduct(ItemProduct itemProduct, DataBaseHandler dh) {
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.PRODUCT_TITLE, itemProduct.getTitle());
        values.put(DataBaseHandler.PRODUCT_IMAGE, itemProduct.getImage());
        values.put(DataBaseHandler.PRODUCT_ID_CATEGORY, itemProduct.getCategory().getId());

        db.insert(DataBaseHandler.TABLE_PRODUCT, null, values);

        String selectIdP = "SELECT " + DataBaseHandler.PRODUCT_ID_PRODUCT
                + " FROM " + DataBaseHandler.TABLE_PRODUCT +
                " WHERE " + DataBaseHandler.PRODUCT_TITLE + " = '" + itemProduct.getTitle() + "'";
        String selectIdS = "SELECT " + DataBaseHandler.STORE_ID
                + " FROM " + DataBaseHandler.TABLE_STORE +
                " WHERE " +DataBaseHandler.STORE_NAME + " = '" + itemProduct.getStore().getName()+ "'";
        Cursor cursor = db.rawQuery(selectIdP, null);
        cursor.moveToNext();
        int id = cursor.getInt(0);
        Log.e("Tech", Integer.toString(id));
        ContentValues valuesStoreProduct = new ContentValues();
        valuesStoreProduct.put(DataBaseHandler.STORE_PRODUCT_ID_PRODUCT, id);
        cursor = db.rawQuery(selectIdS, null);
        cursor.moveToNext();
        id = cursor.getInt(0);
        valuesStoreProduct.put(DataBaseHandler.STORE_PRODUCT_ID_STORE, id);
        db.insert(DataBaseHandler.TABLE_STORE_PRODUCT, null, valuesStoreProduct);
        try {
            db.close();
            cursor.close();
        } catch (Exception e) {

        }
    }


   public ArrayList<ItemProduct> getItemProductsByCategory(int idCategory, DataBaseHandler dh){
        ArrayList<ItemProduct> items = new ArrayList<>();
        SQLiteDatabase db = dh.getReadableDatabase();
        Log.e("Tech", "getItemProducts: " + Integer.toString(idCategory));
       Category cat = new Category();
       Store store = new Store();
        String select = "SELECT " +
                DataBaseHandler.PRODUCT_TITLE +
                "," + DataBaseHandler.PRODUCT_IMAGE +
                "," + DataBaseHandler.PRODUCT_ID_CATEGORY +
                "," + DataBaseHandler.TABLE_STORE + "." +  DataBaseHandler.STORE_ID +
                "," + DataBaseHandler.STORE_NAME
                + " FROM " + DataBaseHandler.TABLE_PRODUCT
                + " INNER JOIN " + DataBaseHandler.TABLE_STORE_PRODUCT + " ON "
                + DataBaseHandler.TABLE_PRODUCT + "." + DataBaseHandler.PRODUCT_ID_PRODUCT + "=" + DataBaseHandler.TABLE_STORE_PRODUCT + "." +DataBaseHandler.STORE_PRODUCT_ID
                + " INNER JOIN " + DataBaseHandler.TABLE_STORE + " ON "
                + DataBaseHandler.TABLE_STORE + "." + DataBaseHandler.STORE_ID + "=" + DataBaseHandler.TABLE_STORE_PRODUCT + "." + DataBaseHandler.STORE_PRODUCT_ID
                + " WHERE " + DataBaseHandler.PRODUCT_ID_CATEGORY + " = " + idCategory;

        Cursor cursor = db.rawQuery(select, null);
        while (cursor.moveToNext()) {
            ItemProduct itemProduct = new ItemProduct();
            putItem(cursor, itemProduct,cat, store);
            Log.e("Tech", itemProduct.toString());
            items.add(itemProduct);
        }

        try {
            cursor.close();
            db.close();
        } catch (Exception e) {

        }
        return items;
    }

    public void putItem(Cursor cursor, ItemProduct itemProduct, Category category, Store store) {

        category.setId(cursor.getInt(2));
        store.setId(cursor.getInt(3));
        store.setName(cursor.getString(4));
        itemProduct.setTitle(cursor.getString(0));
        Log.e("Tech", "putItem: " + itemProduct.getTitle());
        itemProduct.setImage(cursor.getInt(1));
        itemProduct.setCategory(category);
        itemProduct.setStore(store);
        }

    @Override
    public boolean onCreate() {
        DataBaseHandler dbHandler = DataBaseHandler.getInstance(getContext());
        db = dbHandler.getWritableDatabase();
        return (db == null) ? false: true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long rowID = db.insert(DataBaseHandler.TABLE_PRODUCT, "", contentValues);
        if (rowID > 0) {
            Uri uri1 = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(uri,null);
            return  uri1;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DataBaseHandler.TABLE_PRODUCT);

        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                qb.setProjectionMap(PRODUCTS_PROJECTION_MAP);
                break;
                default:
        }

        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                count = db.delete(DataBaseHandler.TABLE_PRODUCT, selection, selectionArgs);
                break;
                default:
                    throw new IllegalArgumentException("Unknown URI" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues contentValues,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                count = db.update(DataBaseHandler.TABLE_PRODUCT, contentValues, selection,selectionArgs);
                break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.examen2.productos";
                default:
                    throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
