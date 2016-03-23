package com.agro.star.dhara.productapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.agro.star.dhara.productapp.models.Language;
import com.agro.star.dhara.productapp.models.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhara Shah on 22-03-2016 </br>
 * Perfoms DB operations
 */
public class DBHelper  extends SQLiteOpenHelper{
	private static DBHelper mDatabaseHelper;
	private static SQLiteDatabase mSqliteDb;
	private Context mContext;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "productapp.sqlite";

    /**
     * Gets a singleton instance of the DBHelper class
     * @param context
     * @return
     */
	public static DBHelper getInstance(Context context) {
		if (mDatabaseHelper == null) {
			mDatabaseHelper = new DBHelper(context);
			if(mSqliteDb != null) {
				mSqliteDb.close();
			}
		}
		return mDatabaseHelper;
	}

    /**
     * Constructor that initializes the database
     * @param context
     */
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
		openDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	/**
	 * Checks if the database is open or not
	 * @return
	 */
	@Override
	public synchronized void close() {
		super.close();
	}

	/**
	 * Open an existing database or
	 * copy the database from assets if it doesn't exist
	 * @return
	 */
	public SQLiteDatabase openDatabase() {
		File databaseFile = new File("/data/data/"+
				mContext.getApplicationInfo().packageName + "/databases");
		File dbFile = mContext.getDatabasePath(DATABASE_NAME);

		if (!databaseFile.exists()){
			databaseFile.mkdir();
		}

		if (!dbFile.exists()) {
			try {
				this.getReadableDatabase();
				copyDatabase(dbFile);
			} catch (IOException e) {
				throw new RuntimeException("Error creating source database", e);
			}
		}

		mSqliteDb = SQLiteDatabase.openDatabase(dbFile.getPath(), null,
				SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READONLY|SQLiteDatabase.CREATE_IF_NECESSARY);
		return mSqliteDb;
	}

	/**
	 * Copy database from assets folder into the memory
	 * @param dbFile
	 * @throws IOException
	 */
	private void copyDatabase(File dbFile) throws IOException {
		InputStream is = mContext.getAssets().open(DATABASE_NAME);
		OutputStream os = new FileOutputStream(dbFile);

		byte[] buffer = new byte[1024];
		while (is.read(buffer) > 0) {
			os.write(buffer);
		}

		os.flush();
		os.close();
		is.close();
	}

	/**
	 * Gets the list of products based on the language currently selected</br>
	 * We are using the startValue to allow pagination to take place as it is not
	 * correct to load all the items at once
	 * @param startValue - will start with 0 and get incremented by 10
	 *
	 * @return
	 */
	public List<Product> getAllProducts(int startValue, String languageCode) {
		List<Product> productList = new ArrayList<>();
		String sql = " select " +
				ProductContract.ProductEntry.TABLE_NAME + "." + ProductContract.ProductEntry._ID + "," +
				ProductTransContract.ProductTransEntry.COLUMN_NAME_PRODUCT_NAME + "," +
				ProductTransContract.ProductTransEntry.COLUMN_NAME_PRODUCT_DESC + "," +
				ProductContract.ProductEntry.COLUMN_NAME_PROD_IMAGE + "," +
				ProductTransContract.ProductTransEntry.COLUMN_NAME_PRODUCT_DISPLAY_PRICE + "," +
                ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE +
				" from " +
				ProductContract.ProductEntry.TABLE_NAME + " join " + ProductTransContract.ProductTransEntry.TABLE_NAME +
				" on " +
				ProductContract.ProductEntry.TABLE_NAME + "." + ProductContract.ProductEntry._ID +
				"=" +
				ProductTransContract.ProductTransEntry.TABLE_NAME + "." + ProductTransContract.ProductTransEntry.COLUMN_NAME_PRODUCT_ID +
				" where " +
				ProductTransContract.ProductTransEntry.COLUMN_NAME_CODE + "='" + languageCode +"'" +
				" limit " + startValue + ", 15 ";

		mSqliteDb = this.getReadableDatabase();
		Cursor c = mSqliteDb.rawQuery(sql, null);

		try {

			if(c !=null && c.moveToFirst()) {
				/**
				 * While the cursor has records it gets data into the list
				 */
				do {
					productList.add(cursorToProduct(c));
				}while (c.moveToNext());
			}
		}finally {
			if(c !=null)c.close();
		}

		return productList;
	}

    /**
     * Gets the list of languages supported by the application
     * @param languageCode
     * @return
     */
    public List<Language> getAllLanguages(String languageCode){
        List<Language> languageList = new ArrayList<>();
        String sql = "Select " +
                LanguageContract.LanguageEntry.COLUMN_NAME_CODE + " , " +
                LanguageTransContract.LanguageTransEntry.COLUMN_NAME_LANG_NAME +
                " from " +
                LanguageContract.LanguageEntry.TABLE_NAME + " join " +
                LanguageTransContract.LanguageTransEntry.TABLE_NAME +
                " on " +
                LanguageContract.LanguageEntry.TABLE_NAME + "." + LanguageContract.LanguageEntry._ID +
                "=" +
                LanguageTransContract.LanguageTransEntry.COLUMN_NAME_LANG_ID +
                " where " +
                LanguageTransContract.LanguageTransEntry.COLUMN_NAME_LANG_DISPLAY_CODE + "='" + languageCode + "'";

        mSqliteDb = this.getReadableDatabase();
        Cursor c = mSqliteDb.rawQuery(sql, null);

        try {

            if (c != null && c.moveToFirst()) {
                /**
                 * Add the langugages obtained into the list
                 */
                do {
                    languageList.add(cursorToLanguage(c));
                }while (c.moveToNext());
            }
        }finally {
            if(c != null) c.close();
        }
        return languageList;
    }

    /**
     * Gets the featured products, this is based on random currently but it can be
     * modified to pick the products that are yet to come or the best selling products
     * @return
     */
    public List<String> getFeaturedImages(){
        List<String> featuredImages = new ArrayList<>();
        String sql = "SELECT " +
                ProductContract.ProductEntry.COLUMN_NAME_PROD_IMAGE +
                " FROM " +
                ProductContract.ProductEntry.TABLE_NAME +
                " ORDER BY RANDOM() LIMIT 3";

        mSqliteDb = this.getReadableDatabase();
        Cursor c = mSqliteDb.rawQuery(sql, null);
        try {
            if(c!= null  && c.moveToFirst()) {
                do {
                    String imageName = c.getString(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PROD_IMAGE));
                    featuredImages.add(imageName);
                }while (c.moveToNext());
            }
        }finally {
            if(c != null) c.close();
        }
        return featuredImages;
    }

	/**
	 * Gets data from the cursor and returns the product object
	 * @param c
	 * @return
	 */
	private Product cursorToProduct(Cursor c) {
		Product product = new Product();
		product.setId(c.getInt(c.getColumnIndex(ProductContract.ProductEntry._ID)));
		product.setProductName(c.getString(c.getColumnIndex(ProductTransContract.ProductTransEntry.COLUMN_NAME_PRODUCT_NAME)));
		product.setProductDescription(c.getString(c.getColumnIndex(ProductTransContract.ProductTransEntry.COLUMN_NAME_PRODUCT_DESC)));
		product.setProductPrice(c.getDouble(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
        product.setProductDisplayPrice(c.getString(c.getColumnIndex(ProductTransContract.ProductTransEntry.COLUMN_NAME_PRODUCT_DISPLAY_PRICE)));
		product.setProductImage(c.getString(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PROD_IMAGE)));
		return product;
	}

    /**
     * Get data from the cursor and convert to the Language model
     * @param c
     * @return
     */
    private Language cursorToLanguage(Cursor c) {
        Language language = new Language();
        language.setLanguageCode(c.getString(c.getColumnIndex(LanguageContract.LanguageEntry.COLUMN_NAME_CODE)));
        language.setLanguageName(c.getString(c.getColumnIndex(LanguageTransContract.LanguageTransEntry.COLUMN_NAME_LANG_NAME)));
        return language;
    }
}
