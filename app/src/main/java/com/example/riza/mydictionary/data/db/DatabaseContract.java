package com.example.riza.mydictionary.data.db;

import android.provider.BaseColumns;

/**
 * Created by riza on 23/08/18.
 */

public class DatabaseContract {

    public static String TABLE_ENIN = "engind";
    public static String TABLE_INEN = "indeng";

    public static final class LanguageColumns implements BaseColumns {
        //Note title
        public static String NAME = "name";
        //Note description
        public static String DESCRIPTION = "desc";
    }

}
