package utils;

import models.Department;
import utils.interfaces.Convertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDepartmentConverter implements Convertable<Department> {
    @Override
    public List<Department> fromListOfMapsToListOfModel(List<Map<String, String>> data) {
        List<Department> employees = new ArrayList<>();
        String[] headers = data.get(0).keySet().toArray(new String[0]);
        for(var deptMap : data) {
            String[] fields = deptMap.values().toArray(new String[0]);
            int id = Integer.parseInt(fields[0]);
            String name = fields[1];

            employees.add(new Department(id, name, new ArrayList<>()));
        }

        return employees;
    }

    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Department> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = new String[] {"ID", "Name"};
        for(Department dept : objs) {
            Map<String, String> obj = new HashMap<>();
            String[] fields = new String[] {dept.getId() + "", dept.getName()};
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}