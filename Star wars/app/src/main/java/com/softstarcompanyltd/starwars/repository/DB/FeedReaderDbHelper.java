package com.softstarcompanyltd.starwars.repository.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FavouriteEntity.db";

    ////////////////////////////////////////////////////////////
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.FeedEntry.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedReaderContract.FeedEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    ////////////////////////////////////////////////////////////
    public void writeToDbEntityData(String idName, String description, FeedReaderDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.ID_NAME, idName);
        values.put(FeedReaderContract.FeedEntry.DATA_OF_ENTITY, description);
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }

    public void deleteIntoToDb(String idName, FeedReaderDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, FeedReaderContract.FeedEntry.ID_NAME + "=?", new String[]{idName});
    }
    public boolean exists (String idName, FeedReaderDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {FeedReaderContract.FeedEntry.ID_NAME};

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null, null);

        boolean flag = false;

        while (cursor.moveToNext()) {
            String a = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.ID_NAME));
            if (a.contains(idName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public ArrayList<String> getAllFavouriteEntity(FeedReaderDbHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {FeedReaderContract.FeedEntry.DATA_OF_ENTITY};

        Cursor cursor = db.query(FeedReaderContract.FeedEntry.TABLE_NAME, projection, null, null, null, null, null, null);

        ArrayList <String> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.DATA_OF_ENTITY)));
        }
        cursor.close();
        return list;
    }
}