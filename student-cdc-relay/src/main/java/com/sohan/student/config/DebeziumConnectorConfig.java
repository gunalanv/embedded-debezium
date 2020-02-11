package com.sohan.student.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class provides the configurations required to setup a Debezium connector for the Student Table.
 *
 * @author Sohan
 */
@Configuration
public class DebeziumConnectorConfig {

    /**
     * Student Database details.
     */
    @Value("${student.datasource.host}")
    private String studentDBHost;

    @Value("${student.datasource.databasename}")
    private String studentDBName;

    @Value("${student.datasource.port}")
    private String studentDBPort;

    @Value("${student.datasource.username}")
    private String studentDBUserName;

    @Value("${student.datasource.password}")
    private String studentDBPassword;

    private String STUDENT_TABLE_NAME = "student.students";

    /**
     * Student database connector.
     *
     * @return Configuration.
     */
    @Bean
    public io.debezium.config.Configuration studentConnector() {
        return io.debezium.config.Configuration.create()
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/path/to/storage/dbhistory.dat")
                .with("offset.storage.file.filename", "/path/to/storage/offset.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("name", "my-sql-connector")
                .with("database.server.name", studentDBHost+"-"+studentDBName)
                .with("database.hostname", studentDBHost)
                .with("database.port", studentDBPort)
                .with("database.user", studentDBUserName)
                .with("database.password", studentDBPassword)
                .with("database.dbname", studentDBName)
                .with("table.whitelist", STUDENT_TABLE_NAME).build();
    }
}
