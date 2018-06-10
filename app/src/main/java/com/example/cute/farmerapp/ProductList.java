package com.example.cute.farmerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cute on 3/22/2018.
 */
public class ProductList extends ArrayAdapter<Products>{
    private Activity context;
    private List<Products> productList;

    public ProductList(Activity context,List<Products> productList){
        super(context,R.layout.list_item,productList);
        this.context=context;
        this.productList=productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listview=inflater.inflate(R.layout.list_item, null, true);

        TextView prod_name=(TextView) listview.findViewById(R.id.product_name);
        TextView prod_cate=(TextView) listview.findViewById(R.id.product_category);
        TextView prod_cost=(TextView) listview.findViewById(R.id.product_cost);

        Products product=productList.get(position);

        prod_name.setText(product.getProductname());
        prod_cate.setText(product.getCategory());
        prod_cost.setText(Long.toString(product.getPrice()));

        return listview;

    }
}
