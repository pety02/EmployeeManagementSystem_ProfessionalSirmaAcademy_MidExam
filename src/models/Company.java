package models;

import java.util.List;

/**
 * This class represents a Company entity with an ID, name, and list of departments.
 * It provides methods to get and set the company's name and departments, and overrides
 * the toString method to give a formatted string representation of the company.
 */
public class Company {
    private final int id;
    private String name;
    private List<Department> departments;

    /**
     * Constructs a Company with the specified name and list of departments.
     * The company's ID is automatically generated.
     *
     * @param name the name of the company
     * @param departments the list of departments in the company
     */
    public Company(String name, List<Department> departments) {
        this.id = 0;
        this.setName(name);
        this.setDepartments(departments);
    }

    /**
     * Constructs a Company with the specified ID, name, and list of departments.
     * This constructor allows for setting a specific ID, useful for scenarios like loading from a database.
     *
     * @param id the ID of the company
     * @param name the name of the company
     * @param departments the list of departments in the company
     */
    public Company(int id, String name, List<Department> departments) {
        this.id = id;
        this.setName(name);
        this.setDepartments(departments);
    }

    /**
     * Gets the ID of the company.
     *
     * @return the ID of the company
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the company.
     *
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     *
     * @param name the new name of the company
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of departments in the company.
     *
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments in the company.
     *
     * @param departments the new list of departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Returns a string representation of the company, which includes the ID and name.
     *
     * @return a formatted string representation of the company
     */
    @Override
    public String toString() {
        return String.format("%d, %s%n", this.id, this.name);
    }
}