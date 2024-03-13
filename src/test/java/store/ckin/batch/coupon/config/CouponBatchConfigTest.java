package store.ckin.batch.coupon.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 03. 12
 */
@SpringBatchTest
@SpringBootTest
@EnableBatchProcessing
class CouponBatchConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job giveBirthCouponJob;

    @Test
    @DisplayName("giveBirthCoupon Job 실행 테스트")
    void testGiveBirthCouponJob() throws Exception {
        assertNotNull(giveBirthCouponJob);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }
}