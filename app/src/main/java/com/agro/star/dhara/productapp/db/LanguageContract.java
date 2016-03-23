package com.agro.star.dhara.productapp.db;

import android.provider.BaseColumns;

/**
 * Created by Dhara Shah on 23-03-2016.
 */
public class LanguageContract {
    public LanguageContract(){}

    public static abstract class LanguageEntry implements BaseColumns {
        public static final String TABLE_NAME = "language_master";
        public static final String COLUMN_NAME_CODE = "language_code";
    }
}
