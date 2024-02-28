package store.ckin.batch.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * DataSourceConfig
 *
 * @author : gaeun
 * @version : 2024. 02. 20
 */
@Configuration
@MapperScan(value = "store.ckin.batch.coupon.mapper")
@RequiredArgsConstructor
public class DataSourceConfig {

    private final ApplicationContext applicationContext;
    private final DbProperties dbProperties;

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();


        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl(dbProperties.getUrl());
        basicDataSource.setUsername(dbProperties.getUserName());
        basicDataSource.setPassword(dbProperties.getPassword());

        basicDataSource.setInitialSize(dbProperties.getInitialSize());
        basicDataSource.setMaxTotal(dbProperties.getMaxTotal());
        basicDataSource.setMaxIdle(dbProperties.getMaxIdle());
        basicDataSource.setMinIdle(dbProperties.getMinIdle());

        basicDataSource.setMaxWaitMillis(dbProperties.getMaxWaitMillis());
        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setTestOnBorrow(dbProperties.isTestOnBorrow());

        return basicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }
}
