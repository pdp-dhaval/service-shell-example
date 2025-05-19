package com.opl.serviceshellexample.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@ShellComponent
public class CSVCommand {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${csv.output.filepath}")
    String path;

    @ShellMethod(key = "mkcsv" ,value = "command to create csv")
    public String hello(@ShellOption(defaultValue = "output.csv") String fileName) {
        long startTime = System.currentTimeMillis();
        String csvFilePath = path + fileName;
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            String header = "Id,Name,Email,Gender,Ip Address,PAN\n";
            writer.append(header);

            List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM `loan_application`.`application_master`");
            for (Map<String, Object> row : results) {
                writer.append(String.valueOf(row.get("id"))).append(",").append(String.valueOf(row.get("name"))).append(",").append(String.valueOf(row.get("email"))).append(",").append(String.valueOf(row.get("gender"))).append(",").append(String.valueOf(row.get("ip_address"))).append(",").append(String.valueOf(row.get("pan"))).append("\n");
            }
            long endTime = System.currentTimeMillis();
            double executionTimeSeconds = (endTime - startTime) / 1000.0;
            return "CSV file generated successfully at " + csvFilePath + " in "+executionTimeSeconds +" sec";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to generate CSV file: " + e.getMessage();
        }
    }
}
