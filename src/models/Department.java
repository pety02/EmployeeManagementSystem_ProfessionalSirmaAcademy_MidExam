package models;

import java.util.ArrayList;

/**
 *
 */
public class Department {
    private final int id;
    private String name;
    private ArrayList<Employee> employees;

    // This field is removed for simplicity of the application, but
    // in real time application it would be appropriate the department
    // object to know the company that owns it.
    private Company company;

    /**
     *
     * @param id
     * @param name
     * @param employees
     * @param company
     */
    public Department(int id, String name, ArrayList<Employee> employees, Company company) {
        this.id = id;
        this.setName(name);
        this.setEmployees(employees);
        this.setCompany(company);
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     *
     * @param employees
     */
    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {

        return String.format("%d, %s%n", this.id, this.name);
    }

    /**
     *
     * @return
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * @param company
     */
    public void setCompany(Company company) {
        this.company = company;
    }
}