package com.example.riza.mydictionary.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by riza on 23/08/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbdictionary";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_ENIN = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_ENIN,
            DatabaseContract.LanguageColumns._ID,
            DatabaseContract.LanguageColumns.NAME,
            DatabaseContract.LanguageColumns.DESCRIPTION

    );
    private static final String SQL_CREATE_TABLE_INEN = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_INEN,
            DatabaseContract.LanguageColumns._ID,
            DatabaseContract.LanguageColumns.NAME,
            DatabaseContract.LanguageColumns.DESCRIPTION

    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_ENIN);
        db.execSQL(SQL_CREATE_TABLE_INEN);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_ENIN);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_INEN);
        onCreate(db);
    }
}
