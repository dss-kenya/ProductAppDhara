package com.agro.star.dhara.productapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
	public static final String DATABASE_NAME = "product_app.sqlite";

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
	 * Gets the list of products under all the categories </br>
	 * We are using the startValue to allow pagination to take place as it is not
	 * correct to load all the items at once
	 * @param startValue - will start with 0 and get incremented by 10
	 *
	 * @return
	 */
	public List<Product> getProductsUnderAllCategories(int startValue) {
		List<Product> productList = new ArrayList<>();
		String sql = " select " +
				ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_ID + "," +
				ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME + "," +
				ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_DESC + "," +
				ProductContract.ProductEntry.COLUMN_NAME_PROD_IMAGE + "," +
				ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE + "," +
				CategoryContract.CategoryEntry.COLUMN_NAME_CATEGORY_NAME +
				" from " +
				ProductContract.ProductEntry.TABLE_NAME + " , " + CategoryContract.CategoryEntry.TABLE_NAME +
				" where " +
				ProductContract.ProductEntry.TABLE_NAME + "." + ProductContract.ProductEntry.COLUMN_NAME_PROD_CAT_ID +
				"=" +
				CategoryContract.CategoryEntry.TABLE_NAME + "." + CategoryContract.CategoryEntry.COLUMN_NAME_CATEGORY_ID +
				" limit " + startValue + ", 10 ";

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
	 * Gets data from the cursor and returns the product object
	 * @param c
	 * @return
	 */
	private Product cursorToProduct(Cursor c) {
		Product product = new Product();
		product.setId(c.getInt(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_ID)));
		product.setProductName(c.getString(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME)));
		product.setCategoryName(c.getString(c.getColumnIndex(CategoryContract.CategoryEntry.COLUMN_NAME_CATEGORY_NAME)));
		product.setProductDescription(c.getString(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_DESC)));
		product.setProductPrice(c.getDouble(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PRODUCT_PRICE)));
		product.setProductImage(c.getString(c.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME_PROD_IMAGE)));
		return product;
	}
}
