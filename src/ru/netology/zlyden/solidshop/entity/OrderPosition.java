package ru.netology.zlyden.solidshop.entity;

import ru.netology.zlyden.solidshop.GoodsCatalog;

import java.text.DecimalFormat;

public class OrderPosition {
    protected int positionId;
    protected int goodsId;
    protected int quantity;
    protected double price;
    protected double cost;

    protected OrderPosition(int positionId, int goodsId, int quantity) {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        this.positionId = positionId;
        this.goodsId = goodsId;
        this.quantity = quantity;
        this.price = goodsCatalog.getById(goodsId).getPrice();
        this.cost = countCost();
    }

    private double countCost() {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        if (goodsCatalog.getById(this.goodsId) == null) {
            throw new IllegalArgumentException("Нет такого в справочнике");
        }
        double price = goodsCatalog.getById(goodsId).getPrice();
        return price * quantity;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        String goodsName = goodsCatalog.getById(goodsId).getName();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return "OrderPosition{" +
                "positionId=" + positionId +
                ", goods = " + goodsName +
                ", price = " + price +
                ", quantity=" + quantity +
                ", cost=" + decimalFormat.format(cost) +
                '}';
    }
}
