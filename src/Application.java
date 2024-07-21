import managers.DepartmentManager;
import managers.EmployeeManager;
import models.Department;
import models.Employee;
import models.Role;
import services.DepartmentService;
import services.EmployeeService;
import utils.Reader;
import utils.Validator;
import utils.Writer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

/**
 * Entry point for the Employee Management System application.
 * This class provides a command-line interface for managing employees,
 * including functionalities such as listing, hiring, editing, firing,
 * deleting employees, and searching by various criteria.
 */
public class Application {
    /**
     * Initializes and displays the main menu of the application.
     * The menu provides options for various employee management operations.
     */
    private static void initMenu() {
        System.out.println("MENU");
        System.out.println("-------------------------");
        System.out.println("1. Show All Employees");
        System.out.println("2. Hire Employee");
        System.out.println("3. Edit Employee");
        System.out.println("4. Fire Employee");
        System.out.println("5. Delete Employee");
        System.out.println("6. Search Employees By");
    }

    /**
     * Lists all employees managed by the given EmployeeManager.
     *
     * @param manager the EmployeeManager instance used to retrieve employees
     */
    private static void listAllEmployees(EmployeeManager manager) {
        List<Employee> allEmployees = manager.listAllEntities();

        if (!allEmployees.isEmpty()) {
            System.out.println("All Employees");
            for (Employee employee : allEmployees) {
                System.out.print(employee);
            }
        } else {
            System.out.println("No employees!");
        }
    }

    /**
     * Retrieves the names of all departments managed by the provided DepartmentManager.
     * This method fetches a list of all department entities from the department manager,
     * extracts their names, prints them to the console, and returns a list containing these names.
     *
     * @param departmentManager the DepartmentManager instance used to retrieve department data
     * @return a List of String representing the names of all departments
     */
    private static List<String> getDepartmentsNames(DepartmentManager departmentManager) {
        List<Department> allDepartments = departmentManager.listAllEntities();
        List<String> departmentsNames = new ArrayList<>();

        System.out.println("All Departments Names:");

        for (Department dept : allDepartments) {
            departmentsNames.add(dept.getName());
            System.out.println(dept.getName());
        }

        return departmentsNames;
    }

    /**
     * Handles the hiring process of a new employee.
     * This method prompts the user to enter details for a new employee, including name, family name,
     * department, role, and salary. It validates each input using regular expressions and ensures that
     * the entered department and role are valid. After gathering and validating the information, it attempts
     * to add the new employee to the system using the `EmployeeManager`.
     *
     * @param scanner the `Scanner` instance used for user input
     * @param employeeManager the `EmployeeManager` instance used to add the new employee
     */
    private static void hireEmployee(Scanner scanner, EmployeeManager employeeManager) {
        String name, family;

        // Input and validation for name
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
            if (!Validator.isValid(name, "^+[A-Z][a-z,-]{3,}$")) {
                System.out.print("""
                            The name must start with a capital letter.
                            Then it can contain - and lowercase letters.
                            Its length must be a minimum of 3 characters.
                            """);
            }
        } while (!Validator.isValid(name, "^+[A-Z][a-z,-]{3,}$"));

        // Input and validation for family name
        do {
            System.out.print("Enter family: ");
            family = scanner.nextLine();
            if (!Validator.isValid(family, "^+[A-Z][a-z,-]{3,}$")) {
                System.out.print("""
                            The family name must start with a capital letter.
                            Then it can contain - and lowercase letters.
                            Its length must be a minimum of 3 characters.
                            """);
            }
        } while (!Validator.isValid(family, "^+[A-Z][a-z,-]{3,}$"));

        DepartmentManager departmentManager = new DepartmentManager(new DepartmentService(new Reader(), new Writer()));
        List<String> departmentsNames = getDepartmentsNames(departmentManager);
        String departmentName;
        // Input and validation for department name
        do {
            System.out.print("Enter department name: ");
            departmentName = scanner.nextLine();
        } while (!departmentsNames.contains(departmentName));

        List<Role> roles = Arrays.stream(Role.values()).toList();
        System.out.println("All Roles Names:");
        for (Role role : roles) {
            System.out.println(role.toString());
        }
        String role;
        // Input and validation for role name
        do {
            System.out.print("Enter role: ");
            role = scanner.nextLine();
        } while (!roles.contains(Role.getRole(role)));

        // Input and validation for salary
        String salary;
        do {
            System.out.print("Enter salary: ");
            salary = scanner.nextLine();
            if (!Validator.isValid(salary, "^[0-9]{1,13}(.[0-9]*)$")) {
                System.out.println("The salary must be a positive floating point number!");
            }
        } while (!Validator.isValid(salary, "^[0-9]{1,13}(.[0-9]*)$"));

        // Add the new employee and provide feedback
        if (employeeManager.addEntity(name, family, departmentName, role, salary)) {
            System.out.println("Successfully hired employee!");
            System.out.println();
            listAllEmployees(employeeManager);
        } else {
            System.out.println("Sorry, we cannot hire this employee!");
        }
    }

    /**
     * Handles the editing process of an existing employee.
     * This method prompts the user to enter the ID of the employee they wish to edit, along with optional
     * details such as end date, department, role, and salary. It validates each input using regular expressions
     * and ensures that the entered department and role are valid. After gathering and validating the information,
     * it attempts to update the employee's details using the `EmployeeManager`.
     *
     * @param scanner the `Scanner` instance used for user input
     * @param employeeManager the `EmployeeManager` instance used to edit the employee
     */
    private static void editEmployee(Scanner scanner, EmployeeManager employeeManager) {
        System.out.print("Enter employee id: ");
        int id = Integer.parseInt(scanner.nextLine());

        // Input and validation for end date
        String endDate;
        do {
            System.out.print("Enter end date: ");
            endDate = scanner.nextLine();
            if (!endDate.isBlank() && !Validator.isValid(endDate, "^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
                System.out.println("End date must be in format YYYY-MM-dd or blank!");
            }
        } while (!endDate.isBlank() && !Validator.isValid(endDate, "^[0-9]{4}-[0-9]{2}-[0-9]{2}$"));

        DepartmentManager departmentManager = new DepartmentManager(new DepartmentService(new Reader(), new Writer()));
        List<String> departmentsNames = getDepartmentsNames(departmentManager);
        String departmentName;
        // Input and validation for department name
        do {
            System.out.print("Enter department name: ");
            departmentName = scanner.nextLine();
            if (!departmentName.isBlank() && !departmentsNames.contains(departmentName)) {
                System.out.println("Department name must be from the list or blank!");
            }
        } while (!departmentName.isBlank() && !departmentsNames.contains(departmentName));

        // Retrieve and display role names
        List<Role> roles = Arrays.stream(Role.values()).toList();
        System.out.println("All Roles Names:");
        for (Role role : roles) {
            System.out.println(role.toString());
        }
        // Input and validation for role name
        String role;
        do {
            System.out.print("Enter role: ");
            role = scanner.nextLine();
            if (!role.isBlank() && !roles.contains(Role.getRole(role))) {
                System.out.println("Role must be blank or from the list!");
            }
        } while (!role.isBlank() && !roles.contains(Role.getRole(role)));

        // Input and validation for salary
        String salary;
        do {
            System.out.print("Enter salary: ");
            salary = scanner.nextLine();
            if (!salary.isBlank() && !Validator.isValid(salary, "^[0-9]{1,13}(.[0-9]*)$")) {
                System.out.println("Salary must be blank or a positive floating point number!");
            }
        } while (!salary.isBlank() && !Validator.isValid(salary, "^[0-9]{1,13}(.[0-9]*)$"));

        // Edit the employee and provide feedback
        if (employeeManager.editEntity(id, endDate, departmentName, role, salary)) {
            System.out.println("Successfully edited employee!");
            System.out.println();
            listAllEmployees(employeeManager);
        } else {
            System.out.println("Sorry, we cannot edit this employee!");
        }
    }

    /**
     * Handles the firing process of an employee.
     * This method prompts the user to enter the ID of the employee they wish to fire. It validates that the ID
     * is a positive number. After validating the input, it attempts to fire the employee using the `EmployeeManager`.
     *
     * @param scanner the `Scanner` instance used for user input
     * @param employeeManager the `EmployeeManager` instance used to fire the employee
     */
    private static void fireEmployee(Scanner scanner, EmployeeManager employeeManager) {
        String idValue;
        // Input and validation for employee id
        do {
            System.out.print("Enter employee id: ");
            idValue = scanner.nextLine();
            if (!Validator.isValid(idValue, "^[0-9]{1,}$")) {
                System.out.println("Employee id must be a positive number!");
            }
        } while (!Validator.isValid(idValue, "^[0-9]{1,}$"));
        int id = Integer.parseInt(idValue);

        // Fire the employee and provide feedback
        if (employeeManager.fireEmployee(id)) {
            System.out.println("Successfully fired employee!");
            System.out.println();
            listAllEmployees(employeeManager);
        } else {
            System.out.println("Sorry, we cannot fire this employee!");
        }
    }

    /**
     * Handles the deletion process of an employee.
     * This method prompts the user to enter the ID of the employee they wish to delete. It validates that the ID
     * is a positive number. After validating the input, it attempts to delete the employee using the `EmployeeManager`.
     *
     * @param scanner the `Scanner` instance used for user input
     * @param employeeManager the `EmployeeManager` instance used to delete the employee
     */
    private static void deleteEmployee(Scanner scanner, EmployeeManager employeeManager) {
        // Input and validation for employee id
        String idValue;
        do {
            System.out.print("Enter employee id: ");
            idValue = scanner.nextLine();
            if (!Validator.isValid(idValue, "^[0-9]{1,}$")) {
                System.out.println("Employee id must be a positive number!");
            }
        } while (!Validator.isValid(idValue, "^[0-9]{1,}$"));
        int id = Integer.parseInt(idValue);

        // Delete the employee and provide feedback
        if (employeeManager.removeEntity(id)) {
            System.out.println("Successfully deleted employee!");
            System.out.println();
            listAllEmployees(employeeManager);
        } else {
            System.out.println("Sorry, we cannot delete this employee!");
        }
    }

    /**
     * Searches for employees based on a specified criteria.
     * This method prompts the user to choose a search criteria (ID, Name, or Department Name) and then enter
     * the value to search for. It validates the input based on the selected criteria and performs the search using
     * the `EmployeeManager`. It then displays the found employees or an error message if no employees match the criteria.
     *
     * @param scanner the `Scanner` instance used for user input
     * @param employeeManager the `EmployeeManager` instance used to perform the search
     */
    private static void searchEmployeesBy(Scanner scanner, EmployeeManager employeeManager) {
        System.out.println("Criteria for searching are <ID | Name | Department Name>.");
        String criteria;
        // Input and validation for searching by criteria
        do {
            System.out.print("Enter search criteria: ");
            criteria = scanner.nextLine();
        } while (!criteria.equals("ID") && !criteria.equals("Name") && !criteria.equals("Department Name"));

        String value;
        if (criteria.equals("ID")) {
            // Input and validation for searching by criteria value
            do {
                System.out.print("Enter search criteria value: ");
                value = scanner.nextLine();
                if (!Validator.isValid(value, "^[0-9]{1,}$")) {
                    System.out.println("The criteria value must be a number because the criteria is ID!");
                }
            } while (!Validator.isValid(value, "^[0-9]{1,}$"));
        } else {
            System.out.print("Enter search criteria value: ");
            value = scanner.nextLine();
        }

        // Perform the search and display results
        List<Employee> foundEmployees = employeeManager.searchBy(criteria, value);
        if (!foundEmployees.isEmpty()) {
            System.out.println();
            System.out.println("Found employees:");
            for (Employee employee : foundEmployees) {
                System.out.print(employee);
            }
        } else {
            System.out.println("Sorry, we cannot find employees by this criteria!");
        }
    }

    /**
     * Executes a command based on user input.
     * Handles operations such as showing all employees, hiring, editing, firing, deleting employees, and searching by criteria.
     *
     * @param command the user command to execute
     * @param employeeManager the EmployeeManager instance used to manage employee data
     * @param scanner the Scanner instance used to read user input
     */
    private static void execute(String command, EmployeeManager employeeManager, Scanner scanner) {
        switch (command) {
            case "Show All Employees" -> listAllEmployees(employeeManager);
            case "Hire Employee" -> hireEmployee(scanner, employeeManager);
            case "Edit Employee" -> editEmployee(scanner, employeeManager);
            case "Fire Employee" -> fireEmployee(scanner, employeeManager);
            case "Delete Employee" -> deleteEmployee(scanner, employeeManager);
            case "Search Employees By" -> searchEmployeesBy(scanner, employeeManager);

            case "END" -> {
                System.out.println("Goodbye...");
                System.exit(0);
            }

            default -> {
                System.out.println("Invalid command! View possible commands from the menu!");
                System.out.println();
                System.out.print("Enter command: ");
                command = scanner.nextLine();
                execute(command, employeeManager, scanner);
            }
        }
    }

    /**
     * Entry point of the application.
     * Initializes the application, displays the menu, and processes user commands.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Employee Management System!");
        System.out.println();
        initMenu();
        System.out.println();

        Reader reader = new Reader();
        Writer writer = new Writer();
        EmployeeService employeeService = new EmployeeService(reader, writer);
        EmployeeManager manager = new EmployeeManager(employeeService);
        listAllEmployees(manager);
        System.out.println();

        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            System.out.print("Enter command: ");
            command = scanner.nextLine();
            execute(command, manager, scanner);
            if (command.equals("END")) {
                isRunning = false;
            }
        } while (isRunning);

        System.exit(0);
    }
}