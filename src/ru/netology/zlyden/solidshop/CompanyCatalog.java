package ru.netology.zlyden.solidshop;

import ru.netology.zlyden.solidshop.Implementations.ConsOutputImpl;
import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;
import ru.netology.zlyden.solidshop.entity.Company;
import ru.netology.zlyden.solidshop.entity.ResidentCompany;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

//Singleton
public final class CompanyCatalog {
    private Set<Company> catalog; // коллекция объектов Company
    private static CompanyCatalog instance = null; // хранит ссылку на единственный экземпляр
    private int docEnumerator = 0; // хранит id последнего добавленного объекта

    private CompanyCatalog() {
        this.catalog = new HashSet<>();
    }

    public static CompanyCatalog getInstance() {
        if (instance == null) instance = new CompanyCatalog();
        return instance;
    }
    //когда нужен очередной идентификатор организации, инкрементируем docEnumerator и возвращаем его
    public int getDocEnumerator() {
        return ++this.docEnumerator;
    }

    public Company newCompany(String name, String address){
        int id = getDocEnumerator();
        return new Company(id, name, address);
    }

    public ResidentCompany newResidentCompany(String name, String address, String inn, String kpp){
        int id = getDocEnumerator();
        return new ResidentCompany(id, name, address, inn, kpp);
    }

    public ResidentCompany newResidentCompany(String name, String address, String inn, String kpp, String factAddress){
        int id = getDocEnumerator();
        return new ResidentCompany(id, name, address, inn, kpp, factAddress);
    }

    public void add(Company comp) {
        this.catalog.add(comp);
    }

    public Set<Company> getCatalog() {
        return catalog;
    }

    public Company getById(int id) {
        Iterator<Company> iterator = catalog.iterator();
        while (iterator.hasNext()) {
            Company company = iterator.next();
            if (id == (int) company.getId()) {
                return company;
            }
        }
        return null;
    }

    public Set<Company> searchByName(String name) {
        Set<Company> result = new HashSet<>();
        Iterator<Company> iterator = catalog.iterator();
        while (iterator.hasNext()) {
            Company company = iterator.next();
            if (company.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(company);
            }
        }
        if (result.isEmpty()) return null;
        return result;
    }

    public Set<Company> searchByAddress(String addr) {
        Set<Company> result = new HashSet<>();
        Iterator<Company> iterator = catalog.iterator();
        while (iterator.hasNext()) {
            Company comp = iterator.next();
            if (comp.getAddress().toLowerCase().contains(addr.toLowerCase())) {
                result.add(comp);
            }
        }
        if (result.isEmpty()) return null;
        return result;
    }

    //выводит справочник организаций
    public void print() {
        ConsOutput consOut = ConsOutputImpl.getInstance();
        Iterator<Company> iterator = catalog.iterator();
        System.out.println("Справочник организаций:");
        while (iterator.hasNext()) {
            Company comp = iterator.next();
            System.out.println(comp.toString());
        }
        consOut.printCuttingLine();
    }
}
