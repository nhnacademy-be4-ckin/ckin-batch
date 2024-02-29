package store.ckin.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.item.ParseException;

/**
 * 생일 쿠폰 실행 시 로그를 찍는 리스너 입니다.
 *
 * @author : 이가은
 * @version : 2024. 02. 29
 */
@Slf4j
public class BirthCouponListener {

    @BeforeStep
    public void beforeStep(StepExecution execution) {
        log.info("start : {}", execution.getStepName());
    }

    @AfterStep
    public void afterStep(StepExecution execution) {
        log.info("end : {}", execution.getStepName());
    }

    @OnReadError
    public void onReadError(Exception e) {
        if (e instanceof ParseException) {
            log.error("ParseException 발생!!");
        } else {
            log.error("An error has occured");
        }
    }
}
