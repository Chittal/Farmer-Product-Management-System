package com.example.cute.farmerapp;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Spinner;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;
import android.text.Editable;

import com.google.firebase.database.DatabaseReference;

import java.util.Locale;
public class BuyerHome extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] vproduct,fproduct,cproduct;
    TypedArray vpics,fpics,cpics;
    String[] vcost,fcost,ccost;
    EditText editsearch;

    List<RowItem>  rowItems;
    ListView mylistview;
    Spinner s;

    //DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);
        mylistview=(ListView) findViewById(R.id.list);
        rowItems=new ArrayList<RowItem>();
        s=(Spinner) findViewById(R.id.category);
        editsearch = (EditText) findViewById(R.id.srch);
        populateList();
        mylistview.setOnItemClickListener(this);
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(" ")) {
                    populateList();
                } else
                    searchItem(s.toString());
                //Toast.makeText(getApplicationContext(), "Search!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Toast.makeText(getApplicationContext(), "Bye!", Toast.LENGTH_SHORT).show();
    }
    public void populateList(){
        vproduct=getResources().getStringArray(R.array.VegProducts);

        vpics=getResources().obtainTypedArray(R.array.Veg);

        vcost=getResources().getStringArray(R.array.VegCost);

        /*fproduct=getResources().getStringArray(R.array.FruitProducts);

        fpics=getResources().obtainTypedArray(R.array.Fruit);
        fcost=getResources().getStringArray(R.array.FruitCost);

        cproduct=getResources().getStringArray(R.array.CerealProducts);

        cpics=getResources().obtainTypedArray(R.array.Cereals);

        ccost=getResources().getStringArray(R.array.CerealsCost);
*/

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BuyerHome.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(myAdapter);

        //String cat=s.getSelectedItem().toString();
        //if(cat.equals("Vegetables")) {

        for (int i = 0; i < vproduct.length; i++) {
            //Toast.makeText(getApplicationContext(), "Vege!", Toast.LENGTH_SHORT).show();
            RowItem item = new RowItem(vproduct[i], vpics.getResourceId(i, -1), vcost[i]);
            rowItems.add(item);
        }
        //}if(cat.equals("Fruits")) {
    /*    for (int i = 0; i < fproduct.length; i++) {
            RowItem item = new RowItem(fproduct[i], fpics.getResourceId(i, -1), fcost[i]);
            rowItems.add(item);
            //Toast.makeText(getApplicationContext(), "Fruits!", Toast.LENGTH_SHORT).show();
        }
        //}if(cat.equals("Cereals")) {
        for (int i = 0; i < cproduct.length; i++) {
            RowItem item = new RowItem(cproduct[i], cpics.getResourceId(i, -1), ccost[i]);
            rowItems.add(item);
           // Toast.makeText(getApplicationContext(), "Cereals!", Toast.LENGTH_SHORT).show();
        }
        //}
*/
        final CustomAdapter adapter=new CustomAdapter(this,rowItems);
        mylistview.setAdapter(adapter);
    }
    public void  searchItem(String item){
        for(String i:vproduct){
            if(!i.contains(item)){
                rowItems.remove(i);
                //Toast.makeText(getApplicationContext(), "Removing!", Toast.LENGTH_SHORT).show();
            }
        }
      /*  for(String i:fproduct){
            if(!i.contains(item)){
                rowItems.remove(i);
            }
        }
        for(String i:cproduct){
            if(!i.contains(item)){
                rowItems.remove(i);
            }
        }*/
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String product=rowItems.get(position).getProduct();
        Toast.makeText(getApplicationContext()," "+product,Toast.LENGTH_SHORT).show();
    }
}
