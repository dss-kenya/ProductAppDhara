package com.agro.star.dhara.productapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.agro.star.dhara.productapp.ProductApp;
import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.activities.MainActivity;
import com.agro.star.dhara.productapp.adapters.ProductAdapter;
import com.agro.star.dhara.productapp.db.DBHelper;
import com.agro.star.dhara.productapp.interfaces.ISearchPerformedListener;
import com.agro.star.dhara.productapp.models.Product;

import java.util.List;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public class MainFragment extends Fragment implements ISearchPerformedListener{
    private static MainFragment mFragment;
    private GridView mGridProducts;
    private ProductAdapter mProductAdapter;
    private List<Product> mProductList;
    private int mStartValue;

    /**
     * Holds the instance of the listener implemented by this fragment
     */
    private ISearchPerformedListener mSearchPerformedListener;

    /**
     * Creates an instance of the fragment
     * @return
     */
    public static MainFragment newInstance(){
        mFragment = new MainFragment();
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Set this to true inorder to be able to load menu items from fragments
         */
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * initialize all the views here using the view parameter
         */
        mGridProducts = (GridView)view.findViewById(R.id.gridView);

        /**
         * Initialize the listener and pass it to the activity
         */
        mSearchPerformedListener = this;
        ((MainActivity)getActivity()).setSearchPerformedListener(mSearchPerformedListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * Set the adapter for the gridview, start with upper bound 0
         */
        mStartValue = 0;
        mProductList = DBHelper.getInstance(ProductApp.getAppContext())
                .getProductsUnderAllCategories(mStartValue);
        mProductAdapter = new ProductAdapter(ProductApp.getAppContext(),
                R.layout.individual_row, mProductList);
        mGridProducts.setAdapter(mProductAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu items for use in the action bar
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSearchPerformed(String searchString) {
        /**
         * Use filterable to filter those records with the search string </br>
         * And notify the adapter. The search string will be searched for in the
         * product name, product description, and also product category.
         */
         mProductAdapter.getFilter().filter(searchString);
    }
}
