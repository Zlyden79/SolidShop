package ru.netology.zlyden.solidshop.entity;

import ru.netology.zlyden.solidshop.GoodsCatalog;
import ru.netology.zlyden.solidshop.Implementations.ConsOutputImpl;
import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id; //идентификатор заказа
    private int positionEnumerator = 0; //хранит id последней добавленной позиции
    private List<OrderPosition> positions = new ArrayList<>(); //хранилище позиций заказа
    private int totalQuantity = 0; //аккумулирует общее количество товаров в заказе
    private double totalCost = 0; // аккумулирует общую цену товаров в заказе

    //конструктор принимает ид заказа
    public Order(int id) {
        this.id = id;
    }

    public boolean addOrderPosition(int goodsId, int quantity) {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        if (goodsCatalog.getById(goodsId) == null) {
            System.out.println("Попытка добавить товар не из справочника");
            return false;
        }
        OrderPosition op = new OrderPosition(getPositionEnumerator(), goodsId, quantity);
        double price = goodsCatalog.getById(goodsId).getPrice();
        positions.add(op);
        recoutTotal();
        return true;
    }

    private void recoutTotal() {
        totalQuantity = 0;
        totalCost = 0;
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        for (OrderPosition op : positions){
            double price = goodsCatalog.getById(op.goodsId).getPrice();
            double quantity = op.getQuantity();
            totalQuantity += quantity;
            totalCost += price * quantity;
        }
    }

    public boolean delOrderPosition(int id) {
        Iterator<OrderPosition> iterator = positions.iterator();
        while (iterator.hasNext()) {
            OrderPosition op = iterator.next();
            if (op.getPositionId() == id) {
                iterator.remove();
                recoutTotal();
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public int getPositionEnumerator() {
        return ++positionEnumerator;
    }

    public List<OrderPosition> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    //отладочная версия
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return "Order{" +
                "id=" + id +
                ", positions=" + positions +
                ", totalQuantity=" + totalQuantity +
                ", totalCost=" + decimalFormat.format(totalCost) +
                '}';
    }

    //красивая печать заказа
    public void print() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        StringBuilder sb = new StringBuilder();
        sb.append("Заказ №").append(id).append("\n");
        sb.append("Позиции:\n");
        for (OrderPosition op : positions) {
            sb.append("  ").append(op.toString()).append("\n");
        }
        sb.append("Итого: количество ").append(totalQuantity).append(" на сумму ").append(decimalFormat.format(totalCost));
        System.out.println(sb.toString());
        ConsOutput consOut = ConsOutputImpl.getInstance();
        consOut.printCuttingLine();
    }
}
