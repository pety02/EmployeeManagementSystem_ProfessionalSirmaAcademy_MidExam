package utils;

import utils.interfaces.Writable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Provides functionality to write data to a CSV file.
 * Implements the {@link Writable} interface.
 */
public class Writer implements Writable {

    /**
     * Writes a single line of data to the CSV file.
     *
     * @param bw the {@link BufferedWriter} used for writing data
     * @param fields the array of strings to be written as a single line
     * @param csvSeparator the separator used between fields in the CSV file
     * @throws IOException if an I/O error occurs
     */
    private static void writeLine(BufferedWriter bw, String[] fields, String csvSeparator) throws IOException {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                line.append(csvSeparator);
            }
            line.append(fields[i]);
        }
        bw.write(line.toString());
        bw.newLine();
    }

    /**
     * Writes a list of data objects to a CSV file.
     * If the file does not exist, it creates a new file and writes the headers.
     * If the file already exists, it appends the data without rewriting the headers.
     *
     * @param filename the name of the CSV file
     * @param objs a list of maps where each map represents a row in the CSV file
     * @return {@code true} if the data was written successfully; {@code false} otherwise
     */
    @Override
    public boolean write(String filename, List<Map<String, String>> objs) {
        String delimiter = ",";
        File file = new File(filename);
        boolean fileExists = file.exists();

        // Check if there is data to write
        if (objs.isEmpty()) {
            System.out.println("No data to write");
            return false;
        }

        // Get the headers from the first map's keys
        String[] headers = objs.get(0).keySet().toArray(new String[0]);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            // Write headers if the file does not exist
            if (!fileExists) {
                writeLine(bw, headers, delimiter);
            }

            // Write each row of data
            for (Map<String, String> obj : objs) {
                String[] fields = obj.values().toArray(new String[0]);
                writeLine(bw, fields, delimiter);
            }

            return true;
        } catch (IOException e) {
            e.fillInStackTrace();
            return false;
        }
    }
}