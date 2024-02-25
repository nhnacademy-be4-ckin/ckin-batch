package store.ckin.batch.coupon.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import store.ckin.batch.coupon.dto.BirthMemberDto;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

/**
 * CouponBatchConfig
 *
 * @author : gaeun
 * @version : 2024. 02. 25
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CouponBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private static final int chunkSize = 10;

    @Bean
    public Job giveBirthCoupon() throws Exception {
        return jobBuilderFactory.get("giveBirthCoupon")
                .start(giveBirthCouponStep())
                .build();
    }

    @Bean
    public Step giveBirthCouponStep() throws Exception {
        return stepBuilderFactory.get("giveBirthCouponStep")
                .<BirthMemberDto, BirthMemberDto>chunk(chunkSize)
                .reader(jdbcPagingItemReader())
                .writer(itemWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public JdbcPagingItemReader<BirthMemberDto> jdbcPagingItemReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<BirthMemberDto>()
                .pageSize(chunkSize)
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(BirthMemberDto.class))
                .queryProvider(createQueryProvider())
                .name("jdbcPagingItemReader")
                .build();
    }

    private JdbcBatchItemWriter<BirthMemberDto> itemWriter() {
        return new JdbcBatchItemWriterBuilder<BirthMemberDto>()
                .dataSource(dataSource)
                .sql("insert into Coupon(member_id, coupontemplate_id, coupon_expiration_date, coupon_issue_date, coupon_used_date) "
                        + "values (?, 1, ?, ?, ?)")
                .itemPreparedStatementSetter(new BirthMemberStatementParam())
                .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();

        int birthMonth = LocalDate.now().getMonthValue();
        log.info("birthMonth : " + birthMonth);

        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("select member_id, member_birth");
        queryProvider.setFromClause("from Member");
        queryProvider.setWhereClause("where month(member_birth) = " + birthMonth);
        //TODO: where절에 회원 상태 쿼리 추가
        queryProvider.setSortKey("member_id");

        return queryProvider.getObject();
    }

    static class BirthMemberStatementParam implements ItemPreparedStatementSetter<BirthMemberDto> {
        @Override
        public void setValues(BirthMemberDto item, PreparedStatement ps) throws SQLException {
//            LocalDate date = LocalDate.parse("" + item.getBirthMonth());
            Date date = item.getMemberBirth();
            log.info("item.getMemberId" + item.getMemberId());
            log.info("item.getMemberBirth" + item.getMemberBirth());
            ps.setLong(1, item.getMemberId());
            ps.setString(2, String.valueOf(date));
            ps.setString(3, String.valueOf(LocalDate.now()));
            ps.setString(4, null);
        }
    }

}
