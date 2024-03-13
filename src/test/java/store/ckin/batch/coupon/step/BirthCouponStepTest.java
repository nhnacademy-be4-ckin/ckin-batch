package store.ckin.batch.coupon.step;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * BirthCouponStepTest
 *
 * @author : gaeun
 * @version : 2024. 03. 13
 */
@SpringBatchTest
@SpringBootTest
class BirthCouponStepTest {
    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("스텝 실행 테스트")
    void testBirthCouponStep() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("giveBirthCouponStep", jobParameters);
        BatchStatus batchStatus = jobExecution.getStatus();

        Assert.assertEquals(BatchStatus.COMPLETED, batchStatus);
    }

}