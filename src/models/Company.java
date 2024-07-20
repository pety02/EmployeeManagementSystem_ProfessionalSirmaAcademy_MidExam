package models;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;

/**
 *
 */
public class Company {
    private static final AtomicInteger counter = new AtomicInteger();
    private final int id;
    private String name;
    private List<Department> departments;

    /**
     *
     * @param name
     * @param departments
     */
    public Company(String name, List<Department> departments) {
        this.id = counter.incrementAndGet();
        this.setName(name);
        this.setDepartments(departments);
    }

    /**
     *
     * @param id
     * @param name
     * @param departments
     */
    public Company(int id, String name, List<Department> departments) {
        this.id = id;
        this.setName(name);
        this.setDepartments(departments);
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
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     *
     * @param departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%d, %s%n", this.id, this.name);
    }
}