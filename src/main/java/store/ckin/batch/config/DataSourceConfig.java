package store.ckin.batch.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
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
@RequiredArgsConstructor
public class DataSourceConfig {

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
}
