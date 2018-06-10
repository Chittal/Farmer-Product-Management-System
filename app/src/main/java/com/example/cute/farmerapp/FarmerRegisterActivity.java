package com.example.cute.farmerapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FarmerRegisterActivity extends AppCompatActivity {

    EditText uname,phone,address,city,pincode,emailid,password,confirm;
    Button submit;
    //Spinner sptype;
    String record;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_register);

        auth = FirebaseAuth.getInstance();

        uname = (EditText) findViewById(R.id.runame);
        phone = (EditText) findViewById(R.id.rphone);
        address = (EditText) findViewById(R.id.radd);
        city = (EditText) findViewById(R.id.rcity);
        pincode = (EditText) findViewById(R.id.rpin);
        emailid = (EditText) findViewById(R.id.remail);
        password = (EditText) findViewById(R.id.rpass);
        confirm = (EditText) findViewById(R.id.rcpass);

        submit = (Button) findViewById(R.id.regsub);

        databaseReference=FirebaseDatabase.getInstance().getReference("Users");

        /*sptype = (Spinner) findViewById(R.id.rtype);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(FarmerRegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptype.setAdapter(myAdapter);

        sptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        record = "Consumer";
                        break;
                    case 1:
                        record = "Farmer";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sun = uname.getText().toString().trim();
                String sph = phone.getText().toString().trim();
                String sa = address.getText().toString().trim();
                String sc = city.getText().toString().trim();
                String spin = pincode.getText().toString().trim();
                String se = emailid.getText().toString().trim();
                //String st= sptype.getSelectedItem().toString();
                String spass=password.getText().toString().trim();
                String scpass=confirm.getText().toString().trim();
                if (TextUtils.isEmpty(sun)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sph)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(sa)) {
                    Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(sc)) {
                    Toast.makeText(getApplicationContext(), "Enter city!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(spin)) {
                    Toast.makeText(getApplicationContext(), "Enter pincode!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(se)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(spass)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!spass.equals(scpass)){
                    Toast.makeText(getApplicationContext(), "Both passwords does not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (sph.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(se, spass)
                        .addOnCompleteListener(FarmerRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent=new Intent(FarmerRegisterActivity.this, BuyerHome.class);
                                    // intent.putExtra("mail",email);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


                String id=databaseReference.push().getKey();
                Users users=new Users(sun,sph,sa,sc,spin,se,spass);
                databaseReference.child(id).child("name").setValue(sun);
                databaseReference.child(id).child("phone").setValue(sph);
                databaseReference.child(id).child("address").setValue(sa);
                databaseReference.child(id).child("city").setValue(sc);
                databaseReference.child(id).child("pincode").setValue(spin);
                databaseReference.child(id).child("email").setValue(se);
                //databaseReference.child(id).child("Type").setValue(st);
                databaseReference.child(id).child("password").setValue(spass);
                Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                clearTxt();
            }
        });
    }
    private void clearTxt(){
        uname.setText(null);
        password.setText(null);
        phone.setText(null);
        address.setText(null);
        city.setText(null);
        pincode.setText(null);
        emailid.setText(null);
        confirm.setText(null);
    }
}
