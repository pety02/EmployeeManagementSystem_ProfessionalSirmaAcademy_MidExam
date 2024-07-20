import managers.EmployeeManager;
import models.Company;
import models.Department;
import models.Employee;
import services.EmployeeService;
import utils.CsvCompanyConverter;
import utils.Reader;
import utils.Writer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Application {
    private static boolean isNumber(String value) {
        for (int i = 0; i < value.length(); i++) {
            if('0' <= value.charAt(i) && value.charAt(i) <= '9') {
                return true;
            }
        }

        return false;
    }

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
    private static void listAllEmployees(EmployeeManager manager) {
        List<Employee> allEmployees = manager.listAllEntities();

        System.out.println("All Employees");
        for(Employee employee : allEmployees) {
            System.out.print(employee);
        }
    }
    private static void execute(String command, EmployeeManager employeeManager, Scanner scanner) {
        switch (command) {
            case "Show All Employees" -> {
                listAllEmployees(employeeManager);
            }
            case "Hire Employee" -> {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter family: ");
                String family = scanner.nextLine();
                System.out.print("Enter department name: ");
                String departmentName = scanner.nextLine();
                System.out.print("Enter role: ");
                String role = scanner.nextLine();
                System.out.print("Enter salary: ");
                String salary = scanner.nextLine();

                if(employeeManager.addEntity(name, family, departmentName, role, salary)) {
                    System.out.println("Successfully hired employee!");
                    System.out.println();
                    listAllEmployees(employeeManager);
                } else {
                    System.out.println("Sorry, we cannot hire this employee!");
                }
            }
            case "Edit Employee" -> {
                System.out.print("Enter employee id: ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter end date: ");
                String endDate = scanner.nextLine();
                System.out.print("Enter department name: ");
                String departmentName = scanner.nextLine();
                System.out.print("Enter role: ");
                String role = scanner.nextLine();
                System.out.print("Enter salary: ");
                String salary = scanner.nextLine();

                if(employeeManager.editEntity(id, endDate, departmentName, role, salary)) {
                    System.out.println("Successfully edited employee!");
                    System.out.println();
                    listAllEmployees(employeeManager);
                } else {
                    System.out.println("Sorry, we cannot edited this employee!");
                }
            }
            case "Fire Employee" -> {
                System.out.print("Enter employee id: ");
                int id = Integer.parseInt(scanner.nextLine());
                if(employeeManager.fireEmployee(id)) {
                    System.out.println("Successfully fired employee!");
                    System.out.println();
                    listAllEmployees(employeeManager);
                } else {
                    System.out.println("Sorry we cannot fire this employee!");
                }
            }
            case "Delete Employee" -> {
                System.out.print("Enter employee id: ");
                int id = Integer.parseInt(scanner.nextLine());
                if(employeeManager.removeEntity(id)) {
                    System.out.println("Successfully deleted employee!");
                    System.out.println();
                    listAllEmployees(employeeManager);
                } else {
                    System.out.println("Sorry we cannot delete this employee!");
                }
            }
            case "Search Employees By" -> {
                System.out.println("Criterias for searching by are <ID | Name | Department Name>.");
                System.out.print("Enter search by criteria: ");
                String criteria;
                do {
                    criteria = scanner.nextLine();
                } while (!criteria.equals("ID") && !criteria.equals("Name") && !criteria.equals("Department Name"));
                String value;
                if(criteria.equals("ID")) {
                    do {
                        System.out.print("Enter search by criteria value: ");
                        value = scanner.nextLine();
                        if(!isNumber(value)) {
                            System.out.println("The criteria value must be number because the criteria is ID!");
                        }
                    } while (!isNumber(value));
                } else {
                    System.out.print("Enter search by criteria value: ");
                    value = scanner.nextLine();
                }
                List<Employee> foundEmployees = employeeManager.searchBy(criteria, value);
                if(!foundEmployees.isEmpty()) {
                    System.out.println();
                    System.out.println("Found employees:");
                    for(Employee employee : foundEmployees) {
                        System.out.print(employee);
                    }
                } else {
                    System.out.println("Sorry we cannot found employees by this criteria!");
                }
            }
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
            if(!isRunning) {
                break;
            }
            System.out.print("Enter command: ");
            command = scanner.nextLine();
            execute(command, manager, scanner);
            if(command.equals("END")) {
                isRunning = false;
            }
        } while (isRunning);

        System.exit(0);
    }
}