package com.example.cute.farmerapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import java.text.DateFormat;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddProduct extends AppCompatActivity {

    EditText name,quan,price;
    Button add,dt;
    TextView hv;
    Spinner procat;
    DatabaseReference databaseReference, databaseReference1;
    FirebaseUser user;
    String uid;
    String mailid;
    Calendar calendar;
    int day,month,year,d1,m1,y1;
    String record;
    DataSnapshot dataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name=(EditText) findViewById(R.id.proname);
        quan=(EditText) findViewById(R.id.qty);
        price=(EditText) findViewById(R.id.cost);

        hv=(TextView) findViewById(R.id.harvest_date);

        add=(Button) findViewById(R.id.productadd);

        calendar=Calendar.getInstance();
        day=calendar.get(Calendar.DAY_OF_MONTH);
        month=calendar.get(Calendar.MONTH);
        year=calendar.get(Calendar.YEAR);

        month=month+1;

        hv.setText(day+"/"+month+"/"+year);
        dt=(Button) findViewById(R.id.date_button);
        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddProduct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear=monthOfYear+1;
                        d1=dayOfMonth;
                        m1=monthOfYear;
                        y1=year;
                        hv.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        mailid=user.getEmail();
        //Toast.makeText(getApplicationContext()," "+mailid,Toast.LENGTH_SHORT).show();

       // databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://myfarmerapp-fdb2a.firebaseio.com/Users");

        databaseReference1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myfarmerapp-fdb2a.firebaseio.com/Products");

        procat = (Spinner) findViewById(R.id.procategory);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddProduct.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        procat.setAdapter(myAdapter);

        procat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        record = "Vegetables";
                        break;
                    case 1:
                        record = "Fruits";
                        break;
                    case 2:
                        record = "Cereals";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //mailid=databaseReference.child("Email").toString();
        //Toast.makeText(getApplicationContext()," "+mailid,Toast.LENGTH_SHORT).show();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pname = name.getText().toString();
                String pqty = quan.getText().toString();
                String pprice = price.getText().toString();
                String pdate = hv.getText().toString();

                if (TextUtils.isEmpty(pname)) {
                    Toast.makeText(getApplicationContext(), "Enter product name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pqty)) {
                    Toast.makeText(getApplicationContext(), "Enter quantity!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pprice)) {
                    Toast.makeText(getApplicationContext(), "Enter price!", Toast.LENGTH_SHORT).show();
                    return;
                }
                calendar=Calendar.getInstance();
                day=calendar.get(Calendar.DAY_OF_MONTH);
                month=calendar.get(Calendar.MONTH);
                year=calendar.get(Calendar.YEAR);

                month=month+1;
                String cur=day+"/"+month+"/"+year;
                DateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
               // Toast.makeText(getApplicationContext(),cur+"!chosen!"+hv.getText().toString(),Toast.LENGTH_LONG).show();

                Date d1=null;
                Date d2=null;
                try {
                    d1 = sf.parse(cur);
                    d2 = sf.parse(hv.getText().toString());
                }catch(Exception e){
                    e.printStackTrace();
                }
                long diff=d1.getTime()-d2.getTime();
                long hours=diff/(60*60*1000);
                long days=hours/24;
              // Toast.makeText(getApplicationContext(),Long.toString(days),Toast.LENGTH_LONG).show();
                if(record.equals("Vegetables")){
                    if(pname.equals("Cauliflower")||pname.equals("Cucumber")||pname.equals("Green Beans")||pname.equals("Leafy Vegetables")) {
                        if (days > 5) {
                            Toast.makeText(getApplicationContext(), "Validity of vegetables is over.You cannot add this item!!", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Calendar c=Calendar.getInstance();
                            c.setTime(d2);
                            //c.add(Calendar.YEAR, 5);
                            //c.add(Calendar.MONTH, 5);
                            //c.add(Calendar.DAY_OF_MONTH, 5);
                            c.add(Calendar.DATE,5);
                            day=c.get(Calendar.DAY_OF_MONTH);
                            month=c.get(Calendar.MONTH);
                            year=c.get(Calendar.YEAR);
                            String hdate=day+"/"+month+"/"+year;
                            addItem(pname, pqty, pprice,pdate,hdate);
                        }
                    }else if(pname.equals("Broccoli")||pname.equals("Eggplant")){
                        if(days > 4) {
                            Toast.makeText(getApplicationContext(), "Validity of vegetables is over.You cannot add this item!!", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Calendar c=Calendar.getInstance();
                            c.setTime(d2);
                           // c.add(Calendar.YEAR, 4);
                           // c.add(Calendar.MONTH,4);
                           // c.add(Calendar.DAY_OF_MONTH, 4);
                            c.add(Calendar.DATE,4);
                            day=c.get(Calendar.DAY_OF_MONTH);
                            month=c.get(Calendar.MONTH);
                            month=month+1;
                            year=c.get(Calendar.YEAR);
                            String hdate=day+"/"+month+"/"+year;
                            addItem(pname, pqty, pprice,pdate,hdate);
                        }
                    }else if(pname.equals("Cabbage")||pname.equals("Celery")||pname.equals("Onions")){
                        if(days > 14) {
                            Toast.makeText(getApplicationContext(), "Validity of vegetables is over.You cannot add this item!!", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Calendar c=Calendar.getInstance();
                            c.setTime(d2);
                           // c.add(Calendar.YEAR, 14);
                           // c.add(Calendar.MONTH,14);
                           // c.add(Calendar.DAY_OF_MONTH, 14);
                            c.add(Calendar.DATE,14);
                            day=c.get(Calendar.DAY_OF_MONTH);
                            month=c.get(Calendar.MONTH);
                            month=month+1;
                            year=c.get(Calendar.YEAR);
                            String hdate=day+"/"+month+"/"+year;
                            addItem(pname, pqty, pprice,pdate,hdate);
                        }
                    }else if(pname.equals("Onions")||pname.equals("Tomato")){
                        if(days > 2) {
                            Toast.makeText(getApplicationContext(), "Validity of vegetables is over.You cannot add this item!!", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Calendar c=Calendar.getInstance();
                            c.setTime(d2);
                            c.add(Calendar.DATE, 2);
                            //c.add(Calendar.YEAR, 2);
                            //c.add(Calendar.MONTH,2);
                           // c.add(Calendar.DAY_OF_MONTH, 2);
                            day=c.get(Calendar.DAY_OF_MONTH);
                            month=c.get(Calendar.MONTH);
                            month=month+1;
                            year=c.get(Calendar.YEAR);
                            String hdate=day+"/"+month+"/"+year;
                            addItem(pname, pqty, pprice,pdate,hdate);
                        }
                    }else if(pname.equals("Chili")||pname.equals("Potato")){
                        if(days > 7) {
                            Toast.makeText(getApplicationContext(), "Validity of vegetables is over.You cannot add this item!!", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Calendar c=Calendar.getInstance();
                            c.setTime(d2);
                           // c.add(Calendar.YEAR, 7);
                           // c.add(Calendar.MONTH,7);
                            c.add(Calendar.DATE,7);
//                            day=c.get(Calendar.DAY_OF_MONTH);
                            month=c.get(Calendar.MONTH);
                            month=month+1;
                            year=c.get(Calendar.YEAR);
                            String hdate=day+"/"+month+"/"+year;
                            addItem(pname, pqty, pprice,pdate,hdate);
                        }
                    }else if(pname.equals("Pumpkin")||pname.equals("Garlic")||pname.equals("Mushroom")||pname.equals("Lettuce")||pname.equals("Carrot")){
                        if(days > 30) {
                            Toast.makeText(getApplicationContext(), "Validity of vegetables is over.You cannot add this item!!", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Calendar c=Calendar.getInstance();
                            c.setTime(d2);
                            //c.add(Calendar.YEAR, 30);
                            //c.add(Calendar.MONTH, 30);
                            //c.add(Calendar.DAY_OF_MONTH, 30);
                            c.add(Calendar.DATE,30);
                            day=c.get(Calendar.DAY_OF_MONTH);
                            month=c.get(Calendar.MONTH);
                            month=month+1;
                            year=c.get(Calendar.YEAR);
                            String hdate=day+"/"+month+"/"+year;
                            addItem(pname, pqty, pprice,pdate,hdate);
                        }
                    }else{
                        addItem(pname,pqty,pprice,pdate,"0");
                    }
                }else if(record.equals("Fruits")){
                    if(days > 3) {
                        Toast.makeText(getApplicationContext(), "Validity of fruits is over.You cannot add this item!!", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        Calendar c=Calendar.getInstance();
                        c.setTime(d2);
                        c.add(Calendar.DATE, 3);
                        //c.add(Calendar.YEAR, 3);
                        //c.add(Calendar.MONTH,3);
                        //c.add(Calendar.DAY_OF_MONTH, 3);
                        day=c.get(Calendar.DAY_OF_MONTH);
                        month=c.get(Calendar.MONTH);
                        month=month+1;
                        year=c.get(Calendar.YEAR);
                        String hdate=day+"/"+month+"/"+year;
                        addItem(pname, pqty, pprice,pdate,hdate);
                    }
                }else if(record.equals("Cereals")){
                    addItem(pname, pqty, pprice,pdate,"0");
                }
              /*  String id = databaseReference1.push().getKey();
               // Products products = new Products(pname, pqty, pprice);
                databaseReference1.child(id).child("farmer").setValue(mailid);
                databaseReference1.child(id).child("productname").setValue(pname);
                databaseReference1.child(id).child("quantity").setValue(Long.parseLong(pqty));
                databaseReference1.child(id).child("price").setValue(Long.parseLong(pprice));
                databaseReference1.child(id).child("category").setValue(record);
                Toast.makeText(getApplicationContext(), "Products Added", Toast.LENGTH_SHORT).show();
                clearTxt();*/
            }
        });
    }
    public void clearTxt(){
        name.setText(null);
        quan.setText(null);
        price.setText(null);
    }
    public void addItem(String pname,String pqty,String pprice,String pdate,String hdate){
        String id = databaseReference1.push().getKey();
        // Products products = new Products(pname, pqty, pprice);
        databaseReference1.child(id).child("farmer").setValue(mailid);
        databaseReference1.child(id).child("productname").setValue(pname);
        databaseReference1.child(id).child("quantity").setValue(Long.parseLong(pqty));
        databaseReference1.child(id).child("price").setValue(Long.parseLong(pprice));
        databaseReference1.child(id).child("category").setValue(record);
        databaseReference1.child(id).child("harvestdate").setValue(pdate);
        databaseReference1.child(id).child("validity").setValue(hdate);
        Toast.makeText(getApplicationContext(), "Products Added", Toast.LENGTH_SHORT).show();
        clearTxt();
    }
}
