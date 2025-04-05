package ru.netology.zlyden.solidshop.entity;

public class ResidentCompany extends Company{
    protected String inn;
    protected String kpp;
    protected String factAddress;

    //конструктор с минимальным набором параметров
    public ResidentCompany(int id, String name, String address) {
        super(id, name, address);
    }

    //конструктор с максимальным набором параметров
    public ResidentCompany(int id, String name, String address, String inn, String kpp, String factAddress) {
        super(id, name, address);
        this.inn = inn;
        this.kpp = kpp;
        this.factAddress = factAddress;
    }

    //конструктор, где фактический адрес проставляется юридическим
    public ResidentCompany(int id, String name, String address, String inn, String kpp) {
        super(id, name, address);
        this.kpp = kpp;
        this.inn = inn;
        this.factAddress = address;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getFactAddress() {
        return factAddress;
    }

    public void setFactAddress(String factAddress) {
        this.factAddress = factAddress;
    }

    @Override
    public String toString() {
        return "ResidentCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", address='" + address + '\'' +
                ", factAddress='" + factAddress + '\'' +
                '}';
    }
}
