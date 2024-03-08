package store.ckin.batch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import store.ckin.batch.coupon.config.CouponBatchConfig;
import store.ckin.batch.coupon.dto.BirthCouponDto;
import store.ckin.batch.coupon.mapper.BirthMapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@SpringBootTest
class BatchApplicationTests {

	@Autowired
	BirthMapper birthMapper;
	private final int TEST_SIZE = 100;
	private List<BirthCouponDto> bulkCoupon = new ArrayList<>();

	@Test
	void contextLoads() {
	}

	@BeforeEach
	@Disabled
	void setUp() {
		for(int i = 0; i < TEST_SIZE; i++) {
			bulkCoupon.add(new BirthCouponDto(1L, 1L, Date.valueOf("2024-02-03"), Date.valueOf(LocalDate.now()), null));
		}
	}

	@Test
	@Disabled
	@DisplayName("쿠폰 insert Test: 단일 insert, 벌크 insert")
	void insertTest() {
		StopWatch stopWatch = new StopWatch();

		// bulk insert 성능 측정
		stopWatch.start();
		birthMapper.bulkInsertUserList(bulkCoupon);
		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());

		// 단일 insert 성능 측정
		stopWatch = new StopWatch();
		stopWatch.start();
		for(BirthCouponDto couponDto : bulkCoupon) {
			birthMapper.insertUserList(couponDto);
		}
		stopWatch.stop();
		System.out.println(stopWatch.prettyPrint());
	}

}
