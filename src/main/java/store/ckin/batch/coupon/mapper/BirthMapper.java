package store.ckin.batch.coupon.mapper;

import store.ckin.batch.coupon.dto.BirthCouponDto;
import store.ckin.batch.coupon.dto.BirthMemberDto;

/**
 * description:
 *
 * @author : 이가은
 * @version : 2024. 02. 28
 */
public interface BirthMapper {
    BirthMemberDto getBirthMember();

    void insertBirthCoupon(BirthCouponDto birthCouponDto);
}
