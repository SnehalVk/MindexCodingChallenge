/*
 * This file was created by Snehal Kulkarni for solving
 * the task 2.
 */

package com.mindex.challenge.data;

import java.time.LocalDate;

public class Compensation {

    private Employee employee;
    private int salary;
    private LocalDate effectiveDate;

    // Getter and setter methods.
    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Compensation() {
        super();
    }

    public Compensation(Employee employee, int salary, LocalDate effectiveDate) {
        super();
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "Compensation [employee=" + employee.getFirstName() +" "+ employee.getLastName()
                + ", salary=" + salary + ", effectiveDate=" + effectiveDate + "]";
    }

}
