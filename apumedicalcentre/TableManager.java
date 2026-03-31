/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package apumedicalcentre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;

/**
 *
 * @author User
 */
public class TableManager {
    // Unfiltered Table View
    public static void showTable(File file, JTable table) {
        showTable(file, table, null, null, null, null);
    }

    // Filtered Table View with One condition
    public static void showTable(File file, JTable table, String filterColumn, String filterValue) {
        showTable(file, table, filterColumn, filterValue, null, null);
    }

    // Filtered Table View with Two Conditions
    public static void showTable(File file, JTable table, 
                                 String filterColumn, String filterValue, 
                                 String secColumn, String secValue) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine == null)
                return;

            String[] headers = headerLine.split(",");
            DefaultTableModel model = new DefaultTableModel(headers, 0);

            String line;
            int filterIndex1 = (filterColumn != null) ? Arrays.asList(headers).indexOf(filterColumn) : -1;
            int filterIndex2 = (secColumn != null) ? Arrays.asList(headers).indexOf(secColumn) : -1;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;

                String[] row = line.split(",");

                if (row.length != headers.length) {
                    System.out.println("Skipping malformed row: " + line);
                    continue;
                }

                boolean condition1 = (filterIndex1 == -1 || row[filterIndex1].trim().equalsIgnoreCase(filterValue.trim()));
                boolean condition2 = (filterIndex2 == -1 || row[filterIndex2].trim().equalsIgnoreCase(secValue.trim()));

                if (condition1 && condition2) {
                    model.addRow(row);
                }
            }

            table.setModel(model);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}