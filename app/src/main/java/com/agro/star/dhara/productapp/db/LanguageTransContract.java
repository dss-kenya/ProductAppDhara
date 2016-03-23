package com.agro.star.dhara.productapp.db;

import android.provider.BaseColumns;

/**
 * Created by USER on 23-03-2016.
 */
public class LanguageTransContract {
    public LanguageTransContract(){}

    public static abstract class LanguageTransEntry implements BaseColumns {
        public static final String TABLE_NAME = "language_translation_master";
        public static final String COLUMN_NAME_LANG_ID = "language_id";
        public static final String COLUMN_NAME_LANG_NAME = "language_name";
        public static final String COLUMN_NAME_LANG_DISPLAY_CODE="display_code";
    }
}
