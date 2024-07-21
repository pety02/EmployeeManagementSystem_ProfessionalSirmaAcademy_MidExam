package utils;

import models.Company;
import models.Department;
import utils.interfaces.Convertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts between `Company` objects and their CSV representations.
 * Implements the `Convertable` interface for `Company` type.
 */
public class CsvCompanyConverter implements Convertable<Company> {

    /**
     * Converts a list of maps (CSV data) to a list of `Company` objects.
     * Each map represents a row in the CSV, with column headers as keys.
     *
     * @param data the list of maps where each map represents a row in the CSV
     * @return a list of `Company` objects constructed from the provided data
     */
    @Override
    public List<Company> fromListOfMapsToListOfModel(List<Map<String, String>> data) {
        List<Company> companies = new ArrayList<>();

        // Process each map (row) in the data
        for (var companiesMap : data) {
            String[] fields = companiesMap.values().toArray(new String[0]);
            int id = Integer.parseInt(fields[1]);
            String name = fields[2];
            String[] departmentName = fields[0].split("_");

            // Create a list of departments from department names
            List<Department> departments = new ArrayList<>();
            for (String deptName : departmentName) {
                departments.add(new Department(0, deptName, new ArrayList<>(), null));
            }

            // Create a Company object with the extracted data
            companies.add(new Company(id, name, departments));
        }

        return companies;
    }

    /**
     * Converts a list of `Company` objects to a list of maps (CSV representation).
     * Each map represents a row in the CSV with column headers as keys.
     *
     * @param objs the list of `Company` objects to be converted
     * @return a list of maps where each map represents a row in the CSV
     */
    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Company> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        // Define CSV headers
        String[] headers = new String[]{"ID", "Name", "Department"};

        // Process each Company object
        for (Company company : objs) {
            Map<String, String> obj = new HashMap<>();
            List<Department> departments = company.getDepartments();
            StringBuilder sb = new StringBuilder();
            int cnt = 0;

            // Concatenate department names with "_" as separator
            for (Department department : departments) {
                cnt++;
                if (cnt == departments.size()) {
                    sb.append(department.getName());
                    break;
                }
                // More appropriate to use department IDs but names are used here for clarity
                sb.append(department.getName()).append("_");
            }

            // Populate the map with company data
            String[] fields = new String[]{company.getId() + "", company.getName(), sb.toString()};
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}