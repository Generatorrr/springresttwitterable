package com.example.springresttwitterable.entity.enums;

/**
 * Created on 2020-05-12
 *
 * @author generatorr
 */
public enum Severity {
    TRIVIAL(0), MINOR(1), AVERAGE(2), MAJOR(3), CRITICAL(4), BLOCKER(5);

    private int value;
    Severity(int i) {}

    public int getValue() {
        return value;
    }
}
