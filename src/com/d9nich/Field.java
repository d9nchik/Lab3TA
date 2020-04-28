package com.d9nich;

import java.io.Serializable;

public class Field implements Serializable, Comparable<Field> {
    private static int defaultKey = 0;
    private final int key;
    private String value;

    public Field(String value) {
        this(defaultKey++, value);
    }

    public Field(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static int getDefaultKey() {
        return defaultKey;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        return getKey() == field.getKey();
    }

    @Override
    public int hashCode() {
        return getKey();
    }

    @Override
    public int compareTo(Field field) {
        return Integer.compare(key, field.key);
    }

    @Override
    public String toString() {
        return "Field{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
