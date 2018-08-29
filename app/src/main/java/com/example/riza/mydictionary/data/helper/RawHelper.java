package com.example.riza.mydictionary.data.helper;

import android.content.Context;
import android.util.Log;

import com.example.riza.mydictionary.data.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by riza on 28/08/18.
 */

public class RawHelper {
    public static ArrayList<Word> retrieveData(Context context, int resId)
    {
        InputStream inputStream = context.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        ArrayList<Word> words = new ArrayList<>();
        try {
            while (( line = buffreader.readLine()) != null) {
                String[] splitstr = line.split("\t");
                Word word = new Word(0,splitstr[0],splitstr[1]);
                words.add(word);
            }
        } catch (IOException e) {
            Log.e("RAW Err",e.getMessage());
        }
        return words;
    }

}
