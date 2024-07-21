package models;

import java.util.ArrayList;

/**
 * This class represents a Department entity with an ID, name, list of employees, and
 * an associated company. It provides methods to get and set the department's name,
 * employees, and company, and overrides the toString method to give a formatted string
 * representation of the department.
 */
public class Department {
    private final int id;
    private String name;
    private ArrayList<Employee> employees;
    private Company company;

    /**
     * Constructs a Department with the specified ID, name, list of employees, and company.
     *
     * @param id the ID of the department
     * @param name the name of the department
     * @param employees the list of employees in the department
     * @param company the company that owns the department
     */
    public Department(int id, String name, ArrayList<Employee> employees, Company company) {
        this.id = id;
        this.setName(name);
        this.setEmployees(employees);
        this.setCompany(company);
    }

    /**
     * Gets the ID of the department.
     *
     * @return the ID of the department
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the department.
     *
     * @return the name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the department.
     *
     * @param name the new name of the department
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees in the department.
     *
     * @return the list of employees
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the department.
     *
     * @param employees the new list of employees
     */
    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Returns a string representation of the department, which includes the ID and name.
     *
     * @return a formatted string representation of the department
     */
    @Override
    public String toString() {
        return String.format("%d, %s%n", this.id, this.name);
    }

    /**
     * Gets the company that owns the department.
     *
     * @return the company that owns the department
     */
    public Company getCompany() {
        return company;
    }

    /**
     * Sets the company that owns the department.
     *
     * @param company the new company that owns the department
     */
    public void setCompany(Company company) {
        this.company = company;
    }
}