package com.example.cute.farmerapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class ViewProduct extends AppCompatActivity {

    ListView mProductList;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    String uid;
    String mailid;
    ProductList adapter ;
    FirebaseAuth.AuthStateListener mAuthListenter;
    List<Products> productList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        mProductList=(ListView) findViewById(R.id.product_recycler_list);
        //mProductList.setHasFixedSize(true);
        //mProductList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Products");
        user= FirebaseAuth.getInstance().getCurrentUser();
        mailid=user.getEmail();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data for You....Please Wait !");
        progressDialog.show();

        mAuth=FirebaseAuth.getInstance();
        mAuthListenter=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(ViewProduct.this,LoginActivity.class));
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListenter);
        //Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
      /*  FirebaseRecyclerAdapter<Products,ProductViewHolder> fbra=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(
                Products.class,R.layout.list_item,ProductViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Products model, int position) {
                viewHolder.setName(model.getProductname());
                //viewHolder.setCate(model.getCategory());
                viewHolder.setCost(Long.toString(model.getPrice()));
                // Toast.makeText(getApplicationContext(),"Hi "+model.getProductname(),Toast.LENGTH_SHORT).show();
            }
        };
        mProductList.setAdapter(fbra);*/
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                productList.clear();
                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Products product = dataSnapshot1.getValue(Products.class);
                    String em=dataSnapshot1.child("farmer").getValue(String.class);
                    if(em.equals(mailid))
                    productList.add(product);
                }
                adapter = new ProductList(ViewProduct.this, productList);
                mProductList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ViewProduct.this, ProductClickActivity.class);
                Products p = (Products) mProductList.getItemAtPosition(position);
                String name = p.getProductname();
                String cate = p.getCategory();
                long cost = p.getPrice();
                String farmer = mailid;
                i.putExtra("pname", name);
                i.putExtra("pcost", cost);
                i.putExtra("pcate", cate);
                i.putExtra("pfarmer", farmer);
                startActivity(i);
            }
        });
    }
   /* public static class ProductViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ProductViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String name) {
            TextView prod_name=(TextView) mView.findViewById(R.id.pro);
            prod_name.setText(name);

        }
        public void setCate(String cate) {
            TextView prod_cate=(TextView) mView.findViewById(R.id.text_category);
            prod_cate.setText(cate);
        }
        public void setCost(String cost) {
            TextView prod_cost=(TextView) mView.findViewById(R.id.costt);
            prod_cost.setText(cost);
        }
    }*/
}
