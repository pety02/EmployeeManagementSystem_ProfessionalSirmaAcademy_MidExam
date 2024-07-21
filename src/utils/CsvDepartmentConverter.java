package utils;

import models.Company;
import models.Department;
import utils.interfaces.Convertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts between `Department` objects and their CSV representations.
 * Implements the `Convertable` interface for `Department` type.
 */
public class CsvDepartmentConverter implements Convertable<Department> {
    private static final String companiesFilename = "companies.csv";

    /**
     * Converts a list of maps (CSV data) to a list of `Department` objects.
     * Each map represents a row in the CSV, with column headers as keys.
     *
     * @param data the list of maps where each map represents a row in the CSV
     * @return a list of `Department` objects constructed from the provided data
     */
    @Override
    public List<Department> fromListOfMapsToListOfModel(List<Map<String, String>> data) {
        List<Department> departments = new ArrayList<>();

        // Process each map (row) in the data
        for (var deptMap : data) {
            String[] fields = deptMap.values().toArray(new String[0]);
            int id = Integer.parseInt(fields[1]);
            String name = fields[2];
            String companyName = fields[0];

            // Create a Company object (in real scenario, it would likely be fetched from a database)
            Company company = new Company(0, companyName, new ArrayList<>());
            Department department = new Department(id, name, new ArrayList<>(), company);
            company.getDepartments().add(department);
            departments.add(department);
        }

        return departments;
    }

    /**
     * Converts a list of `Department` objects to a list of maps (CSV representation).
     * Each map represents a row in the CSV with column headers as keys.
     *
     * @param objs the list of `Department` objects to be converted
     * @return a list of maps where each map represents a row in the CSV
     */
    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Department> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = new String[] {"ID", "Name", "Company"};

        // Read the companies file to get existing company data
        Reader reader = new Reader();
        CsvCompanyConverter companyConverter = new CsvCompanyConverter();
        List<Company> companies = new ArrayList<>();
        try {
            companies = companyConverter.fromListOfMapsToListOfModel(
                    reader.read(CsvDepartmentConverter.companiesFilename, Company.class));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

        // Process each Department object
        for (Department dept : objs) {
            String[] fields = new String[]{
                    dept.getId() + "",
                    dept.getName(),
                    dept.getCompany() != null ? dept.getCompany().getName() : "null"
            };
            Map<String, String> obj = new HashMap<>();

            // Populate the map with department data
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}