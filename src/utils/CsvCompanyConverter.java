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
        for(var deptMap : data) {
            String[] fields = deptMap.values().toArray(new String[0]);
            int id = Integer.parseInt(fields[0]);
            String name = fields[1];

            companies.add(new Company(id, name, new ArrayList<>()));
        }

        return companies;
    }

    @Override
    public List<Map<String, String>> fromListOfModelToListOfMaps(List<Company> objs) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = new String[] {"ID", "Name"};
        for(Company company : objs) {
            Map<String, String> obj = new HashMap<>();
            String[] fields = new String[] {company.getId() + "", company.getName()};
            for (int i = 0; i < headers.length; i++) {
                obj.put(headers[i], fields[i]);
            }
            data.add(obj);
        }

        return data;
    }
}