package com.agro.star.dhara.productapp.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.agro.star.dhara.productapp.R;
import com.agro.star.dhara.productapp.customviews.CustomTextView;
import com.agro.star.dhara.productapp.models.Product;

import java.util.List;

/**
 * Created by Dhara Shah on 22-03-2016.
 */
public class ProductAdapter extends ArrayAdapter<Product> implements Filterable{
    private Context mContext;
    private List<Product> mProductList;
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
