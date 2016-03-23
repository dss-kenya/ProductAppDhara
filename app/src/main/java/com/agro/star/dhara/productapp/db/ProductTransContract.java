package com.agro.star.dhara.productapp.db;

import android.provider.BaseColumns;

/**
 * Created by Dhara Shah on 23-03-2016.
 */
public class ProductTransContract {
    public ProductTransContract(){}

    public static abstract class ProductTransEntry implements BaseColumns {
        public static final String TABLE_NAME = "product_translation_master";
        public static final String COLUMN_NAME_PRODUCT_ID = "product_id";
        public static final String COLUMN_NAME_CODE = "language_code";
        public static final String COLUMN_NAME_PRODUCT_DESC="product_desc";
        public static final String COLUMN_NAME_PRODUCT_DISPLAY_PRICE =  "product_display_price";
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
    }
}
