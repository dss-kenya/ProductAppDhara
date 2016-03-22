package com.agro.star.dhara.productapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.customviews.CustomTextView;
import com.agro.star.dhara.productapp.models.Product;
import com.agro.star.dhara.productapp.utils.predicates.ProductPredicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public class ProductAdapter extends ArrayAdapter<Product> implements Filterable{
    private Context mContext;
    private List<Product> mProductList;
    private List<Product> mOriginalProductList;
    private int RESOURCE;

    /**
     * Constructor
     * @param context
     * @param resource
     * @param objects
     */
    public ProductAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mProductList = objects;
        RESOURCE = resource;
        mOriginalProductList = new ArrayList<>();
        mOriginalProductList.addAll(mProductList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder vh = null;

        if(view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(RESOURCE, parent, false);
            vh = new ViewHolder();
            vh.txtProductDesc = (CustomTextView)view.findViewById(R.id.txtDescription);
            vh.txtProductName = (CustomTextView)view.findViewById(R.id.txtProductName);
            vh.txtProductPrice = (CustomTextView)view.findViewById(R.id.txtPrice);
            vh.imgProduct = (ImageView)view.findViewById(R.id.imgProduct);
            view.setTag(vh);
        }else {
            vh = (ViewHolder)view.getTag();
        }

        /**
         * Sets data into the views
         */
        vh.txtProductName.setText(mProductList.get(position).getProductName());
        vh.txtProductDesc.setText(mProductList.get(position).getProductDescription());
        vh.txtProductPrice.setText(""+mProductList.get(position).getProductPrice());

        return view;
    }

    @Override
    public Filter getFilter() {
        return new CustomFilter();
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            /**
             * There is no search being performed so we pass the original data as it is
             */
            if(constraint == null || constraint.length() ==0){
                results.values = mOriginalProductList;
                results.count = mOriginalProductList.size();
            }else{
                List<Product> filteredProducts = new ArrayList<>();

                /**
                 * Guava predicate will filter those products with the searchString
                 */
                Collection<Product> collection = Collections2.filter(mOriginalProductList,
                        new ProductPredicate(constraint.toString()));
                filteredProducts.addAll(collection);
                results.values = filteredProducts;
                results.count = filteredProducts.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mProductList = (List<Product>)results.values;
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder for the views
     */
    class ViewHolder {
        CustomTextView txtProductName;
        CustomTextView txtProductDesc;
        CustomTextView txtProductPrice;
        ImageView imgProduct;
    }
}
