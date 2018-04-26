package com.zundrel.moneyworks.api;

public class Denomination {
    private float value;

    public Denomination(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
