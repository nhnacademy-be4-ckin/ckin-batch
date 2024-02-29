package store.ckin.batch.coupon.step;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.ckin.batch.coupon.dto.BirthCouponDto;
import store.ckin.batch.coupon.dto.BirthMemberDto;
import store.ckin.batch.listener.BirthCouponListener;

import java.time.LocalDate;

/**
 * BirthCouponStep
 *
 * @author : 이가은
 * @version : 2024. 02. 29
 */
@Configuration
@RequiredArgsConstructor
public class BirthCouponStep {
    private final StepBuilderFactory stepBuilderFactory;
    private final SqlSessionFactory sqlSessionFactory;
    private final BirthCouponListener birthCouponListener;
    private static final int chunkSize = 10;

    @Bean
//    @JobScope
    public Step giveBirthCouponStep() throws Exception {
        return stepBuilderFactory.get("giveBirthCouponStep")
                .<BirthMemberDto, BirthCouponDto>chunk(chunkSize)
                .reader(myBatisPagingItemReader())
                .processor(processor())
                .writer(itemWriter())
                .faultTolerant()
                .skip(ParseException.class)
                .skipLimit(1)
                .listener(birthCouponListener)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<BirthMemberDto> myBatisPagingItemReader() throws Exception {
        return new MyBatisPagingItemReaderBuilder<BirthMemberDto>()
                .pageSize(chunkSize)
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("store.ckin.batch.coupon.mapper.BirthMapper.getBirthMember")
                .build();

    }


    @Bean
    public ItemProcessor<BirthMemberDto, BirthCouponDto> processor() {

        return birthMemberDto -> {
            BirthCouponDto birthCouponDto = new BirthCouponDto(birthMemberDto.getMember_id(), 1L, birthMemberDto.getMember_birth(), java.sql.Date.valueOf(LocalDate.now().toString()), null);

            return birthCouponDto;
        };
    }


    private MyBatisBatchItemWriter<BirthCouponDto> itemWriter() throws Exception {

        return new MyBatisBatchItemWriterBuilder<BirthCouponDto>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("store.ckin.batch.coupon.mapper.BirthMapper.insertBirthCoupon")
                .assertUpdates(false)
                .build();
    }
}
