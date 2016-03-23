package com.agro.star.dhara.productapp.db;

import android.provider.BaseColumns;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public final class ProductContract {
    public ProductContract(){}

    public static abstract class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "product_master";
        public static final String COLUMN_NAME_PROD_IMAGE="product_image";
        public static final String COLUMN_NAME_PRODUCT_PRICE =  "product_price";
    }
}
