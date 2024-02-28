package store.ckin.batch.coupon.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.ckin.batch.coupon.dto.BirthCouponDto;
import store.ckin.batch.coupon.dto.BirthMemberDto;

import java.util.List;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 28
 */
public interface BirthMapper {
    BirthMemberDto getBirthMember();
    void insertBirthCoupon(BirthCouponDto birthCouponDto);
}
