package com.agro.star.dhara.productapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.agro.star.dhara.productapp.ProductApp;
import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.activities.MainActivity;
import com.agro.star.dhara.productapp.adapters.MyPagerAdapter;
import com.agro.star.dhara.productapp.adapters.ProductAdapter;
import com.agro.star.dhara.productapp.db.DBHelper;
import com.agro.star.dhara.productapp.interfaces.IActionPerformedListener;
import com.agro.star.dhara.productapp.models.Product;
import com.agro.star.dhara.productapp.utils.prefs.SharedPrefs;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public class MainFragment extends Fragment implements IActionPerformedListener {
    private static final int UPDATE_UI = 1;
    private static MainFragment mFragment;
    private GridView mGridProducts;
    private ViewPager mViewPager;
    private ProductAdapter mProductAdapter;
    private MyPagerAdapter mMyPagerAdapter;
    private List<Product> mProductList;
    private int mStartValue;
    private int mPreviousTotalCount;
    private boolean mIsLoadMore;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_UI:
                    mProductAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * Holds the instance of the listener implemented by this fragment
     */
    private IActionPerformedListener mActionPerformedListener;

    /**
     * Creates an instance of the fragment
     * @return
     */
    public static MainFragment newInstance(){
        mFragment = new MainFragment();
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Set this to true inorder to be able to load menu items from fragments
         */
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * initialize all the views here using the view parameter
         */
        mGridProducts = (GridView)view.findViewById(R.id.gridView);
        mViewPager = (ViewPager)view.findViewById(R.id.viewPager);
        mMyPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),
                DBHelper.getInstance(ProductApp.getAppContext()).getFeaturedImages());
        mViewPager.setAdapter(mMyPagerAdapter);

        /**
         * Bind the circle indicator to the adapter
         */
        CirclePageIndicator titleIndicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
        titleIndicator.setViewPager(mViewPager);

        /**
         * Initialize the listener and pass it to the activity
         */
        mActionPerformedListener = this;
        ((MainActivity)getActivity()).setSearchPerformedListener(mActionPerformedListener);

        mIsLoadMore = true;
        mStartValue = 0;

        setScrollListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * Set the adapter for the gridview, start with upper bound 0
         */
        mProductList = DBHelper.getInstance(ProductApp.getAppContext())
                .getAllProducts(mStartValue, SharedPrefs.getString(SharedPrefs.LANGUAGE_SELECTED,
                        getString(R.string.english_code)));
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

    /**
     * Sets a scroll listener for load more functionality
     */
    private void setScrollListener(){
        mGridProducts.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (mPreviousTotalCount == totalItemCount)
                    return;

                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
                if (loadMore && mIsLoadMore) {
                    mPreviousTotalCount = totalItemCount;
                    mStartValue += 6;
                    // load more items since the list has reached the end
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Product> productList = DBHelper.getInstance(ProductApp.getAppContext()).getAllProducts(mStartValue,
                                    SharedPrefs.getString(SharedPrefs.LANGUAGE_SELECTED, getString(R.string.english_code)));
                            if(productList.size() > 0){
                                mIsLoadMore = true;
                                mProductList.addAll(productList);
                            }else {
                                mIsLoadMore = false;
                            }

                            handler.sendEmptyMessage(UPDATE_UI);
                        }
                    }).run();

                    return;
                }
            }
        });
    }
}
