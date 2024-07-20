import managers.EmployeeManager;
import models.Role;
import services.EmployeeService;
import utils.Reader;
import utils.Writer;

import java.util.Scanner;

public class Application {
    private static void initMenu() {
        System.out.println("MENU");
        System.out.println("-------------------------");
        System.out.println("1. Add Employee");
        System.out.println("2. Edit Employee");
        System.out.println("3. Remove Employee");
        System.out.println("4. Search Employees By");
    }
    private static void execute(String command, EmployeeManager employeeManager, Scanner scanner) {
        switch (command) {
            case "Add Employee" -> {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter family: ");
                String family = scanner.nextLine();
                System.out.print("Enter department id: ");
                String departmentId = scanner.nextLine();
                System.out.print("Enter role: ");
                String role = scanner.nextLine();
                System.out.print("Enter salary: ");
                String salary = scanner.nextLine();

                if(employeeManager.addEntity(name, family, departmentId, role, salary)) {
                    System.out.println("Successfully added employee!");
                } else {
                    System.out.println("Sorry, we cannot add this employee!");
                }
            }
            case "Edit Employee" -> {
                return;
            }
            case "Remove Employee" -> {
                return;
            }
            case "Search Employees By" -> {
                return;
            }
            default -> {
                System.exit(0);
            }
        }
    }
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Employee Management System!");
        System.out.println();
        initMenu();
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        String command;

        Reader reader = new Reader();
        Writer writer = new Writer();
        EmployeeService employeeService = new EmployeeService(reader, writer);
        EmployeeManager manager = new EmployeeManager(employeeService);
        do {
            if(!isRunning) {
                break;
            }
            System.out.print("Enter command: ");
            command = scanner.nextLine();
            execute(command, manager, scanner);
        } while (isRunning);
        System.exit(0);
    }
}