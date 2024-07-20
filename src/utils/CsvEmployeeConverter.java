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

public class CsvEmployeeConverter implements Convertable<Employee> {
    @Override
    public List<Employee> fromListOfMapsToListOfModel(List<Map<String, String>> data) {
        List<Employee> employees = new ArrayList<>();
        String[] headers = data.get(0).keySet().toArray(new String[0]);
        for(var empMap : data) {
            String[] fields = empMap.values().toArray(new String[0]);
            int id = Integer.parseInt(fields[7]);
            String name = fields[8];
            String family = fields[6];
            String[] stDateArgs = fields[3].split("-");
            LocalDate startDate = LocalDate.of(Integer.parseInt(stDateArgs[0]), Integer.parseInt(stDateArgs[1]), Integer.parseInt(stDateArgs[2]));
            LocalDate endDate;
            if(fields[5].equals("null")) {
                endDate = null;
            } else {
                String[] endDateArgs = fields[5].split("-");
                endDate = LocalDate.of(Integer.parseInt(endDateArgs[0]), Integer.parseInt(endDateArgs[1]), Integer.parseInt(endDateArgs[2]));
            }
            Department department = new Department(0, fields[2], new ArrayList<>(), null);
            Role role = Role.getRole(fields[0]);
            double salary = Double.parseDouble(fields[1]);

            Employee currEmployee = new Employee(id, name, family, startDate, endDate, department, role, salary);
            employees.add(currEmployee);
            department.getEmployees().add(currEmployee);
        }

        return employees;
    }

    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Employee> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = new String[] {"Role", "Salary" , "Department", "Start Date", "End Date", "Family", "ID", "Name", "isFired"};
        for(Employee emp : objs) {
            Map<String, String> obj = new HashMap<>();
            String[] fields = new String[] {emp.getRole().toString(), emp.getSalary() + "", emp.getDepartment() != null ? emp.getDepartment().getName() : "",
                    emp.getStartDate().toString(), emp.getEndDate() != null ? emp.getEndDate().toString() : "null", emp.getFamily(), emp.getId() + "", emp.getName(), emp.isFired() + ""};
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}