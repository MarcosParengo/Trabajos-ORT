package com.leobrasileo.tp3ertrimestre.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ProductosSQLiteHelper extends SQLiteOpenHelper
{
    String sqlCreate =  "CREATE TABLE IF NOT EXISTS PRODUCTOS  ( `NOMBRE` TEXT, `CANTIDAD` TEXT, `PRECIO` TEXT)";

    public ProductosSQLiteHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTOS");
        db.execSQL(sqlCreate);
    }
}
