package com.liberty.poker;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public Flyway flyway(@Qualifier("dataSource") final DataSource dataSource){
        final var configuration = new ClassicConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setLocations(new Location("classpath:flyway"));

        final var flyway = new Flyway(configuration);
        flyway.migrate();

        return flyway;
    }
}
