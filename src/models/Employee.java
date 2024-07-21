package models;

import java.time.LocalDate;

/**
 * This class represents an Employee entity with various attributes such as ID,
 * name, family name, start date, end date, department, role, salary, and employment
 * status. It provides methods to get and set these attributes, and overrides the
 * toString method to give a formatted string representation of the employee.
 */
public class Employee {
    private final int id;
    private final String name;
    private final String family;
    private final LocalDate startDate;
    private LocalDate endDate;
    private Department department;
    private Role role;
    private double salary;
    private boolean isFired;

    /**
     * Constructs an Employee with the specified ID, name, family name, department,
     * role, and salary. The start date is set to the current date and the end date
     * is set to null.
     *
     * @param id the ID of the employee
     * @param name the name of the employee
     * @param family the family name of the employee
     * @param department the department of the employee
     * @param role the role of the employee
     * @param salary the salary of the employee
     */
    public Employee(int id, String name, String family, Department department, Role role, double salary) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.startDate = LocalDate.now();
        this.setEndDate(null);
        this.setDepartment(department);
        this.setRole(role);
        this.setSalary(salary);
        this.isFired = false;
    }

    /**
     * Constructs an Employee with the specified ID, name, family name, start date, end
     * date, department, role, and salary.
     *
     * @param id the ID of the employee
     * @param name the name of the employee
     * @param family the family name of the employee
     * @param startDate the start date of the employee
     * @param endDate the end date of the employee
     * @param department the department of the employee
     * @param role the role of the employee
     * @param salary the salary of the employee
     */
    public Employee(int id, String name, String family, LocalDate startDate, LocalDate endDate,
                    Department department, Role role, double salary) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.startDate = startDate;
        this.setEndDate(endDate);
        this.setDepartment(department);
        this.setRole(role);
        this.setSalary(salary);
        this.isFired = false;
    }

    /**
     * Gets the ID of the employee.
     *
     * @return the ID of the employee
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the employee.
     *
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the family name of the employee.
     *
     * @return the family name of the employee
     */
    public String getFamily() {
        return family;
    }

    /**
     * Gets the start date of the employee.
     *
     * @return the start date of the employee
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the employee.
     *
     * @return the end date of the employee
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the employee.
     *
     * @param endDate the new end date of the employee
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the department of the employee.
     *
     * @return the department of the employee
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department of the employee.
     *
     * @param department the new department of the employee
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Gets the role of the employee.
     *
     * @return the role of the employee
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the employee.
     *
     * @param role the new role of the employee
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets the salary of the employee.
     *
     * @return the salary of the employee
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the employee.
     *
     * @param salary the new salary of the employee
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Checks if the employee is fired.
     *
     * @return true if the employee is fired, false otherwise
     */
    public boolean isFired() {
        return isFired;
    }

    /**
     * Marks the employee as fired.
     */
    public void setFired() {
        isFired = true;
    }

    /**
     * Returns a string representation of the employee, which includes the ID, name, family name, start date, end date,
     * department name, role, and salary.
     *
     * @return a formatted string representation of the employee
     */
    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %s, %s, %s, %.2f%n",
                this.id, this.name, this.family, this.startDate, this.endDate,
                this.department.getName(), this.role.toString(), this.salary);
    }
}