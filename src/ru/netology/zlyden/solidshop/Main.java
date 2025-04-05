package ru.netology.zlyden.solidshop;

import ru.netology.zlyden.solidshop.Implementations.ConsInputImpl;
import ru.netology.zlyden.solidshop.Implementations.ConsOutputImpl;
import ru.netology.zlyden.solidshop.Interfaces.ConsInput;
import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;
import ru.netology.zlyden.solidshop.entity.Goods;
import ru.netology.zlyden.solidshop.entity.Order;

public class Main {
    public static final int FIRSTMENUITEM = 1;
    public static final int LASTMENUITEM = 14;
    /*
    у нас тут Singletonы есть
    //контроль консольного ввода
    ConsInput consInp = ConsInputImpl.getInstance();
    //Вывод строки отреза
    ConsOutput consOut = ConsOutputImpl.getInstance();
    //справочник организаций (производителей в данном случае)
    CompanyCatalog companyCatalog = CompanyCatalog.getInstance();
    //справочник товаров
    GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
    //Оперативные остатки склада
    Warehouse warehouse = Warehouse.getInstance();
    //список заказов
    OrderCatalog orderCatalog = OrderCatalog.getInstance();
    */

    public static void main(String[] args) {
        //начальное заполнение справочников производителей, товаров и остатков на складе
        initShop();
        //Показываем главное меню
        while (true) {
            mainMenu();
        }
    }

    private static void mainMenu() {
        CompanyCatalog companyCatalog = CompanyCatalog.getInstance();
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        Warehouse warehouse = Warehouse.getInstance();
        OrderCatalog orderCatalog = OrderCatalog.getInstance();

        ConsOutput consOut = ConsOutputImpl.getInstance();
        consOut.printCuttingLine();
        System.out.println("1. Справочник производителей - вывести весь");
        System.out.println("2. Справочник производителей - поиск по названию");
        System.out.println("3. Справочник производителей - поиск по адресу");
        consOut.printCuttingLine();
        System.out.println("4. Справочник товаров - вывести весь");
        System.out.println("5. Справочник товаров - поиск по названию");
        System.out.println("6. Справочник товаров - поиск по производитлю ");
        consOut.printCuttingLine();
        System.out.println("7. Оперативные остатки - вывести все");
        System.out.println("8. Оперативные остатки - поиск по товару");
        System.out.println("9. Оперативные остатки - поиск по производителю");
        consOut.printCuttingLine();
        System.out.println("10. Заказы - создать заказ");
        System.out.println("11. Заказы - распечатать заказ");
        System.out.println("12. Заказы - провести заказ");
        System.out.println("13. Заказы - статусы заказов");
        consOut.printCuttingLine();
        System.out.println("14. Выход");
        consOut.printCuttingLine();

        ConsInput consInp = ConsInputImpl.getInstance();
        int input = consInp.getIntInRange(FIRSTMENUITEM, LASTMENUITEM);

        switch (input) {
            case 1:
                companyCatalog.print();
                break;
            case 2:
                System.out.println("Введите часть названия:");
                String name = consInp.getString();
                System.out.println(companyCatalog.searchByName(name));
                break;
            case 3:
                System.out.println("Введите часть адреса:");
                String address = consInp.getString();
                System.out.println(companyCatalog.searchByAddress(address));
                break;
            case 4:
                goodsCatalog.print();
                break;
            case 5:
                System.out.println("Введите часть названия:");
                name = consInp.getString();
                System.out.println(goodsCatalog.searchByName(name));
                break;
            case 6:
                System.out.println("Введите id производителя:");
                int produserId = consInp.getIntInRange(1, companyCatalog.getCatalog().size());
                System.out.println(goodsCatalog.searchByProducerId(produserId));
                break;
            case 7:
                warehouse.print();
                break;
            case 8:
                System.out.println("Введите id товара:");
                int goodsId = consInp.getIntInRange(1, goodsCatalog.getCatalog().size());
                warehouse.searchByGoodsId(goodsId);
                break;
            case 9:
                System.out.println("Введите id производителя:");
                produserId = consInp.getIntInRange(1, companyCatalog.getCatalog().size());
                warehouse.searchByProducerId(produserId);
                break;
            case 10:
                orderMenuItem();
                break;
            case 11:
                if (orderCatalog.getCatalog().size() == 0) {
                    System.out.println("Заказов нет, печатать нЕчего");
                    break;
                }
                System.out.println("Введите id заказа:");
                int orderId = consInp.getIntInRange(1, orderCatalog.getCatalog().size());
                Order order = orderCatalog.getById(orderId);
                order.print();
                break;
            case 12:
                System.out.println("Введите id заказа:");
                orderId = consInp.getIntInRange(1, orderCatalog.getCatalog().size());
                order = orderCatalog.getById(orderId);
                System.out.println(orderCatalog.productOrder(order));
                break;
            case 13:
                orderCatalog.print();
                break;
            case 14:
                System.out.println("Завершение работы программы по воле пользователя");
                System.exit(0);
                break;
        }
    }

    private static void orderMenuItem() {
        ConsInput consInp = ConsInputImpl.getInstance();
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();

        System.out.println("Создаём заказ");
        OrderCatalog orderCatalog = OrderCatalog.getInstance();
        Order order = orderCatalog.newOrder();
        //в заказе должна быть минимум одна позиция, поэтому цикл do while
        do {
            System.out.println("Введите id товара:");
            int goodsId = consInp.getIntInRange(1, goodsCatalog.getCatalog().size());
            System.out.println("Введите количество товара:");
            int quantity = consInp.getInt();
            order.addOrderPosition(goodsId, quantity);
            System.out.println("Добавить ещё позицию? \"y\" = Yes / AnythingElse = No");
            String answer = consInp.getString().toLowerCase();
            if (!"y".equals(answer)) {
                break;
            }
        } while (true);
        orderCatalog.add(order);
        order.print();
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
        System.out.println("Добро пожаловать в Java-магазин без использования SQL СУБД");
        System.out.println("Для навигации по меню используйте цифры");
        System.out.println("Для поиска в справочниках и создания заказов придётся использовать идентификаторы из справочников");
    }
}
