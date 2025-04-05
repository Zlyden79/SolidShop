package ru.netology.zlyden.solidshop;

import ru.netology.zlyden.solidshop.Implementations.ConsOutputImpl;
import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;
import ru.netology.zlyden.solidshop.entity.Goods;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//Singleton

public final class Warehouse {
    private Map<Integer, Integer> storage; //основное хранилище - ключ - id товара, значение - количество
    private static Warehouse instance = null; // хранит ссылку на единственный экземпляр

    private Warehouse() {
        this.storage = new HashMap<>();
    }

    public static Warehouse getInstance() {
        if (instance == null) instance = new Warehouse();
        return instance;
    }

    //вспомогательный метод - создаёт товарную позицию на складе
    public boolean create(int goodsId, int quantity) {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        //если goodsId нет в справочнике товаров - нафиг с пляжу без панамки
        if (goodsCatalog.getById(goodsId) == null) {
            System.out.println("В справочнике нет такой позиции товара");
            return false;
        }
        //иначе смотрим, есть ли позиция на складе
        if (storage.containsKey(goodsId)) {
            System.out.println("Нельзя создавать дубликат позиции на складе");
            return false;
        }
        storage.put(goodsId, quantity);
        return true;
    }

    //получить количество товара на складе по id товара
    public int getQuantity(int goodsId) {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        //если goodsId нет в справочнике товаров или нет на остатках - возвращаем 0
        if ((goodsCatalog.getById(goodsId) == null) || (!storage.containsKey(goodsId))) return 0;
        return storage.get(goodsId);
    }

    //добавить на остатки quantity товара с goodsId
    public boolean add(int goodsId, int quantity) {
        //если позиции товара на складе нет - то создадим её
        if (!storage.containsKey(goodsId)) {
            return create(goodsId, quantity); //может кончиться неудачей, на совести метода create()
        } else {
            //если есть - получаем количество, прибавляем из параметра и запоминаем в мапу.
            Integer oldQuantity = storage.get(goodsId);
            storage.put(goodsId, oldQuantity + quantity);
            return true;
        }
    }

    //списать со склада продукцию goodsId в количестве quantity
    public boolean substract(int goodsId, int quantity) {
        if (!storage.containsKey(goodsId)) {
            System.out.println("Нет позиции на складе");
            return false;
        }
        Integer oldQuantity = storage.get(goodsId);
        if (oldQuantity < quantity) {
            System.out.println("Необеспеченный расход: " + quantity + ">" + oldQuantity);
            return false;
        }
        storage.put(goodsId, oldQuantity - quantity);
        //если остаток товара ушёл в ноль - позиция со склада удаляется
        if (storage.get(goodsId) == 0) storage.remove(goodsId);
        return true;
    }

    public void searchByGoodsId(int goodsId) {
        Set<Map.Entry<Integer, Integer>> storageEntries = storage.entrySet();
        for (Map.Entry<Integer, Integer> entry : storageEntries) {
            if ((int) entry.getKey() == goodsId) {
                printEntry(entry);
                break;
            }
        }
    }

    public void searchByProducerId(int producerId) {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        Set<Goods> goodsSet = goodsCatalog.searchByProducerId(producerId);
        Set<Integer> goodsIdSet = goodsSet.stream().map(a -> a.getId()).collect(Collectors.toSet());
        Set<Map.Entry<Integer, Integer>> storageEntries = storage.entrySet();
        for (Map.Entry<Integer, Integer> entry : storageEntries) {
            if (goodsIdSet.contains(entry.getKey())){
                printEntry(entry);
            }
        }
    }

    //Печать позиции оперативных остатков склада
    public void printEntry(Map.Entry<Integer, Integer> entry) {
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        int goodsId = entry.getKey();
        int quantity = entry.getValue();
        String goodsName = goodsCatalog.getById(goodsId).getName();
        System.out.println("Товар {" + goodsName + "}, в количестве - " + quantity);
    }

    //печать оперативных остатков склада
    public void print() {
        ConsOutput consOut = ConsOutputImpl.getInstance();
        GoodsCatalog goodsCatalog = GoodsCatalog.getInstance();
        Set<Map.Entry<Integer, Integer>> storageEntries = storage.entrySet();
        System.out.println("Остатки на складе:");
        for (Map.Entry<Integer, Integer> storageEntry : storageEntries) {
            printEntry(storageEntry);
        }
        consOut.printCuttingLine();
    }
}
