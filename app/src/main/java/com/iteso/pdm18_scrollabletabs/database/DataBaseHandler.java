package com.iteso.pdm18_scrollabletabs.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Iteso.Store";
    private static final int DATABASE_VERSION = 1;

    private static DataBaseHandler dataBaseHandler;

    //TABLES
    public static final String TABLE_CITY = "City";
    public static final String TABLE_CATEGORY = "Category";
    public static final String TABLE_STORE = "Store";
    public static final String TABLE_PRODUCT = "Product";
    public static final String TABLE_STORE_PRODUCT = "StoreProduct";

    //Columns
    public static final String CITY_ID = "Id";
    public static final String CITY_NAME = "Name";
    public static final String CATEGORY_ID = "Id";
    public static final String CATEGORY_NAME = "Name";
    public static final String STORE_ID = "Id";
    public static final String STORE_NAME = "Name";
    public static final String STORE_PHONE = "Phone";
    public static final String STORE_IDCITY = "IdCity";
    public static final String STORE_THUMBNAIL = "Thumbnail";
    public static final String STORE_LATITUDE = "Latitude";
    public static final String STORE_LONGITUDE = "Longitude";
    public static final String PRODUCT_ID_PRODUCT = "Id";
    public static final String PRODUCT_TITLE = "Title";
    public static final String PRODUCT_IMAGE = "Image";
    public static final String PRODUCT_ID_CATEGORY = "IdCategory";
    public static final String STORE_PRODUCT_ID = "Id";
    public static final String STORE_PRODUCT_ID_PRODUCT = "IdProduct";
    public static final String STORE_PRODUCT_ID_STORE = "IdStore";

    private DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHandler getInstance(Context context) {
        if(dataBaseHandler == null)
            dataBaseHandler = new DataBaseHandler(context);
        return dataBaseHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCity = "CREATE TABLE " + TABLE_CITY + " ("
                + CITY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + CITY_NAME + "TEXT)";
        String tableCategory = "CREATE TABLE " + TABLE_CATEGORY + " ("
                + CATEGORY_ID + "INTEGER PRIMARY KEY,"
                + CATEGORY_NAME + "TEXT)";
        String tableStore = "CREATE TABLE " + TABLE_STORE + " ("
                + STORE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + STORE_NAME + "TEXT,"
                + STORE_PHONE + "TEXT,"
                + STORE_IDCITY + "INTEGER,"
                + STORE_THUMBNAIL + "INTEGER,"
                + STORE_LATITUDE + "DOUBLE,"
                + STORE_LONGITUDE + "DOUBLE)";
        String tableProduct = "CREATE TABLE " + TABLE_PRODUCT + " ("
                + PRODUCT_ID_PRODUCT + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + PRODUCT_TITLE + "TEXT,"
                + PRODUCT_IMAGE + "INTEGER,"
                + PRODUCT_ID_CATEGORY + "INTEGER)";
        String tableStoreProduct = "CREATE TABLE " + TABLE_STORE_PRODUCT + " ("
                + STORE_PRODUCT_ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + STORE_PRODUCT_ID_PRODUCT + "INTEGER,"
                +STORE_PRODUCT_ID_STORE + "INTEGER)";
     db.execSQL(tableCity);
     db.execSQL(tableCategory);
     db.execSQL(tableStore);
     db.execSQL(tableProduct);
     db.execSQL(tableStoreProduct);

     String onCreateInsertCategorys = "INSERT INTO " + TABLE_CATEGORY
             + " (" + CATEGORY_NAME + ") VALUES('Technology')," +
             "('Home'),('Electronics')";
    db.execSQL(onCreateInsertCategorys);

    String onCreateInsertCitys = "INSERT INTO " + TABLE_CITY
            + "(" + CITY_NAME + ") VALUES('Tepito'),('Tlaquepaque')," +
            "('Texcoco'),('Why')";
    db.execSQL(onCreateInsertCitys);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /*switch (oldVersion) {
            case 1:
                default:
        }
        */
    }
}
