package services;

import models.Department;
import models.Employee;
import models.Role;
import services.interfaces.Service;
import utils.CsvDepartmentConverter;
import utils.CsvEmployeeConverter;
import utils.Reader;
import utils.Writer;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class for managing Employee entities.
 * Handles operations such as adding, editing,
 * removing, retrieving, and listing employees.
 */
public class EmployeeService implements Service<Employee> {
    private static final String employeesCSVFilename = "employees.csv";
    private static final String departmentsCSVFilename = "departments.csv";
    private final Reader reader;
    private final Writer writer;

    /**
     * Constructs an EmployeeService with the specified Reader and Writer.
     *
     * @param reader the Reader for reading data
     * @param writer the Writer for writing data
     */
    public EmployeeService(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * Adds an employee to the specified department.
     *
     * @param args the arguments for the employee
     * @param employee the employee to be added
     * @param reader the Reader for reading data
     * @param writer the Writer for writing data
     */
    private static void addEmployeeToDepartment(String[] args, Employee employee, Reader reader, Writer writer) {
        String departmentName = args[1];
        CsvDepartmentConverter departmentConverter = new CsvDepartmentConverter();
        List<Department> departments = departmentConverter.fromListOfMapsToListOfModel(
                reader.read(EmployeeService.departmentsCSVFilename, Department.class));
        for (Department department : departments) {
            if (department.getName().equals(departmentName)) {
                department.getEmployees().add(employee);
                employee.setDepartment(department);
                break;
            }
        }

        File departmentsFile = new File(EmployeeService.departmentsCSVFilename);
        if (departmentsFile.delete()) {
            writer.write(EmployeeService.departmentsCSVFilename, departmentConverter.fromListOfModelToListOfMaps(departments));
        }
    }

    /**
     * Adds a new Employee entity.
     *
     * @param args the arguments for creating the employee
     * @return the created Employee
     * @throws RuntimeException if the employee creation fails
     */
    @Override
    public Employee addEntity(String... args) throws RuntimeException {
        if (args.length != 5) {
            throw new ArrayIndexOutOfBoundsException("Too many arguments for employee creation!");
        }
        String name = args[0];
        String family = args[1];
        String departmentName = args[2];
        Role role = Role.getRole(args[3]);
        double salary = Double.parseDouble(args[4]);

        // Not optimal for unique id and it breaks the principle of Encapsulation, but
        // we have no other way to be sure that the employee's id always will be the
        // next number. I tried with AtomicInteger for generating the next number, but
        // it works only if I create many employees in one application's run. Other problem
        // here is that if we have really many employees it would be slow.
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();
        List<Employee> readEmployees = new ArrayList<>();
        try {
            readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                    reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        CsvDepartmentConverter departmentConverter = new CsvDepartmentConverter();
        List<Map<String, String>> readDepartments = new ArrayList<>();
        try {
            readDepartments = this.reader.read(EmployeeService.departmentsCSVFilename, Department.class);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        List<Department> departments = !readDepartments.isEmpty() ? departmentConverter.
                fromListOfMapsToListOfModel(readDepartments) : new ArrayList<>();
        Employee employee = new Employee(!readEmployees.isEmpty() ? readEmployees.
                get(readEmployees.size() - 1).getId() + 1 : 1, name, family, null, role, salary);
        if (!departments.isEmpty()) {
            for (Department department : departments) {
                if (department.getName().equals(departmentName)) {
                    department.getEmployees().add(employee);
                    employee.setDepartment(department);
                    break;
                }
            }

            File departmentsFile = new File(EmployeeService.departmentsCSVFilename);
            if (departmentsFile.delete()) {
                this.writer.write(EmployeeService.departmentsCSVFilename, departmentConverter.
                        fromListOfModelToListOfMaps(departments));
            }

            List<Employee> employees = new ArrayList<>();
            employees.add(employee);
            this.writer.write(EmployeeService.employeesCSVFilename, employeeConverter.
                    fromListOfModelToListOfMaps(employees));

            return employee;
        } else {
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(employee);
            departments.add(new Department(1, departmentName, employees, null));
            this.writer.write(EmployeeService.employeesCSVFilename, employeeConverter.
                    fromListOfModelToListOfMaps(employees));
            return employee;
        }
    }

    /**
     * Edits an existing Employee entity.
     *
     * @param id the ID of the employee to edit
     * @param args the arguments for editing the employee
     * @return the edited Employee
     * @throws RuntimeException if the employee update fails
     */
    @Override
    public Employee editEntity(int id, String... args) throws RuntimeException {
        if (args.length == 0 || 4 < args.length) {
            throw new ArrayIndexOutOfBoundsException("Invalid arguments count for employee update!");
        }
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        if (!readEmployees.isEmpty()) {
            for (Employee employee : readEmployees) {
                if (employee.isFired()) {
                    throw new RuntimeException("Employee is fired!");
                }
                if (employee.getId() == id) {
                    if (!args[0].isBlank()) {
                        String[] endDateArgs = args[0].split("-");
                        LocalDate endDate = LocalDate.of(Integer.parseInt(endDateArgs[0]),
                                Integer.parseInt(endDateArgs[1]), Integer.parseInt(endDateArgs[2]));
                        employee.setEndDate(endDate);
                        employee.setFired();
                    }
                    if (!args[1].isBlank()) {
                        addEmployeeToDepartment(args, employee, this.reader, this.writer);
                    }
                    if (!args[2].isBlank()) {
                        Role role = Role.getRole(args[2]);
                        employee.setRole(role);
                    }
                    if (!args[3].isBlank()) {
                        double salary = Double.parseDouble(args[3]);
                        employee.setSalary(salary);
                    }
                    File file = new File(EmployeeService.employeesCSVFilename);
                    if (file.delete()) {
                        this.writer.write(EmployeeService.employeesCSVFilename, employeeConverter.
                                fromListOfModelToListOfMaps(readEmployees));
                    }
                    return employee;
                }
            }
        }
        throw new RuntimeException("Employee update unsuccessful!");
    }

    /**
     * Removes an Employee entity by ID.
     *
     * @param id the ID of the employee to remove
     * @return true if the removal was successful, false otherwise
     */
    @Override
    public boolean removeEntity(int id) {
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        if (!readEmployees.isEmpty()) {
            List<Employee> toBeStored = new ArrayList<>();
            for (Employee employee : readEmployees) {
                if (employee.getId() == id) {
                    continue;
                }
                toBeStored.add(employee);
            }
            // It shows that the employee with this id is already deleted so do nothing.
            if (readEmployees.size() == toBeStored.size()) {
                return true;
            }

            File employeesFile = new File(EmployeeService.employeesCSVFilename);
            if (employeesFile.delete()) {
                this.writer.write(EmployeeService.employeesCSVFilename, employeeConverter.
                        fromListOfModelToListOfMaps(toBeStored));
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves an Employee entity by ID.
     *
     * @param id the ID of the employee to retrieve
     * @return the retrieved Employee
     * @throws RuntimeException if the employee is not found
     */
    @Override
    public Employee getEntity(int id) throws RuntimeException {
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        if (!readEmployees.isEmpty()) {
            for (Employee employee : readEmployees) {
                if (employee.getId() == id) {
                    return employee;
                }
            }
        }

        throw new RuntimeException("Employee not found!");
    }

    /**
     * Lists all Employee entities.
     *
     * @return a list of all employees
     */
    @Override
    public List<Employee> listAllEntities() {
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        try {
            return employeeConverter.fromListOfMapsToListOfModel(
                    this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    /**
    * Searches for employees by a specified criteria.
    *
    * @param criteria the search criteria
    * @return a list of employees matching the criteria
    */
    public List<Employee> searchBy(String criteria, String value) {
        List<Employee> wantedEmployees = new ArrayList<>();
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();
        List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
            this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));

        switch (criteria) {
            case "ID" -> {
                int id = Integer.parseInt(value);
                for (Employee employee : readEmployees) {
                    if (employee.getId() == id) {
                        wantedEmployees.add(employee);
                        break;
                    }
                }

                return wantedEmployees;
            }
            case "Name" -> {
                for (Employee employee : readEmployees) {
                    if (employee.getName().equals(value)) {
                        wantedEmployees.add(employee);
                    }
                }

                return wantedEmployees;
            }
            case "Department Name" -> {
                CsvDepartmentConverter departmentConverter = new CsvDepartmentConverter();
                List<Department> readDepartments = departmentConverter.fromListOfMapsToListOfModel(
                    reader.read(EmployeeService.departmentsCSVFilename, Department.class));
                for (Employee employee : readEmployees) {
                    for (Department department : readDepartments) {
                        if (employee.getDepartment().getName().equals(department.getName())
                            && department.getName().equals(value)) {
                            wantedEmployees.add(employee);
                        }
                    }
                }

                return wantedEmployees;
            }
            default -> {
                return readEmployees;
            }
        }
    }
}