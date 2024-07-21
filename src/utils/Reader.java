package utils;

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

/**
 * Reads CSV files and parses the data into a list of maps.
 * Each map represents a row in the CSV file, with column headers as keys.
 * Implements the `Readable` interface for generic file reading.
 */
public class Reader implements Readable {

    private static <T> String[] getHeaders(Class<T> cl, String headersLine, String delimiter) {
        String[] headers = headersLine.split(delimiter);

        // Validate header based on the type of class
        if (cl == Employee.class) {
            if (!headers[0].equals("Role") || !headers[1].equals("Salary") || !headers[2].equals("Department")
                    || !headers[3].equals("Start Date") || !headers[4].equals("isFired") || !headers[5].equals("End Date")
                    || !headers[6].equals("Family") || !headers[7].equals("ID") || !headers[8].equals("Name")) {
                throw new RuntimeException("CSV file header does not match Employee object format");
            }
        } else if (cl == Department.class) {
            if (!headers[1].equals("ID") || !headers[2].equals("Name") || !headers[0].equals("Company")) {
                throw new RuntimeException("CSV file header does not match Department object format");
            }
        } else {
            if (!headers[0].equals("ID") || !headers[1].equals("Name") || !headers[2].equals("Department")) {
                throw new RuntimeException("CSV file header does not match Company object format");
            }
        }
        return headers;
    }

    /**
     * Reads data from a CSV file and converts it into a list of maps.
     * The maps are populated with column headers as keys and corresponding
     * CSV fields as values. Validates the header row to ensure it matches the
     * expected format based on the type of object.
     *
     * @param filename the name of the CSV file to read
     * @param cl the class type that indicates the expected CSV format
     *           (e.g., `Employee.class`, `Department.class`, `Company.class`)
     * @param <T> the type of the class (e.g., `Employee`, `Department`, `Company`)
     * @return a list of maps where each map represents a row in the CSV file
     */
    @Override
    public <T> List<Map<String, String>> read(String filename, Class<T> cl) {
        String line;
        String delimiter = ",";
        List<Map<String, String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Read and validate the header line
            String headersLine = br.readLine();
            if (headersLine == null) {
                throw new IOException("CSV file is empty!");
            }

            String[] headers = getHeaders(cl, headersLine, delimiter);

            // Read and process each line of the CSV file
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(delimiter);
                Map<String, String> obj = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    obj.put(headers[i], fields[i]);
                }

                data.add(obj);
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

        return data;
    }
}