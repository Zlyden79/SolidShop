package ru.netology.zlyden.solidshop.Implementations;

import ru.netology.zlyden.solidshop.Interfaces.ConsOutput;

//Singleton
public class ConsOutputImpl implements ConsOutput {
    private static ConsOutputImpl instance = null;

    private ConsOutputImpl(){};

    public static ConsOutputImpl getInstance(){
        if (instance == null) instance = new ConsOutputImpl();
        return instance;
    }

    @Override
    public void printCuttingLine() {
        System.out.println(REPEATSTRING.repeat(REPEATCOUNT));
    }
}
