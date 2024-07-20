package managers;

import managers.interfaces.Manager;
import models.Employee;
import services.EmployeeService;
import utils.CsvEmployeeConverter;
import utils.Reader;
import utils.Writer;

import java.io.File;
import java.time.LocalDate;
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

    public boolean fireEmployee(int id) {
        try {
            CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();
            Reader reader = new Reader();
            Writer writer = new Writer();

            final String filename = "employees.csv";
            List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                    reader.read(filename, Employee.class));
            for(Employee employee : readEmployees) {
                if(employee.getId() == id) {
                    employee.setFired();
                    employee.setEndDate(LocalDate.now());
                    File file = new File(filename);
                    if(file.delete()) {
                        writer.write(filename, employeeConverter.fromListOfModelToListOfMaps(readEmployees));
                        return true;
                    }
                    break;
                }
            }

            return false;
        } catch (RuntimeException ex) {
            ex.fillInStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }
}