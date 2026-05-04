package com.openclassrooms.mddapi.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@RestController
@RequestMapping("/api/test")
@Profile("e2e")
public class E2ETestController {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public E2ETestController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @DeleteMapping("/reset-database")
    @Transactional
    public void resetDatabase() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");

        jdbcTemplate.execute("TRUNCATE TABLE comments;");
        jdbcTemplate.execute("TRUNCATE TABLE articles;");
        jdbcTemplate.execute("TRUNCATE TABLE abonnements;");
        jdbcTemplate.execute("TRUNCATE TABLE themes;"); 
        jdbcTemplate.execute("TRUNCATE TABLE users;");

        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("./sql/insert_data.sql"));
        populator.execute(dataSource);
    }
}