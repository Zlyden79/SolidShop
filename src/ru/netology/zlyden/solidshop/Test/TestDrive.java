package ru.netology.zlyden.solidshop.Test;

import ru.netology.zlyden.solidshop.CompanyCatalog;
import ru.netology.zlyden.solidshop.GoodsCatalog;
import ru.netology.zlyden.solidshop.Implementations.ConsInputImpl;
import ru.netology.zlyden.solidshop.Implementations.ConsOutputImpl;
import ru.netology.zlyden.solidshop.Interfaces.ConsInput;
import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;
import ru.netology.zlyden.solidshop.OrderCatalog;
import ru.netology.zlyden.solidshop.Warehouse;
import ru.netology.zlyden.solidshop.entity.Company;
import ru.netology.zlyden.solidshop.entity.Goods;
import ru.netology.zlyden.solidshop.entity.Order;

public class TestDrive {

    public static void main(String[] args) {
        ConsInput consInp = ConsInputImpl.getInstance();
        ConsOutput consOut = ConsOutputImpl.getInstance();

        CompanyCatalog companyCatalog = CompanyCatalog.getInstance();
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        Warehouse warehouse = Warehouse.getInstance();
        OrderCatalog orderCatalog = OrderCatalog.getInstance();

        //Метод инициализации магазина
        initShop();

        //Попытка создать товар с несуществующим производителем
        try {
            goodsCatalog.add(new Goods(goodsCatalog.getDocEnumerator(), "Водка Честная 0,375 л", 6, 359.99));
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
        // HashSet не добавит компанию с уже существующим id:
        Company duplicate = new Company(2, "Уильям Грат энд Санс", "Великобритания");
        companyCatalog.add(duplicate);

        //вывод справочников на печать
        companyCatalog.print();
        goodsCatalog.print();

        //демонстрация поиска
        //по идентификаторам:
        System.out.println("Поиск в справочниках по идентификаторам");
        System.out.println(companyCatalog.getById(1));
        System.out.println(goodsCatalog.getById(1));
        consOut.printCuttingLine();
        //По ключевому слову в названии
        System.out.println("Поиск в справочиках по ключевым словам: название производителя, адрес производителя, наименование товара:");
        System.out.println(companyCatalog.searchByName("Башспирт"));
        System.out.println(companyCatalog.searchByAddress("ирла"));
        System.out.println(goodsCatalog.searchByName("лик"));
        consOut.printCuttingLine();
        //поиск продукции по producerId
        System.out.println("Продукция производителя по producerId:");
        System.out.println(goodsCatalog.searchByProducerId(1)); // 2 позиции
        System.out.println(goodsCatalog.searchByProducerId(6)); // нет такого
        consOut.printCuttingLine();
        //Выводим на печать содержимое склада
        warehouse.print();
        //получить количество товара на остатках по id товара
        System.out.println("Получить количество товара на остатках по id товара:");
        System.out.println(warehouse.getQuantity(2)); //120
        System.out.println(warehouse.getQuantity(5)); // 0, так как нет на остатках

        //манипуляции с остатками
        System.out.println("Вычитаем, удаляем...");
        System.out.println(warehouse.add(1, 12)); //true
        System.out.println(warehouse.add(6, 12)); //false
        System.out.println(warehouse.substract(1, 36)); //true
        warehouse.print(); // тут товар goodsId=1 должен исчезнуть с остатков
        System.out.println(warehouse.add(1, 36)); //true
        System.out.println(warehouse.create(1, 36)); //false - нельзя создавать дубликат позиции на складе
        System.out.println(warehouse.add(6, 120)); //false - 6 нет в справочнике
        System.out.println(warehouse.substract(1, 40)); //false - необеспеченный расход
        System.out.println(warehouse.substract(8, 40)); //false - 5 нет в справочнике
        warehouse.print();
        consOut.printCuttingLine();
        System.out.println("Тестируем поиск в оперативных остатках:");
        System.out.print("по ид товара: ");
        warehouse.searchByGoodsId(3);
        System.out.println("по ид производителя: ");
        warehouse.searchByProducerId(1);
        consOut.printCuttingLine();

        //создаём заказ
        Order order1 = orderCatalog.newOrder();//
        System.out.println(order1.addOrderPosition(3, 3));//true
        System.out.println(order1.addOrderPosition(5, 2));//true
        System.out.println(order1.addOrderPosition(2, 1));//true
        orderCatalog.add(order1);
        //смотрим как он печатается
        consOut.printCuttingLine();
        System.out.println(order1);
        order1.print();

        //ну ка снесём позицию в заказе
        System.out.println(order1.delOrderPosition(2));
        order1.print();

        //снова добавим позицию в заказ
        System.out.println(order1.addOrderPosition(5, 2));//true
        order1.print();
        consOut.printCuttingLine();
        orderCatalog.print();

        //нарисуем ка второй заказ и поглядим
        Order order2 = orderCatalog.newOrder();//
        System.out.println(order2.addOrderPosition(3, 300));//true
        System.out.println(order2.addOrderPosition(1, 3 ));//true
        System.out.println(order2.addOrderPosition(5, 2));//true
        orderCatalog.add(order2);
        order2.print();
        orderCatalog.print();
        //
        System.out.println("Попытка проводки заказов:");
        System.out.println(orderCatalog.productOrder(order1));
        System.out.println(orderCatalog.productOrder(order2));
        orderCatalog.print();
        warehouse.print();
        System.out.println("Введите целое число");
        System.out.println(consInp.getInt());
        System.out.println("Введите целое число от 1 до 4 включительно");
        System.out.println(consInp.getIntInRange(1, 4));

    }


    public static void initShop() {
        CompanyCatalog companyCatalog = CompanyCatalog.getInstance();
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        Warehouse warehouse = Warehouse.getInstance();

        //чуть заполним справочник организаций
        companyCatalog.add(companyCatalog.newCompany("Diageo", "Ирландия"));
        companyCatalog.add(companyCatalog.newResidentCompany(
                "АО Башспирт",
                "Россия, Республика Башкортостан, г.Уфа, ул.Ветошникова, 97",
                "0276100884",
                "026832002",
                "Россия, Респ. Башкортостан, г. Стерлитамак, ул. Аэродромная, 12"));
        companyCatalog.add(companyCatalog.newResidentCompany(
                "АО Башспирт",
                "Россия, Республика Башкортостан, г.Уфа, ул.Ветошникова, 97",
                "0276100884",
                "025745001",
                "Россия, Респ. Башкортостан, г. Бирск, ул. Мира, 33"));
        companyCatalog.add(companyCatalog.newCompany("Уильям Грат энд Санс", "Великобритания"));
        //добавим немного товаров
        goodsCatalog.add(new Goods(goodsCatalog.getDocEnumerator(), "Ликер эмульсионный Бэйлис сливочный оригинальный 0,7 л", 1, 1699.99));
        goodsCatalog.add(new Goods(goodsCatalog.getDocEnumerator(), "Водка Сталковская Альфа 0,375 л", 2, 329.99));
        goodsCatalog.add(new Goods(goodsCatalog.getDocEnumerator(), "Ликер эмульсионный Бэйлис сливочный оригинальный 0,5 л", 1, 1299.99));
        goodsCatalog.add(new Goods(goodsCatalog.getDocEnumerator(), "Водка Честная 0,375 л", 3, 359.99));
        goodsCatalog.add(new Goods(goodsCatalog.getDocEnumerator(), "Виски Талламор Дью 0,7 л", 4, 2599.00));

        //добавим на склад продукцию
        warehouse.add(1, 24);
        warehouse.add(2, 120);
        warehouse.add(3, 24);
        warehouse.add(4, 120);
        warehouse.add(5, 12);
        System.out.println("Инициализация закончена");
    }
}
