package ru.netology.zlyden.solidshop.Implementations;

import ru.netology.zlyden.solidshop.Interfaces.ConsInput;

import java.util.Scanner;

//Singleton
public class ConsInputImpl implements ConsInput {
    private static ConsInputImpl instance = null;
    Scanner scanner = new Scanner(System.in);

    private ConsInputImpl() {
    }



    public static ConsInputImpl getInstance() {
        if (instance == null) instance = new ConsInputImpl();
        return instance;
    }

    @Override
    public int getIntInRange(int min, int max) {
        int value = 0;
        do {
            String userInput = scanner.nextLine();
            try {
                value = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Ожидается целое число от " + min + " до " + max +
                        ", а вы ввели { " + userInput + " }");
                System.out.println("Исправьтесь");
            }
            if ((value < min) || (value > max)) {
                System.out.println("Ввод за пределами дозволенного диапазона [" + min + ", " + max + "]");
            }
        } while ((value < min) || (value > max));
        return value;
    }

        @Override
        public int getInt () {
            int value = 0;
            while (true) {
                String userInput = scanner.nextLine();
                try {
                    value = Integer.parseInt(userInput);
                    return value;
                } catch (NumberFormatException e) {
                    System.out.println("Ожидается целое число, а вы ввели { " + userInput + " }");
                    System.out.println("Исправьтесь");
                }
            }
        }

        @Override
        public double getDouble () {
            double value = 0;
            while (true) {
                String userInput = scanner.nextLine();
                try {
                    value = Double.parseDouble(userInput);
                    return value;
                } catch (NumberFormatException e) {
                    System.out.println("Ожидается дробное число, а вы ввели { " + userInput + " }");
                    System.out.println("Исправьтесь");
                }
            }
        }

        @Override
        public String getString () {
            return scanner.nextLine();
        }
    }
