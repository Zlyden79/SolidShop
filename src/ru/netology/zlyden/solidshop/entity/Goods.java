package ru.netology.zlyden.solidshop.entity;

import ru.netology.zlyden.solidshop.CompanyCatalog;

import java.util.Objects;
import java.util.Set;

//справочник товаров
public class Goods {
    protected final int id; //идентификатор товара
    protected String name; // название товара
    protected int producerId; // ид производителя
    protected double price; //цена

    public Goods(int id, String name, int producerId, double price) {
        CompanyCatalog companyCatalog = CompanyCatalog.getInstance();
        if (companyCatalog.getById(producerId) == null) {
            throw new IllegalArgumentException("Попытка создать товар с несуществующим производителем");
        }
        this.id = id;
        this.name = name;
        this.producerId = producerId;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return id == goods.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        String producer;

        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", producerId=" + producerId +
                ", price=" + price +
                '}';
    }
}
