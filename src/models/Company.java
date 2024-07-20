package models;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

public class Company {
    private static final AtomicInteger counter = new AtomicInteger();
    private final int id;
    private String name;
    private List<Department> departments;

    public Company(String name, List<Department> departments) {
        this.id = counter.incrementAndGet();
        this.setName(name);
        this.setDepartments(departments);
    }

    public Company(int id, String name, List<Department> departments) {
        this.id = id;
        this.setName(name);
        this.setDepartments(departments);
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return String.format("%d, %s%n", this.id, this.name);
    }
}