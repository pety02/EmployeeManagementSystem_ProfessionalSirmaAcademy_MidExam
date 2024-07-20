package models;

import java.util.ArrayList;

public class Department {
    private final int id;
    private String name;
    private ArrayList<Employee> employees;

    // This field is removed for simplicity of the application, but
    // in real time application it would be appropriate the department
    // object to know the company that owns it.
    private Company company;

    public Department(int id, String name, ArrayList<Employee> employees, Company company) {
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