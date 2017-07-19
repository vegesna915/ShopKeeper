package com.varma.shopkeeper.shopkeeper.Objects;


public class Item {

    private String itemCode, itemName, itemBrandName, itemSize,
            itemActualPrice, itemProfit, itemTax, itemOtherTax, itemSalePrice, itemIsUpdatedToSever;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemSalePrice() {
        return itemSalePrice;
    }

    public void setItemSalePrice(String itemSalePrice) {
        this.itemSalePrice = itemSalePrice;
    }

    public String getItemTax() {
        return itemTax;
    }

    public void setItemTax(String itemTax) {
        this.itemTax = itemTax;
    }


    public String getItemIsUpdatedToSever() {
        return itemIsUpdatedToSever;
    }

    public void setItemIsUpdatedToSever(String itemIsUpdatedToSever) {
        this.itemIsUpdatedToSever = itemIsUpdatedToSever;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemBrandName() {
        return itemBrandName;
    }

    public void setItemBrandName(String itemBrandName) {
        this.itemBrandName = itemBrandName;
    }


    public String getItemActualPrice() {
        return itemActualPrice;
    }

    public void setItemActualPrice(String itemActualPrice) {
        this.itemActualPrice = itemActualPrice;
    }

    public String getItemProfit() {
        return itemProfit;
    }

    public void setItemProfit(String itemProfit) {
        this.itemProfit = itemProfit;
    }

    public String getItemOtherTax() {
        return itemOtherTax;
    }

    public void setItemOtherTax(String itemOtherTax) {
        this.itemOtherTax = itemOtherTax;
    }
}
