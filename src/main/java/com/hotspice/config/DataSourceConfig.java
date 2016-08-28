package com.hotspice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.hotspice.*")
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySources({
        @PropertySource("classpath:db.properties")
})
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public JpaTransactionManager jpaTransMan(){
        JpaTransactionManager jtManager = new JpaTransactionManager(
                getEntityManagerFactoryBean().getObject());
        return jtManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(getDataSource());
        lcemfb.setPersistenceUnitName("hotSpiceDefault");

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        lcemfb.setPersistenceProvider(jpaVendorAdapter.getPersistenceProvider());
        lcemfb.setJpaVendorAdapter(jpaVendorAdapter);
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        lcemfb.setJpaDialect(new HibernateJpaDialect());
        LoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
        lcemfb.setLoadTimeWeaver(loadTimeWeaver);
        return lcemfb;
    }


    @Bean
    public DataSource getDataSource() {
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        props.setProperty("dataSource.user", env.getRequiredProperty("dataSource.user"));
        props.setProperty("dataSource.password", env.getRequiredProperty("dataSource.password"));
        props.setProperty("dataSource.databaseName", env.getRequiredProperty("dataSource.databaseName"));
        props.setProperty("dataSource.portNumber", env.getRequiredProperty("dataSource.portNumber"));
        props.setProperty("dataSource.serverName", env.getRequiredProperty("dataSource.address"));

        HikariConfig config = new HikariConfig(props);
        config.setAutoCommit(false);
        return new HikariDataSource(config);
    }
}
