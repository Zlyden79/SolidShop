package ru.netology.zlyden.solidshop.Interfaces;

public interface ConsOutput {
    public static final String  REPEATSTRING = "-*-"; //символ для печати "отрывных" строчек
    public static final int REPEATCOUNT = 20; //количество повторений символа

    void printCuttingLine();
}
