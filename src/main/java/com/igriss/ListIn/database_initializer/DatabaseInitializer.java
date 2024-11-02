package com.igriss.ListIn.database_initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    private final List<String> scripts = List.of(
            "/database_sql_scripts/categories.sql",
            "/database_sql_scripts/product_condition.sql",
            "/database_sql_scripts/publication_statuses.sql",
            "/database_sql_scripts/publication_types.sql"
    );

    @PostConstruct
    public void init() {
        for (String script : scripts) {
            executeScript(script);
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
            e.printStackTrace();
        }
    }
}
