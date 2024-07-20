package utils;

import models.Company;
import models.Department;
import utils.interfaces.Convertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvCompanyConverter implements Convertable<Company> {

    @Override
    public List<Company> fromListOfMapsToListOfModel(List<Map<String, String>> data) {
        List<Company> companies = new ArrayList<>();
        String[] headers = data.get(0).keySet().toArray(new String[0]);
        for(var companiesMap : data) {
            String[] fields = companiesMap.values().toArray(new String[0]);
            int id = Integer.parseInt(fields[1]);
            String name = fields[2];
            String[] departmentName = fields[0].split("_");
            List<Department> departments = new ArrayList<>();
            for(String deptName : departmentName) {
                departments.add(new Department(0, deptName, new ArrayList<>(), null));
            }

            companies.add(new Company(id, name, departments));
        }

        return companies;
    }

    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Company> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = new String[] {"ID", "Name", "Department"};
        for(Company company : objs) {
            Map<String, String> obj = new HashMap<>();
            List<Department> departments = company.getDepartments();
            StringBuilder sb = new StringBuilder();
            int cnt = 0;
            for(Department department : departments) {
                cnt++;
                if(cnt == departments.size()) {
                    sb.append(department.getName());
                    break;
                }
                // Here more appropriate would be to store the ids of the departments,
                // separated by _ not their name but for task clarity I use the names
                // of the departments.
                sb.append(department.getName()).append("_");
            }
            String[] fields = new String[] {company.getId() + "", company.getName(), sb.toString()};
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}