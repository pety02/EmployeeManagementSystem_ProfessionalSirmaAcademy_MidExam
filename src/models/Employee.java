package models;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Employee {
    private int id;
    private final String name;
    private final String family;
    private final LocalDate startDate;
    private LocalDate endDate;
    private Department department;
    private Role role;
    private double salary;
    private boolean isFired;

    public Employee(String name, String family, Department department, Role role, double salary) {
        this.setId(0);
        this.name = name;
        this.family = family;
        this.startDate = LocalDate.now();
        this.setEndDate(null);
        this.setDepartment(department);
        this.setRole(role);
        this.setSalary(salary);
        this.isFired = false;
    }

    public Employee(int id, String name, String family, LocalDate startDate, LocalDate endDate, Department department, Role role, double salary) {
        this.setId(id);
        this.name = name;
        this.family = family;
        this.startDate = startDate;
        this.setEndDate(endDate);
        this.setDepartment(department);
        this.setRole(role);
        this.setSalary(salary);
        this.isFired = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %s, %s, %s, %.2f%n",
                this.id, this.name, this.family, this.startDate, this.endDate,
                this.department.getName(), this.role.toString(), this.salary);
    }

    public boolean isFired() {
        return isFired;
    }

    public void setFired() {
        isFired = true;
    }
}