package store.ckin.batch.coupon.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import store.ckin.batch.coupon.step.BirthCouponStep;

/**
 * CouponBatchConfig
 *
 * @author : 이가은
 * @version : 2024. 02. 25
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CouponBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final BirthCouponStep birthCouponStep;

    @Bean
    public Job giveBirthCoupon() throws Exception {
        return jobBuilderFactory.get("giveBirthCoupon")
                .start(birthCouponStep.giveBirthCouponStep())
                .build();
    }
}
