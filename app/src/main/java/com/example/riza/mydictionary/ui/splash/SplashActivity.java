package com.example.riza.mydictionary.ui.splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.riza.mydictionary.R;
import com.example.riza.mydictionary.data.db.DatabaseContract;
import com.example.riza.mydictionary.data.db.WordHelper;
import com.example.riza.mydictionary.data.helper.RawHelper;
import com.example.riza.mydictionary.data.helper.SharedPrefHelper;
import com.example.riza.mydictionary.data.model.Word;
import com.example.riza.mydictionary.ui.main.MainActivity;

import java.sql.SQLException;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new PreLoader().execute();

    }

    private class PreLoader extends AsyncTask<Void, Void, Void>{

        private final String TAG = "Loader Class";

        WordHelper dbHelper;
        SharedPrefHelper prefHelper;

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<Word> eninWords = RawHelper.retrieveData(SplashActivity.this,R.raw.english_indonesia);
            ArrayList<Word> inenWords = RawHelper.retrieveData(SplashActivity.this,R.raw.indonesia_english);
            if(prefHelper.isFirstLaunch()){

                try {
                    dbHelper.open();

                    dbHelper.beginTransaction();
                    try {
                        for(Word word: eninWords){
                            dbHelper.insertTransaction(word, DatabaseContract.TABLE_ENIN);
                        }
                        for(Word word: inenWords){
                            dbHelper.insertTransaction(word, DatabaseContract.TABLE_INEN);
                        }

                        prefHelper.setFirstLaunch(false);
                        dbHelper.setTransactionSuccess();

                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                    }
                    dbHelper.endTransaction();
                    dbHelper.close();
                } catch (SQLException e) {
                    Log.e(TAG, e.getMessage());
                }

            }else{

                try {
                    synchronized (this) {
                        this.wait(1000);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            dbHelper = new WordHelper(SplashActivity.this);
            prefHelper = new SharedPrefHelper(SplashActivity.this);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
