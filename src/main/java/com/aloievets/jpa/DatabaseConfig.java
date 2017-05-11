package com.aloievets.jpa;

import com.aloievets.jpa.user.repository.MyBatisUserRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by Andrew on 02.05.2017.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    // Spring JdbcTemplate configuration (spring-jdbc)
    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // JPA configuration
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceUnitName("JpaSample");

        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // MyBatis configuration
    @Bean
    MyBatisUserRepository myBatisUserRepository(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        MapperFactoryBean<MyBatisUserRepository> mapperFactoryBean = new MapperFactoryBean<>();
        mapperFactoryBean.setMapperInterface(MyBatisUserRepository.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);

        return mapperFactoryBean.getObject();
    }

    @Bean("sqlSessionFactory")
    SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext context) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath*:mybatis/mapper-*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    // MyBatis without xml configuration
    @Bean
    static MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.aloievets.jpa.user.repository.mybatis");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryAnnotated");

        return configurer;
    }

    @Bean("sqlSessionFactoryAnnotated")
    SqlSessionFactory sqlSessionFactoryAnnotated(DataSource dataSource, ApplicationContext context) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        return sqlSessionFactoryBean.getObject();
    }
}
