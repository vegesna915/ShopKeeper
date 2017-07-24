package com.varma.shopkeeper.shopkeeper.Objects;


import java.util.ArrayList;

public class Sale {

    private String saleDate, saleTime, saleTax, saleDiscount, saleId;
    private ArrayList<SaleItem> saleItems;
    private Long saleSubTotal, saleTotalPrice;


    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public Long getSaleSubTotal() {
        return saleSubTotal;
    }

    public void setSaleSubTotal(Long saleSubTotal) {
        this.saleSubTotal = saleSubTotal;
    }

    public Long getSaleTotalPrice() {
        return saleTotalPrice;
    }

    public void setSaleTotalPrice(Long saleTotalPrice) {
        this.saleTotalPrice = saleTotalPrice;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public String getSaleTax() {
        return saleTax;
    }

    public void setSaleTax(String saleTax) {
        this.saleTax = saleTax;
    }

    public String getSaleDiscount() {
        return saleDiscount;
    }

    public void setSaleDiscount(String saleDiscount) {
        this.saleDiscount = saleDiscount;
    }

    public ArrayList<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(ArrayList<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
}
