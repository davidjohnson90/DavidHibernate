package com.david.ORMappingBag;

import java.util.Collection;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int salary;
    private Collection certificates;

    public Employee() {
    }

    public Employee(String firstName, String lastName, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Collection getCertificates() {
        return certificates;
    }

    public void setCertificates(Collection certificates) {
        this.certificates = certificates;
    }
}
