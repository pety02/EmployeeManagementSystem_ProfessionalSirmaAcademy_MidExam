package models;

import java.time.LocalDate;

/**
 *
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
     *
     * @param id
     * @param name
     * @param family
     * @param department
     * @param role
     * @param salary
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
     *
     * @param id
     * @param name
     * @param family
     * @param startDate
     * @param endDate
     * @param department
     * @param role
     * @param salary
     */
    public Employee(int id, String name, String family, LocalDate startDate, LocalDate endDate, Department department, Role role, double salary) {
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
     * @return
     */
    public String getFamily() {
        return family;
    }

    /**
     *
     * @return
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     *
     * @return
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public Department getDepartment() {
        return department;
    }

    /**
     *
     * @param department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     *
     * @return
     */
    public Role getRole() {
        return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     *
     * @return
     */
    public double getSalary() {
        return salary;
    }

    /**
     *
     * @param salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     *
     * @return
     */
    public boolean isFired() {
        return isFired;
    }

    /**
     *
     */
    public void setFired() {
        isFired = true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %s, %s, %s, %.2f%n",
                this.id, this.name, this.family, this.startDate, this.endDate,
                this.department.getName(), this.role.toString(), this.salary);
    }
}