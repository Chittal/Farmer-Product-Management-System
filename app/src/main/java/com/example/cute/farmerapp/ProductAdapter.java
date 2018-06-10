package com.example.cute.farmerapp;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Products> list;


    public ProductAdapter(List<Products> list) {
        this.list = list;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Products products=list.get(position);
        holder.textName.setText(products.productname);
        holder.textCost.setText(Long.toString(products.price));
        holder.textCategory.setText(products.category);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView textName,textCategory,textCost;

        public ProductViewHolder(View itemView){
            super(itemView);

            textName=(TextView) itemView.findViewById(R.id.text_name);
            textCategory=(TextView) itemView.findViewById(R.id.text_category);
            textCost=(TextView) itemView.findViewById(R.id.ct);
        }

    }

}
