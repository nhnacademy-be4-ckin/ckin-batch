package store.ckin.batch.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * description:
 *
 * @author : gaeun
 * @version : 2024. 02. 28
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BirthCouponDto {
    private Long memberId;
    private Long couponTemplateId;
    private Date expirationDate;
    private Date issueDate;
    private Date usedDate;
}
