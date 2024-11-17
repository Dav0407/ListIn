package com.igriss.ListIn.database_initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

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
            "/database_sql_scripts/product_condition.sql",
            "/database_sql_scripts/publication_statuses.sql",
            "/database_sql_scripts/publication_types.sql",
            "/database_sql_scripts/attribute_keys.sql",
            "/database_sql_scripts/attribute_values.sql",
            "/database_sql_scripts/category_attributes.sql",
            "/database_sql_scripts/brand_models.sql"
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
            log.error(e.getMessage());
        }
    }
}
