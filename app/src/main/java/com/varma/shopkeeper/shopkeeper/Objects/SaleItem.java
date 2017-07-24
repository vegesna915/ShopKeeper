package com.varma.shopkeeper.shopkeeper.Objects;


public class SaleItem {

    private String itemId, itemName, itemBrand, itemSize;
    private long itemUnitPrice, itemQty, itemPrice, itemProfit;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public long getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(long itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public long getItemQty() {
        return itemQty;
    }

    public void setItemQty(long itemQty) {
        this.itemQty = itemQty;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getItemProfit() {
        return itemProfit;
    }

    public void setItemProfit(long itemProfit) {
        this.itemProfit = itemProfit;
    }
}
