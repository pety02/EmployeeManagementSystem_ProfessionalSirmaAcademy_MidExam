package utils;

import utils.interfaces.Writable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Writer implements Writable {
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
    @Override
    public boolean write(String filename, List<Map<String, String>> objs) {
        String delimiter = ",";
        File file = new File(filename);
        boolean fileExists = file.exists();

        if (objs.isEmpty()) {
            System.out.println("No data to write");
            return false;
        }
        String[] headers = objs.get(0).keySet().toArray(new String[0]);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            if (!fileExists) {
                writeLine(bw, headers, ",");
            }

            for (Map<String, String> row : objs) {
                String[] fields = new String[headers.length];
                for (int i = 0; i < headers.length; i++) {
                    fields[i] = row.get(headers[i]);
                }
                writeLine(bw, fields, delimiter);
            }

            return true;
        } catch (IOException e) {
            e.fillInStackTrace();
            return false;
        }
    }
}
