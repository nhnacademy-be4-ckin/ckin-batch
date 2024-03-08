package store.ckin.batch.coupon.step;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import store.ckin.batch.coupon.dto.BirthCouponDto;
import store.ckin.batch.coupon.dto.BirthMemberDto;
import store.ckin.batch.coupon.mapper.BirthMapper;
import store.ckin.batch.listener.BatchListener;

import java.time.LocalDate;

/**
 * BirthCouponStep
 *
 * @author : 이가은
 * @version : 2024. 02. 29
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BirthCouponStep {
    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;
    private final PlatformTransactionManager transactionManager;
    private final BatchListener birthCouponListener;
    private final BirthMapper birthMapper;
    private static final int chunkSize = 3;

    /**
     * ItemReader, ItemWriter 실행하고 예외상황에 대한 로그를 남기는 Step 입니다.
     *
     * @return Step
     * @throws Exception
     */
    @Bean
    public Step giveBirthCouponStep() throws Exception {
        return stepBuilderFactory.get("giveBirthCouponStep")
                .<BirthMemberDto, BirthCouponDto>chunk(chunkSize)
                .reader(myBatisPagingItemReader())
                .processor(processor())
                .writer(customItemWriter())
                .faultTolerant()
                .skip(ParseException.class)
                .skipLimit(10)
                .listener(birthCouponListener)
                .transactionManager(transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    /**
     * 생일자 회원의 아이디, 생일 날짜를 읽어오는 ItemReader 입니다.
     *
     * @return
     * @throws Exception
     */
    @Bean
    public MyBatisPagingItemReader<BirthMemberDto> myBatisPagingItemReader() throws Exception {
        return new MyBatisPagingItemReaderBuilder<BirthMemberDto>()
                .pageSize(chunkSize)
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("store.ckin.batch.coupon.mapper.BirthMapper.getBirthMember")
                .build();

    }

    /**
     * 회원정보를 가지고 쿠폰을 생성하는 ItemProcessor 입니다.
     *
     * @return ItemProcessor
     */
    @Bean
    public ItemProcessor<BirthMemberDto, BirthCouponDto> processor() {

        return birthMemberDto -> {
            BirthCouponDto birthCouponDto = new BirthCouponDto(birthMemberDto.getMemberId(), 1L, birthMemberDto.getMemberBirth(), java.sql.Date.valueOf(LocalDate.now().toString()), null);

            return birthCouponDto;
        };
    }

    /**
     * 쿠폰을 bulk insert하는 ItemWriter 입니다.
     *
     * @return ItemWriter
     */
    @Bean
    public ItemWriter<BirthCouponDto> customItemWriter() {
        return items -> {
            birthMapper.bulkInsertUserList(items);
        };
    }

}

