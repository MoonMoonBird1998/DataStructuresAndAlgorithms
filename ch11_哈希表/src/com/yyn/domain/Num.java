package com.yyn.domain;

import java.util.Objects;

public class Num {
    private int number;

    public Num(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Num{" +
                "number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Num num = (Num) o;
        return number == num.number;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
