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
 * This class is responsible for managing Employee entities.
 * It implements Manger of Employee interface that provides
 * methods to add, edit, remove, retrieve, and list employees.
 * Additionally, EmployeeManager class  offers functionality to
 * search employees based on specific criteria and to mark an
 * employee as fired.
 */
public class EmployeeManager implements Manager<Employee> {
    private final EmployeeService service;

    /**
     * Constructs an EmployeeManager with the specified EmployeeService.
     *
     * @param service the EmployeeService to use for managing Employee entities
     */
    public EmployeeManager(EmployeeService service) {
        this.service = service;
    }

    /**
     * Adds a new Employee entity using the provided arguments.
     *
     * @param args the arguments required to create a new Employee entity
     * @return true if the entity was successfully added, false otherwise
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
     * Edits an existing Employee entity with the specified ID using the provided arguments.
     *
     * @param id the ID of the Employee entity to edit
     * @param args the arguments to update the Employee entity with
     * @return true if the entity was successfully edited, false otherwise
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
     * Removes the Employee entity with the specified ID.
     *
     * @param id the ID of the Employee entity to remove
     * @return true if the entity was successfully removed, false otherwise
     */
    @Override
    public boolean removeEntity(int id) {
        return this.service.removeEntity(id);
    }

    /**
     * Retrieves the Employee entity with the specified ID.
     *
     * @param id the ID of the Employee entity to retrieve
     * @return the Employee entity if found, null otherwise
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
     * Lists all Employee entities.
     *
     * @return a list of all Employee entities
     */
    @Override
    public List<Employee> listAllEntities() {
        return this.service.listAllEntities();
    }

    /**
     * Searches for Employee entities based on the specified criteria and value.
     *
     * @param criteria the criteria to search by (e.g., ID,Name, Department Name)
     * @param value the value to search for
     * @return a list of Employee entities that match the search criteria
     */
    public List<Employee> searchBy(String criteria, String value) {
        return this.service.searchBy(criteria, value);
    }

    /**
     * Marks the Employee with the specified ID as fired and sets their end date to the current date.
     *
     * @param id the ID of the Employee to fire
     * @return true if the Employee was successfully fired, false otherwise
     */
    public boolean fireEmployee(int id) {
        try {
            CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();
            Reader reader = new Reader();
            Writer writer = new Writer();

            final String filename = "employees.csv";
            List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                    reader.read(filename, Employee.class));
            for (Employee employee : readEmployees) {
                if (employee.getId() == id) {
                    employee.setFired();
                    employee.setEndDate(LocalDate.now());
                    File file = new File(filename);
                    if (file.delete()) {
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