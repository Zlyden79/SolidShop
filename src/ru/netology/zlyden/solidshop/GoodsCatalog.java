package ru.netology.zlyden.solidshop;

import ru.netology.zlyden.solidshop.Implementations.ConsOutputImpl;
import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;
import ru.netology.zlyden.solidshop.entity.Goods;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Singleton
public final class GoodsCatalog {
    private Set<Goods> catalog; // коллекция объектов Goods
    private static GoodsCatalog instance = null; // хранит ссылку на единственный экземпляр
    private int docEnumerator; // хранит id последнего добавленного объекта

    private GoodsCatalog() {
        this.catalog = new HashSet<>();
    }

    public static GoodsCatalog getInstance() {
        if (instance == null) instance = new GoodsCatalog();
        return instance;
    }

    //когда нужен очередной идентификатор организации, инкрементируем docEnumerator и возвращаем его
    public int getDocEnumerator() {
        return ++this.docEnumerator;
    }

    public Set<Goods> getCatalog() {
        return catalog;
    }

    public void add(Goods goods){
        this.catalog.add(goods);
    }

    public Goods getById(int id){
        Iterator<Goods> iterator = catalog.iterator();
        while (iterator.hasNext()) {
            Goods goods = iterator.next();
            if (id == (int) goods.getId()){
                return goods;
            }
        }
        return null;
    }

    public Set<Goods> searchByProducerId (int producerId){
        CompanyCatalog companyCatalog = CompanyCatalog.getInstance();
        if (companyCatalog.getById(producerId) == null) {
            System.out.println("В справочнике нет такого производителя");
            return null;
        }
        Set<Goods> result = new HashSet<>();
        Iterator<Goods> iterator = catalog.iterator();
        while (iterator.hasNext()) {
            Goods goods = iterator.next();
            if (goods.getProducerId() == producerId) {
                result.add(goods);
            }
        }
        if (result.isEmpty()) return null;
        return result;
    }

    public Set<Goods> searchByName(String name) {
        Set<Goods> result = new HashSet<>();
        Iterator<Goods> iterator = catalog.iterator();
        while (iterator.hasNext()) {
            Goods goods = iterator.next();
            if (goods.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(goods);
            }
        }
        if (result.isEmpty()) return null;
        return result;
    }
    //выводит справочник товаров
    public void print(){
        ConsOutput consOut = ConsOutputImpl.getInstance();
        Iterator<Goods> iterator = catalog.iterator();
        System.out.println("Справочник товаров:");
        while(iterator.hasNext()){
            Goods goods = iterator.next();
            System.out.println(goods.toString());
        }
        consOut.printCuttingLine();
    }
}
