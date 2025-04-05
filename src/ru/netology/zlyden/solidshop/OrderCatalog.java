package ru.netology.zlyden.solidshop;

import ru.netology.zlyden.solidshop.Implementations.ConsOutputImpl;
import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;
import ru.netology.zlyden.solidshop.entity.Order;
import ru.netology.zlyden.solidshop.entity.OrderPosition;
import ru.netology.zlyden.solidshop.entity.OrderStatus;

import java.util.*;

//Singleton
public class OrderCatalog {
    private Map<Order, OrderStatus> catalog; // коллекция объектов Order
    private static OrderCatalog instance = null; // хранит ссылку на единственный экземпляр
    private int docEnumerator; // хранит id последнего добавленного объекта

    private OrderCatalog() {
        catalog = new HashMap<>();
    }

    public static OrderCatalog getInstance() {
        if (instance == null) instance = new OrderCatalog();
        return instance;
    }

    public int getDocEnumerator() {
        return ++this.docEnumerator;
    }

    public Order newOrder() {
        int id = this.getDocEnumerator();
        return new Order(id);
    }

    public Map<Order, OrderStatus> getCatalog() {
        return catalog;
    }

    public void add(Order order) {
        catalog.put(order, OrderStatus.NEW);
    }

    public Order getById(int id){
        Set<Order> keySet = catalog.keySet();
        Iterator<Order> iterator = keySet.iterator();
        while (iterator.hasNext()){
            Order order = iterator.next();
            if (order.getId() == id) return order;
        }
        return null;
    }

    public void print(){
        ConsOutput consOut = ConsOutputImpl.getInstance();
        Set<Map.Entry<Order, OrderStatus>> entries = catalog.entrySet();
        System.out.println("Список заказов:");
        for (Map.Entry<Order, OrderStatus> entry : entries) {
            int id = entry.getKey().getId();
            OrderStatus status = entry.getValue();
            System.out.println("Заказ №" + id + " в статусе " + status.toString());
        }
        consOut.printCuttingLine();
    }
    //вспомогательный метод - проверяет, есть ли на остатках товар из позиций заказа
    public boolean canProduct(Order order) {
        List<OrderPosition> positions = order.getPositions();
        Warehouse warehouse = Warehouse.getInstance();
        for (OrderPosition op : positions) {
            int stockQuantity = warehouse.getQuantity(op.getGoodsId());
            if ((stockQuantity == 0) || (op.getQuantity() > stockQuantity)) return false;
        }
        return true;
    }

    //метод проведения заказа
    public boolean productOrder(Order order){
        Warehouse warehouse = Warehouse.getInstance();
        //если проводка возможна - уменьшаем остатки на складе и переводим статус заказа в Проведено
        if (canProduct(order)){
            List<OrderPosition> positions = order.getPositions();
            for (OrderPosition op : positions) {
                warehouse.substract( op.getGoodsId(), op.getQuantity());
            }
            catalog.put(order, OrderStatus.POSTED);
            return true;
        }
        //иначе статус заказа - отказано, возвращаем false
        System.out.println("Заказ провести невозможно");
        catalog.put(order, OrderStatus.REJECTED);
        return false;
     }
}
