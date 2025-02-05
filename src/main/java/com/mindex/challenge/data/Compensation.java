package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private int salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public Compensation(int salary, Date effectiveDate) {
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
