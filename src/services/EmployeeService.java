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

public class EmployeeService implements Service<Employee> {
    private static final String employeesCSVFilename = "employees.csv";
    private static final String departmentsCSVFilename = "departments.csv";
    private final Reader reader;
    private final Writer writer;

    public EmployeeService(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    private static void addEmployeeToDepartment(String[] args, Employee employee, Reader reader, Writer writer) {
        String departmentName = args[1];
        CsvDepartmentConverter departmentConverter = new CsvDepartmentConverter();
        List<Department> departments = departmentConverter.fromListOfMapsToListOfModel(
                reader.read(EmployeeService.departmentsCSVFilename, Department.class));
        for(Department department : departments) {
            if(department.getName().equals(departmentName)) {
                department.getEmployees().add(employee);
                employee.setDepartment(department);
                break;
            }
        }

        File departmentsFile = new File(EmployeeService.departmentsCSVFilename);
        if(departmentsFile.delete()) {
            writer.write(EmployeeService.departmentsCSVFilename, departmentConverter.fromListOfModelToListOfMaps(departments));
        }
    }

    @Override
    public Employee addEntity(String... args) throws RuntimeException {
        if(args.length != 5) {
            throw new ArrayIndexOutOfBoundsException("Too many arguments for employee creation!");
        }
        String name = args[0];
        String family = args[1];
        String departmentName = args[2];
        Role role = Role.getRole(args[3]);
        double salary = Double.parseDouble(args[4]);
        Employee employee = new Employee(name, family, null, role, salary);

        CsvDepartmentConverter departmentConverter = new CsvDepartmentConverter();
        List<Department> departments = departmentConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.departmentsCSVFilename, Department.class));
        if(!departments.isEmpty()) {
            for (Department department : departments) {
                if (department.getName().equals(departmentName)) {
                    department.getEmployees().add(employee);
                    employee.setDepartment(department);
                    break;
                }
            }

            File departmentsFile = new File(EmployeeService.departmentsCSVFilename);
            if (departmentsFile.delete()) {
                this.writer.write(EmployeeService.departmentsCSVFilename, departmentConverter.fromListOfModelToListOfMaps(departments));
            }

            CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();
            List<Employee> employees = new ArrayList<>();
            employees.add(employee);
            this.writer.write(EmployeeService.employeesCSVFilename, employeeConverter.fromListOfModelToListOfMaps(employees));

            return employee;
        }
        throw new RuntimeException("Employee insertion unsuccessful!");
    }

    @Override
    public Employee editEntity(int id, String... args) throws RuntimeException{
        if(args.length == 0 || 4 < args.length) {
            throw new ArrayIndexOutOfBoundsException("Invalid arguments count for employee update!");
        }
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        if(!readEmployees.isEmpty()) {
            for (Employee employee : readEmployees) {
                if (employee.isFired()) {
                    throw new RuntimeException("Employee is fired!");
                }
                if (employee.getId() == id) {
                    String[] endDateArgs = args[0].split("-");
                    LocalDate endDate = LocalDate.of(Integer.parseInt(endDateArgs[0]),
                            Integer.parseInt(endDateArgs[1]), Integer.parseInt(endDateArgs[2]));
                    employee.setEndDate(endDate);
                    if (args[1] != null) {
                        addEmployeeToDepartment(args, employee, this.reader, this.writer);
                        if (args[2] != null) {
                            Role role = Role.getRole(args[2]);
                            employee.setRole(role);
                            if (args[3] != null) {
                                double salary = Double.parseDouble(args[3]);
                                employee.setSalary(salary);
                            }
                            this.writer.write(EmployeeService.employeesCSVFilename, employeeConverter.fromListOfModelToListOfMaps(readEmployees));
                        }
                    }
                    return employee;
                }
            }
        }
        throw new RuntimeException("Employee update unsuccessful!");
    }

    @Override
    public boolean removeEntity(int id) {
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        if(!readEmployees.isEmpty()) {
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
                this.writer.write(EmployeeService.employeesCSVFilename, employeeConverter.fromListOfModelToListOfMaps(toBeStored));
                return true;
            }
        }
        return false;
    }

    @Override
    public Employee getEntity(int id) throws RuntimeException {
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        List<Employee> readEmployees = employeeConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
        if(!readEmployees.isEmpty()) {
            for (Employee employee : readEmployees) {
                if (employee.getId() == id) {
                    return employee;
                }
            }
        }

        throw new RuntimeException("Employee not found!");
    }

    @Override
    public List<Employee> listAllEntities() {
        CsvEmployeeConverter employeeConverter = new CsvEmployeeConverter();

        return employeeConverter.fromListOfMapsToListOfModel(
                this.reader.read(EmployeeService.employeesCSVFilename, Employee.class));
    }
}
