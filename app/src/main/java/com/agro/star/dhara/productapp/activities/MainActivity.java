package com.agro.star.dhara.productapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.agro.star.dhara.productapp.ProductApp;
import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.fragments.MainFragment;
import com.agro.star.dhara.productapp.interfaces.ISearchPerformedListener;

/**
 * Created by Dhara Shah on 22-03-2016
 */
public class MainActivity extends BaseActivity {
    /**
     * Holds the searched string value
     */
    private String mSearchString;

    /**
     * Listener that listens to a search being performed here in the activity
     */
    private ISearchPerformedListener mSearchPerformedListener;

    /**
     * Sets the listener instance passed from the fragment to the activity
     * @param searchPerformedListener
     */
    public void setSearchPerformedListener(ISearchPerformedListener searchPerformedListener) {
        mSearchPerformedListener = searchPerformedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager =
                (SearchManager) ProductApp.getAppContext().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            MenuItemCompat.collapseActionView(searchItem);
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    mSearchString = newText;
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    mSearchString = query;
                    if (mSearchPerformedListener != null) {
                        mSearchPerformedListener.onSearchPerformed(mSearchString);
                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search) {
            // implemented here not in the fragment
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the fragment and loads it into the container frame
     */
    private void initFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_frame, MainFragment.newInstance());
        ft.commit();
    }
}
