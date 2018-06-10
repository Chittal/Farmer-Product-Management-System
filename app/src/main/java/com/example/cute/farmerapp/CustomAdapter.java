package com.example.cute.farmerapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cute on 2/11/2018.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    CustomAdapter(Context context,List<RowItem> rowItems){
        this.context=context;
        this.rowItems=rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder{
        ImageView image;
        TextView product;
        TextView cost;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;

        LayoutInflater mInflator=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView=mInflator.inflate(R.layout.list_item,null);
            holder=new ViewHolder();

           // holder.product=(TextView) convertView.findViewById(R.id.pro);
           // holder.image=(ImageView) convertView.findViewById(R.id.img);
           // holder.cost=(TextView) convertView.findViewById(R.id.costt);

            RowItem row_pos=rowItems.get(position);

            holder.image.setImageResource(row_pos.getId());
            holder.product.setText(row_pos.getProduct());
            holder.cost.setText(row_pos.getCost());
        }
        return convertView;
    }
/*
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        rowItems.clear();
        if (charText.length() == 0) {
            rowItems.addAll(r);
        } else {
            for (WorldPopulation wp : arraylist) {
                if (wp.getCountry().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    */
}
