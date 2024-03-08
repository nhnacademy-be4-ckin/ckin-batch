package store.ckin.batch.coupon.dto;

import lombok.*;

import java.util.Date;

/**
 * 쿠폰을 생성하는 DTO입니다.
 *
 * @author : 이가은
 * @version : 2024. 02. 28
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BirthCouponDto {
    private Long memberId;
    private Long couponTemplateId;
    private Date expirationDate;
    private Date issueDate;
    private Date usedDate;
}
