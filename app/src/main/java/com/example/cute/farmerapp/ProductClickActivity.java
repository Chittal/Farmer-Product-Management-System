package com.example.cute.farmerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductClickActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView qty;
    TextView valid,cate,f1;
    TextView n,cst,ct;
    String email,mailid,k1;
    DatabaseReference databaseReference1,databaseReference;
    FirebaseUser user;
    String x;
    Button ap;
    long q1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_click);

        n=(TextView) findViewById(R.id.pc_name);
        cst=(TextView) findViewById(R.id.pc_cost);
       // ct=(TextView) findViewById(R.id.categ);
        ap=(Button) findViewById(R.id.back);

        qty=(TextView) findViewById(R.id.pc_qty);
        valid=(TextView) findViewById(R.id.valdate);
        cate=(TextView) findViewById(R.id.categ);
       // f1=(TextView) findViewById(R.id.farm_name);

        user= FirebaseAuth.getInstance().getCurrentUser();
        // uid=user.getUid();
        mailid=user.getEmail();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            x=bundle.getString("pname");
            n.setText(bundle.getString("pname").toUpperCase());
            cate.setText(bundle.getString("pcate"));
            cst.setText(Long.toString(bundle.getLong("pcost")));
            email=bundle.getString("pfarmer");
        }
        Toast.makeText(getApplicationContext(),"!email"+email,Toast.LENGTH_LONG).show();
        toolbar=(Toolbar) findViewById(R.id.toolbar2);
       // toolbar.setTitle("PRODUCT");
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://myfarmerapp-fdb2a.firebaseio.com/Products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String n1 = dataSnapshot1.child("farmer").getValue(String.class);
                    String pn1 = dataSnapshot1.child("productname").getValue(String.class);
                    if (pn1.equals(x) && n1.equals(email)) {
                        valid.setText(dataSnapshot1.child("validity").getValue(String.class));
                        //q1=Long.parseLong(dataSnapshot1.child("quantity").getValue(String.class));
                       // Toast.makeText(getApplicationContext(),"!emailn1"+n1+"!pn1"+pn1,Toast.LENGTH_LONG).show();
                        qty.setText(Long.toString(dataSnapshot1.child("quantity").getValue(Long.class)));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                long x=Long.parseLong(s.toString());
                long x1=Long.parseLong(cst.getText().toString());
                if (x>q1){
                    Toast.makeText(getApplicationContext(), "Please enter quantity less than or equal to " + q1, Toast.LENGTH_LONG).show();
                    return;
                }
                cst.setText(Long.toString(x*x1));
            }
        });
        databaseReference1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myfarmerapp-fdb2a.firebaseio.com/Customer");
        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pname = n.getText().toString().toLowerCase();
                String pcate = ct.getText().toString();
                String pprice = cst.getText().toString();
                String qty1=qty.getText().toString();
                String id = databaseReference1.push().getKey();
                databaseReference1.child(id).child("buyer").setValue(mailid);
                databaseReference1.child(id).child("productname").setValue(pname);
                databaseReference1.child(id).child("category").setValue(pcate);
                databaseReference1.child(id).child("quantity").setValue(Long.parseLong(qty1));
                databaseReference1.child(id).child("price").setValue(Long.parseLong(pprice));
                databaseReference1.child(id).child("farmer").setValue(email);
                Toast.makeText(getApplicationContext(), "Added to cart!!", Toast.LENGTH_SHORT).show();
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String key=dataSnapshot1.getKey();
                            //Toast.makeText(ProductClickActivity.this,"!key:"+key+"!",Toast.LENGTH_LONG).show();
                            Product product = dataSnapshot1.getValue(Product.class);
                            String fr=dataSnapshot1.child("farmer").getValue(String.class);
                            String pn=dataSnapshot1.child("productname").getValue(String.class);
                            //Toast.makeText(ProductClickActivity.this,"!"+fr+":"+email+"!"+pn+":"+pname,Toast.LENGTH_LONG).show();
                            if(fr.equals(email)&&pn.equals(pname)){
                               k1=key;
                                //Toast.makeText(ProductClickActivity.this,"!k1"+k1+"!",Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        deleteProduct(k1);
                        Toast.makeText(getApplicationContext(),"Data Added",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });*/
        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductClickActivity.this,ViewProduct.class));
                finish();
            }
        });
    }
    public void deleteProduct(String key){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Products").child(key);
        Toast.makeText(ProductClickActivity.this,key,Toast.LENGTH_LONG).show();
        databaseReference.removeValue();
    }
}
