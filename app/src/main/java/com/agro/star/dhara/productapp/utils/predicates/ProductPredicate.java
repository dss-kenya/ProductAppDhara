package com.agro.star.dhara.productapp.utils.predicates;

import com.agro.star.dhara.productapp.models.Product;
import com.google.common.base.Predicate;

import java.util.Locale;

/**
 * Created by USER on 22-03-2016.
 */
public class ProductPredicate implements Predicate<Product> {
    private String mSearchString;

    public ProductPredicate(String searchString){
        mSearchString = searchString.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public boolean apply(Product product) {
        return product.getProductName().toLowerCase(Locale.ENGLISH).contains(mSearchString) ||
                product.getProductDescription().toLowerCase(Locale.ENGLISH).contains(mSearchString);
    }
}
