package models;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Department {
    private static final AtomicInteger counter = new AtomicInteger();
    private final int id;
    private String name;
    private ArrayList<Employee> employees;

    public Department(String name, ArrayList<Employee> employees) {
        this.id = counter.incrementAndGet();
        this.setName(name);
        this.setEmployees(employees);
    }

    public Department(int id, String name, ArrayList<Employee> employees) {
        this.id = id;
        this.setName(name);
        this.setEmployees(employees);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {

        return String.format("%d, %s%n", this.id, this.name);
    }
}