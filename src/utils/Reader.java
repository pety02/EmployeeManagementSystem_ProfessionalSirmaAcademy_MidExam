package utils;

import models.Company;
import models.Department;
import models.Employee;
import utils.interfaces.Readable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader implements Readable {

    @Override
    public <T> List<Map<String, String>> read(String filename, Class<T> cl) {
        String line;
        String delimiter = ",";
        List<Map<String, String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String headersLine = br.readLine();
            if(headersLine == null) {
                throw new IOException("CSV file is empty!");
            }

            String[] headers = headersLine.split(delimiter);
            if(cl == Employee.class) {
                if (!headers[6].equals("ID") || !headers[7].equals("Name") || !headers[5].equals("Family")
                        || !headers[3].equals("Start Date") || !headers[4].equals("End Date") || !headers[2].equals("Department")
                        || !headers[0].equals("Role") || !headers[1].equals("Salary")) {
                    throw new RuntimeException("Not Employee object");
                }
            } else if (!headers[0].equals("ID") || !headers[1].equals("Name")) {
                if(cl == Department.class) {
                    throw new RuntimeException("Not Department object");
                } else {
                    throw new RuntimeException("Not Company object");
                }
            }

            while((line = br.readLine()) != null) {
                String[] fields = line.split(delimiter);
                Map<String, String> obj = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    obj.put(headers[i], fields[i]);
                }

                data.add(obj);
            }
        } catch (IOException | RuntimeException ex) {
            ex.fillInStackTrace();
        }

        return data;
    }
}