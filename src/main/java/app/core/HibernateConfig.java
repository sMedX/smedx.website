package app.core;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by anton_arakcheyev on 04/12/2018.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("app.core.DB.Repositories")
@ComponentScan("app.core.DB")
@PropertySource(value = {
        "classpath:resources/app.properties"
})
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("app.jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("app.jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("app.jdbc.username"));
        try {
            /*System.out.println("check!!!");
            System.out.println(InetAddress.getLoopbackAddress());
            System.out.println(InetAddress.getLocalHost().getHostName());*/
            if (InetAddress.getLocalHost().getHostName().contains("MacBook-Air")) {
                dataSource.setPassword(env.getRequiredProperty("app.jdbc.password.dev"));
            } else {
                dataSource.setPassword(env.getRequiredProperty("app.jdbc.password.prod"));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            dataSource.setPassword(env.getRequiredProperty("app.jdbc.password.prod"));
        }
        //if()
        //dataSource.setPassword(env.getRequiredProperty("app.jdbc.password.dev"));
        return dataSource;
    }

    private Properties getHibernateProperties() {
        System.out.println("Getting hibernate properties");
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
        properties.put(AvailableSettings.HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put(AvailableSettings.HBM2DLL_CREATE_SCHEMAS, "true");
        properties.put(AvailableSettings.HBM2DDL_CHARSET_NAME, "utf8");
        //properties.put(AvailableSettings.ENC, "utf8");

        //properties.put(AvailableSettings.HBM);
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
        return properties;
    }

   /* @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        sessionFactoryBean.setPackagesToScan(new String[]{"core.dataModel"});
        sessionFactoryBean.setHibernateProperties(getHibernateProperties());
        return sessionFactoryBean;
    }


    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }*/

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            final DataSource dataSource,
            final JpaVendorAdapter jpaVendorAdapter,
            final Environment env
    ) {
        // System.out.println("LocalContainerEntityManagerFactoryBean");
        // log.info("LocalContainerEntityManagerFactoryBean");
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("app.core.DB.DataModel");

        final Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
        properties.put(AvailableSettings.HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put(AvailableSettings.HBM2DLL_CREATE_SCHEMAS, "true");
        properties.put(AvailableSettings.HBM2DDL_CHARSET_NAME, "utf8");
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
        properties.put(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS, false);
        //properties.put(AvailableSettings.ISOLATION, String.valueOf(Connection.TRANSACTION_READ_COMMITTED));
        emf.setJpaProperties(properties);
        return emf;
    }


    @Primary
    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        return transactionManager;
    }

    @Primary
    @Bean
    public TransactionTemplate transactionTemplate(final PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Primary
    @Bean
    public JpaVendorAdapter mysqlJPAVEndorAdapter() {
        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(false);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(MySQL5Dialect.class.getName());
        return adapter;

    }
}
