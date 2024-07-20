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

/**
 *
 */
public class EmployeeManager implements Manager<Employee> {
    private final EmployeeService service;

    /**
     *
     * @param service
     */
    public EmployeeManager(EmployeeService service) {
        this.service = service;
    }

    /**
     *
     * @param args
     * @return
     */
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

    /**
     *
     * @param id
     * @param args
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean removeEntity(int id) {
        return this.service.removeEntity(id);
    }

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public List<Employee> listAllEntities() {
        return this.service.listAllEntities();
    }

    /**
     *
     * @param criteria
     * @param value
     * @return
     */
    @Override
    public List<Employee> searchBy(String criteria, String value) {
        return this.service.searchBy(criteria, value);
    }

    /**
     *
     * @param id
     * @return
     */
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