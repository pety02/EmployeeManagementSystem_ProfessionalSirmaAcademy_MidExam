package managers;

import managers.interfaces.Manager;
import models.Employee;
import services.EmployeeService;

import java.util.List;

public class EmployeeManager implements Manager<Employee> {
    private final EmployeeService service;
    public EmployeeManager(EmployeeService service) {
        this.service = service;
    }

    @Override
    public boolean addEntity(String... args) {
        try {
            return this.service.addEntity(args) != null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.fillInStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean editEntity(int id, String... args) {
        try {
            return this.service.editEntity(id, args) != null;
        } catch (RuntimeException ex) {
            ex.fillInStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeEntity(int id) {
        return this.service.removeEntity(id);
    }

    @Override
    public Employee getEntity(int id) {
        try {
            return this.service.getEntity(id);
        } catch (RuntimeException ex) {
            ex.fillInStackTrace();
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Employee> listAllEntities() {
        return this.service.listAllEntities();
    }
}