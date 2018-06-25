package com.social.conf;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ContextLoader;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Properties;

import static java.lang.ProcessBuilder.Redirect.to;


@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"com.social.persistence"})
@PropertySource("classpath:application.properties")
public class PersistenceConfig{
    @Autowired
    private Environment env;

    static int method() {
        return true ? null : 0;
    }




    @Bean()
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.social.persistence.model", "com.social.security.model");

        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(hibernateProperties());
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public Properties hibernateProperties() {
        return new Properties(){
            {
                setProperty("hibernate.hbm2ddl.auto",
                        env.getProperty("spring.jpa.hibernate.ddl-auto"));
                setProperty("hibernate.dialect",
                        env.getProperty("spring.jpa.properties.hibernate.dialect"));
            }
        };
    }

    @Qualifier("MyDataSources")
    @Bean
    public DataSource dataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        //dataSource.setMaxActive(Integer.parseInt(env.getProperty("spring.database.max-active")));
        return dataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
