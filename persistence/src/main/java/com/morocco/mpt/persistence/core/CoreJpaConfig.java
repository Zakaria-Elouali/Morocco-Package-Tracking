package com.morocco.mpt.persistence.core;

import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages =
        {
                "com.morocco.mpt.persistence.core",
                "com.morocco.mpt.persistence.users",
                "com.morocco.mpt.persistence.employee"
        },
        entityManagerFactoryRef = "coreEntityManagerFactory",
        transactionManagerRef = "coreTransactionManager")

public class CoreJpaConfig {

  @Bean(name = "coreDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.core")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();

  }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        // Provide non-null JPA properties
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return new EntityManagerFactoryBuilder(jpaVendorAdapter, jpaProperties, null);
    }

    @Bean(name = "coreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory (
            EntityManagerFactoryBuilder builder, @Qualifier("coreDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages(
                        "com.morocco.mpt.domain.core",
                        "com.morocco.mpt.domain.users",
                        "com.morocco.mpt.domain.employee"
                )
                .persistenceUnit("jpa")
                .build();
    }

  @Primary
  @Bean(name = "coreTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("coreEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }


  @Bean(name = "flywayCore")
  @DependsOn("coreDataSource")
  public Flyway flywayCore(@Qualifier("coreDataSource") DataSource dataSource) {
    Flyway flyway =

                Flyway.configure()
                        .dataSource(dataSource)
                        .locations("classpath:db/migration/core",
                                "classpath:db/migration/main"
                        )
                        .outOfOrder(true).load();
        flyway.repair();
        flyway.migrate();
        return flyway;
    }
}
