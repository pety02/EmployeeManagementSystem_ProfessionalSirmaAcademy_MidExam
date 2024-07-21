package utils;

import models.Department;
import models.Employee;
import models.Role;
import utils.interfaces.Convertable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts between `Employee` objects and their CSV representations.
 * Implements the `Convertable` interface for `Employee` type.
 */
public class CsvEmployeeConverter implements Convertable<Employee> {

    /**
     * Converts a list of maps (CSV data) to a list of `Employee` objects.
     * Each map represents a row in the CSV, with column headers as keys.
     *
     * @param data the list of maps where each map represents a row in the CSV
     * @return a list of `Employee` objects constructed from the provided data
     */
    @Override
    public List<Employee> fromListOfMapsToListOfModel(List<Map<String, String>> data) {
        List<Employee> employees = new ArrayList<>();

        // Process each map (row) in the data
        for (var empMap : data) {
            String[] fields = empMap.values().toArray(new String[0]);

            // Parse employee data from CSV fields
            int id = Integer.parseInt(fields[7]);
            String name = fields[8];
            String family = fields[6];

            // Parse start date and end date
            LocalDate startDate = LocalDate.of(Integer.parseInt(fields[3].split("-")[0]),
                    Integer.parseInt(fields[3].split("-")[1]),
                    Integer.parseInt(fields[3].split("-")[2]));
            LocalDate endDate = fields[5].equals("null") ? null : LocalDate.of(Integer.
                            parseInt(fields[5].split("-")[0]),
                    Integer.parseInt(fields[5].split("-")[1]),
                    Integer.parseInt(fields[5].split("-")[2]));

            // Create a Department object
            Department department = new Department(0, fields[2], new ArrayList<>(), null);

            // Convert role and salary
            Role role = Role.getRole(fields[0]);
            double salary = Double.parseDouble(fields[1]);

            // Create and add Employee object to the list
            Employee currEmployee = new Employee(id, name, family, startDate, endDate, department, role, salary);
            employees.add(currEmployee);
            department.getEmployees().add(currEmployee);
        }

        return employees;
    }

    /**
     * Converts a list of `Employee` objects to a list of maps (CSV representation).
     * Each map represents a row in the CSV with column headers as keys.
     *
     * @param objs the list of `Employee` objects to be converted
     * @return a list of maps where each map represents a row in the CSV
     */
    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Employee> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        // Define CSV headers
        String[] headers = new String[] {"Role", "Salary" , "Department", "Start Date", "End Date",
                "Family", "ID", "Name", "isFired"};

        // Process each Employee object
        for (Employee emp : objs) {
            Map<String, String> obj = new HashMap<>();

            // Prepare fields for the CSV row
            String[] fields = new String[] {
                    emp.getRole().toString(),
                    emp.getSalary() + "",
                    emp.getDepartment() != null ? emp.getDepartment().getName() : "",
                    emp.getStartDate().toString(),
                    emp.getEndDate() != null ? emp.getEndDate().toString() : "null",
                    emp.getFamily(),
                    emp.getId() + "",
                    emp.getName(),
                    emp.isFired() + ""
            };

            // Populate the map with employee data
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}