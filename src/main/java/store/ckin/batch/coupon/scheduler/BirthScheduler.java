package store.ckin.batch.coupon.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import store.ckin.batch.coupon.config.CouponBatchConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 26
 */
@Component
public class BirthScheduler {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private CouponBatchConfig couponBatchConfig;

    @Scheduled(cron = "0 0 0 1 * *")
    public void birthJob() {
        Map<String, JobParameter> confMap = new HashMap<>();

        Date date = Calendar.getInstance().getTime();
        confMap.put("time", new JobParameter(date));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(couponBatchConfig.giveBirthCoupon(), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
