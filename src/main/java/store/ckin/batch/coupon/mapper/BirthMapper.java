package store.ckin.batch.coupon.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.ckin.batch.coupon.dto.BirthCouponDto;
import store.ckin.batch.coupon.dto.BirthMemberDto;

import java.util.List;

/**
 * MyBatis를 이용한 데이터베이스 접근을 위한 인터페이스 입니다.
 *
 * @author : 이가은
 * @version : 2024. 02. 28
 */
@Mapper
public interface BirthMapper {
    BirthMemberDto getBirthMember();

    int bulkInsertUserList(List<? extends BirthCouponDto> birthCouponDtoList);

}
