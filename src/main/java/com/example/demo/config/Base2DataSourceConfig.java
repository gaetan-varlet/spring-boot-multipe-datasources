package com.example.demo.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.demo.base2.repositories",
        entityManagerFactoryRef = "base2EntityManagerFactory",
        transactionManagerRef = "base2TransactionManager"
)
public class Base2DataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix="datasource.base2")
    public DataSourceProperties base2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource base2DataSource() {
        DataSourceProperties base2DataSourceProperties = base2DataSourceProperties();
        return DataSourceBuilder.create()
                .driverClassName(base2DataSourceProperties.getDriverClassName())
                .url(base2DataSourceProperties.getUrl())
                .username(base2DataSourceProperties.getUsername())
                .password(base2DataSourceProperties.getPassword())
                .build();
    }

    @Bean
    public PlatformTransactionManager base2TransactionManager() {
        EntityManagerFactory factory = base2EntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean base2EntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(base2DataSource());
        factory.setPackagesToScan("com.example.demo.base2.entities");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        jpaProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        factory.setJpaProperties(jpaProperties);
        return factory;
    }

}
