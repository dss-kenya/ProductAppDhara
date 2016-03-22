package com.agro.star.dhara.productapp.db;

import android.provider.BaseColumns;

/**
 * Created by Dhara Shah on 22-03-2016
 */
public final class CategoryContract {
	public CategoryContract(){}
	
	public static abstract class CategoryEntry implements BaseColumns {
		public static final String TABLE_NAME = "category_master";
        public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
        public static final String COLUMN_NAME_CATEGORY_NAME = "category_name";
	}
}
