package com.itba.eda.Jefe;

public class Jefe {
    private String name;
    private int age;

    public Jefe(String value) {
        try {
            String[] arrOfStr = value.split(":");
            if (arrOfStr.length != 2)
                throw new IllegalArgumentException();
            if (!arrOfStr[0].startsWith("(") || !arrOfStr[1].endsWith(")"))
                throw new IllegalArgumentException();
            name = arrOfStr[0].substring(1);
            age = Integer.parseInt(arrOfStr[1].substring(0,
                    arrOfStr[1].length() - 1));
        } catch (Exception e) {
            name = value;
            age = Integer.MIN_VALUE;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s:%d)", name, age);
    }
}
