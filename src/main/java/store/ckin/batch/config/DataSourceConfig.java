package store.ckin.batch.config;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import store.ckin.batch.keymanager.KeyManager;

import javax.sql.DataSource;

/**
 * DataSourceConfig
 *
 * @author : 이가은
 * @version : 2024. 02. 20
 */
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final ApplicationContext applicationContext;
    private final DbProperties dbProperties;
    private final KeyManager keyManager;


    /**
     * 배치 데이터를 실제 읽고 쓸 dataSource 입니다.
     *
     * @return 개발환경 DataSource
     */
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        return getDataSource(keyManager.keyStore(dbProperties.getUrlDev()));
    }

    /**
     * 배치 실행 시 생성되는 메타 테이블이 기록되는 dataSource 입니다.
     *
     * @return 메타 테이블 DataSource
     */
    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource() {
        return getDataSource(keyManager.keyStore(dbProperties.getUrlBatch()));
    }

    /**
     * url에 따라 dataSource를 연결하는 메소드 입니다.
     *
     * @return basicDataSource
     */
    private DataSource getDataSource(String url) {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName(keyManager.keyStore(dbProperties.getDriver()));
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(keyManager.keyStore(dbProperties.getUserName()));
        basicDataSource.setPassword(keyManager.keyStore(dbProperties.getPassword()));

        basicDataSource.setInitialSize(dbProperties.getInitialSize());
        basicDataSource.setMaxTotal(dbProperties.getMaxTotal());
        basicDataSource.setMaxIdle(dbProperties.getMaxIdle());
        basicDataSource.setMinIdle(dbProperties.getMinIdle());

        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setValidationQuery("SELECT 1");

        basicDataSource.setMaxWaitMillis(dbProperties.getMaxWaitMillis());
        return basicDataSource;
    }

    /**
     * 데이터베이스와 MyBatis를 연결하여 사용하기 위한 메소드 입니다.
     *
     * @param dataSource 개발용 데이터 베이스와 연결
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    /**
     * MyBatis의 세션을 스프링 프레임 워크의 트랜잭션과 통합하여 사용하도록 하는 빈 입니다.
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(dataSource()), ExecutorType.BATCH);
    }

}
