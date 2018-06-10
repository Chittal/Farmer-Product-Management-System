package com.example.cute.farmerapp;

/**
 * Created by Cute on 2/11/2018.
 */
public class RowItem {

    private String product;
    private int id;
    private String Cost;

    public RowItem(String product,int id,String Cost){
        this.product=product;
        this.id=id;
        this.Cost=Cost;
    }

    public String getProduct(){ return product; }

    public void setProduct(String product) { this.product=product; }

    public int getId() { return id; }

    public void setId(int id) {this.id=id;}

    public String getCost() { return Cost; }

    public void setCost(String Cost) { this.Cost=Cost; }
}
