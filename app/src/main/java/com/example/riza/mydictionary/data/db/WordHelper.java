package com.example.riza.mydictionary.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.riza.mydictionary.data.model.Word;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by riza on 23/08/18.
 */
public class WordHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public WordHelper(Context context){
        this.context = context;
    }

    public WordHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Word> getDataByName(String name, String table){
        String result = "";
        Cursor cursor = database.query(table,null,
                DatabaseContract.LanguageColumns.NAME+" LIKE ?",
                new String[]{name+"%"},null,null,null,"20");
        cursor.moveToFirst();
        ArrayList<Word> arrayList = new ArrayList<>();
        Word word;
        if (cursor.getCount()>0) {
            do {
                word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.LanguageColumns._ID)));
                word.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LanguageColumns.NAME)));
                word.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LanguageColumns.DESCRIPTION)));

                arrayList.add(word);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Word> getAllData(String table){
        Cursor cursor = database.query(table,null,null,null,null,null,DatabaseContract.LanguageColumns._ID+ " ASC",null);
        cursor.moveToFirst();
        ArrayList<Word> arrayList = new ArrayList<>();
        Word word;
        if (cursor.getCount()>0) {
            do {
                word = new Word();
                word.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.LanguageColumns._ID)));
                word.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LanguageColumns.NAME)));
                word.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.LanguageColumns.DESCRIPTION)));


                arrayList.add(word);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Word word,String  table){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(DatabaseContract.LanguageColumns.NAME, word.getName());
        initialValues.put(DatabaseContract.LanguageColumns.DESCRIPTION, word.getDesc());
        return database.insert(table, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(Word word, String table){
        String sql = "INSERT INTO "+table+" ("+DatabaseContract.LanguageColumns.NAME+", "+DatabaseContract.LanguageColumns.DESCRIPTION
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, word.getName());
        stmt.bindString(2, word.getDesc());
        stmt.execute();
        stmt.clearBindings();

    }

    public int update(Word word, String table){
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.LanguageColumns.NAME, word.getName());
        args.put(DatabaseContract.LanguageColumns.DESCRIPTION, word.getDesc());
        return database.update(table, args, DatabaseContract.LanguageColumns._ID + "= '" + word.getId() + "'", null);
    }


    public int delete(int id, String table){
        return database.delete(table, DatabaseContract.LanguageColumns._ID + " = '"+id+"'", null);
    }
}
