package com.igriss.ListIn.database_initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    private final List<String> scripts = List.of(
            "/database_sql_scripts/categories.sql",
            "/database_sql_scripts/attribute_keys.sql",
            "/database_sql_scripts/attribute_values.sql",
            "/database_sql_scripts/category_attributes.sql",
            "/database_sql_scripts/models/smartphone_brand_models.sql",
            "/database_sql_scripts/models/laptop_brand_models.sql",
            "/database_sql_scripts/models/smartwatch_brand_models.sql",
            "/database_sql_scripts/models/tablet_brand_models.sql",
            "/database_sql_scripts/models/console_brand_models.sql",
            "/database_sql_scripts/models/laptop_processor_models.sql",
            "/database_sql_scripts/models/pc_processor_models.sql",
            "/database_sql_scripts/models/laptop_gpu_models.sql",
            "/database_sql_scripts/models/pc_gpu_models.sql"
    );

    @PostConstruct
    public void init() {
        clearDatabase();
        for (String script : scripts) {
            executeScript(script);
        }
    }

    private void clearDatabase() {
        try {
            List<String> tablesToClear = List.of(
                    "category_attributes",
                    "attribute_values",
                    "attribute_keys",
                    "publication_types",
                    "publication_statuses",
                    "categories",
                    "smartphone_brand_models",
                    "laptop_brand_models",
                    "smartwatch_brand_models",
                    "tablet_brand_models",
                    "console_brand_models"
            );

            for (String table : tablesToClear) {
                jdbcTemplate.update("DELETE FROM " + table);
            }
            log.info("Database cleared successfully.");
        } catch (Exception e) {
            log.error("Error while clearing the database: {}", e.getMessage());
        }
    }

    private void executeScript(String scriptPath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(scriptPath)), StandardCharsets.UTF_8))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            jdbcTemplate.execute(sql.toString());
        } catch (Exception e) {
            log.error("Error executing script {}: {}", scriptPath, e.getMessage());
        }
    }
}
