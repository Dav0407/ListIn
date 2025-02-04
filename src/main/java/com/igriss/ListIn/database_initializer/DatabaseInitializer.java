package com.igriss.ListIn.database_initializer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
    private final ElasticsearchClient elasticsearchClient;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${elasticsearch.index-name}")
    private String indexName;

    private final List<String> scripts = List.of(
            "/database_sql_scripts/categories.sql",
            "/database_sql_scripts/attribute_keys.sql",
            "/database_sql_scripts/attribute_values.sql",
            "/database_sql_scripts/category_attributes.sql",
            "/database_sql_scripts/models/electronics/smartphone_brand_models.sql",
            "/database_sql_scripts/models/electronics/laptop_brand_models.sql",
            "/database_sql_scripts/models/electronics/smartwatch_brand_models.sql",
            "/database_sql_scripts/models/electronics/tablet_brand_models.sql",
            "/database_sql_scripts/models/electronics/console_brand_models.sql",
            "/database_sql_scripts/models/electronics/laptop_processor_models.sql",
            "/database_sql_scripts/models/electronics/pc_processor_models.sql",
            "/database_sql_scripts/models/electronics/laptop_gpu_models.sql",
            "/database_sql_scripts/models/electronics/pc_gpu_models.sql",
            "/database_sql_scripts/models/electronics/pc_brand_models.sql"
    );

    @PostConstruct
    public void flushRedis() {
        Objects.requireNonNull(redisTemplate
                        .getConnectionFactory()
                )
                .getConnection()
                .serverCommands()
                .flushAll();
        log.info("#Redis cache successfully cleared");
    }

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
            log.info("#Database cleared successfully.");
        } catch (Exception e) {
            log.error("#Error while clearing the database: {}", e.getMessage());
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
            log.error("#Error executing script {}: {}", scriptPath, e.getMessage());
        }
    }


    @PostConstruct
    public void clearElasticsearchData() {
        try {
            if (elasticsearchClient.indices().exists(e -> e.index(indexName)).value()) {
                elasticsearchClient.indices().delete(d -> d.index(indexName));
                log.info("#Index deleted: {}", indexName);
            }
        } catch (Exception e) {
            log.error("#Exception while clearing elastic search data: {}", e.getMessage());
        }
    }
}






