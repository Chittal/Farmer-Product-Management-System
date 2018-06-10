package com.example.cute.farmerapp;

/**
 * Created by Cute on 3/10/2018.
 */
public class Users {
    String name,phone,address,city,pincode,email,pass;

    public Users(String name,String phone,String address,String city,String pincode,String email,String pass){
        this.name=name;
        this.phone=phone;
        this.address=address;
        this.city=city;
        this.pincode=pincode;
        this.email=email;
        this.pass=pass;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getCity(){
        return this.city;
    }
    public void setCity(String city){
        this.city=city;
    }
    public String getPincode(){
        return this.pincode;
    }
    public void setPincode(String pincode){
        this.pincode=pincode;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getPass(){
        return this.pass;
    }
    public void setPass(String pass){
        this.pass=pass;
    }
}
