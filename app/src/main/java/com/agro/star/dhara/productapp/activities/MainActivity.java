package com.agro.star.dhara.productapp.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.agro.star.dhara.productapp.ProductApp;
import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.adapters.LanguageAdapter;
import com.agro.star.dhara.productapp.customviews.CustomTextView;
import com.agro.star.dhara.productapp.db.DBHelper;
import com.agro.star.dhara.productapp.fragments.MainFragment;
import com.agro.star.dhara.productapp.interfaces.IActionPerformedListener;
import com.agro.star.dhara.productapp.interfaces.ILanguageSelectionListener;
import com.agro.star.dhara.productapp.models.Language;
import com.agro.star.dhara.productapp.utils.CommonUtilities;
import com.agro.star.dhara.productapp.utils.DialogUtils;
import com.agro.star.dhara.productapp.utils.prefs.SharedPrefs;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Dhara Shah on 22-03-2016
 */
public class MainActivity extends BaseActivity implements ILanguageSelectionListener{
    private Toolbar mToolbar;
    private CustomTextView mTxtTitle;

    /**
     * Holds the searched string value
     */
    private String mSearchString;

    /**
     * Listener that would receive language selection changes
     */
    private ILanguageSelectionListener mLanguageSelectedListener;

    /**
     * Listener that listens to a search being performed here in the activity
     */
    private IActionPerformedListener mActionPerformedListener;

    /**
     * Sets the listener instance passed from the fragment to the activity
     * @param actionPerformedListener
     */
    public void setSearchPerformedListener(IActionPerformedListener actionPerformedListener) {
        mActionPerformedListener = actionPerformedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Make language changes here
         */
        CommonUtilities.changeKeyBoardSettings();
        setContentView(R.layout.activity_main);
        mLanguageSelectedListener = this;
        initToolbar();
        initFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setTitle(getString(R.string.action_search));
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
                    if(TextUtils.isEmpty(mSearchString)) {
                        /**
                         * The search is empty so refresh the view to the original one
                         */
                        if (mActionPerformedListener != null) {
                            mActionPerformedListener.onSearchPerformed("");
                        }
                    }
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    mSearchString = query;
                    if (mActionPerformedListener != null) {
                        mActionPerformedListener.onSearchPerformed(mSearchString);
                    }
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

            SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    if (mActionPerformedListener != null) {
                        mActionPerformedListener.onSearchPerformed("");
                    }
                    return true;
                }
            };

            searchView.setOnCloseListener(onCloseListener);
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
            DialogUtils.showLanguageBox(MainActivity.this, mLanguageSelectedListener);
            return true;
        }

        if(id == R.id.action_search) {
            // implemented here not in the fragment
            return true;
        }

        return false;
    }

    @Override
    public void onLanguageSelected(String languageCode) {
        String currentLanguageCode = SharedPrefs.getString(SharedPrefs.LANGUAGE_SELECTED,
                getString(R.string.english_code));
        if(!currentLanguageCode.equals(languageCode)) {
            /**
             * Store in the shared preference and update the UI
             */
            SharedPrefs.putString(SharedPrefs.LANGUAGE_SELECTED, languageCode);
            switchLanguage();
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mTxtTitle = (CustomTextView)findViewById(R.id.txtHeader);
        setSupportActionBar(mToolbar);
    }

    /**
     * Initializes the fragment and loads it into the container frame
     */
    private void initFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_frame, MainFragment.newInstance());
        ft.commit();
    }

    private void switchLanguage() {
        /**
         * Start the activity again since that is required now in the newer api levels
         * for the effect to be seen
         */
        Intent intent = new Intent(ProductApp.getAppContext(),
                MainActivity.class);
        startActivity(intent);
    }
}
