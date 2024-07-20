package utils;

import models.Company;
import models.Department;
import utils.interfaces.Convertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDepartmentConverter implements Convertable<Department> {
    private static final String companiesFilename = "companies.csv";
    @Override
    public List<Department> fromListOfMapsToListOfModel(List<Map<String, String>> data) {
        List<Department> departments = new ArrayList<>();
        String[] headers = data.get(0).keySet().toArray(new String[0]);
        for(var deptMap : data) {
            String[] fields = deptMap.values().toArray(new String[0]);
            int id = Integer.parseInt(fields[1]);
            String name = fields[2];
            String companyName = fields[0];

            Company company = new Company(0, companyName, new ArrayList<>());
            Department department = new Department(id, name, new ArrayList<>(), company);
            company.getDepartments().add(department);
            departments.add(department);
        }

        return departments;
    }

    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Department> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = new String[] {"ID", "Name", "Company"};
        Reader reader = new Reader();
        CsvCompanyConverter companyConverter = new CsvCompanyConverter();
        List<Company> companies = new ArrayList<>();
        try {
            companies = companyConverter.fromListOfMapsToListOfModel(
                    reader.read(CsvDepartmentConverter.companiesFilename, Company.class));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        for(Department dept : objs) {
            String[] fields = new String[]{dept.getId() + "", dept.getName(), dept.getCompany() != null ? dept.getCompany().getName() : "null"};
            Map<String, String> obj = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}