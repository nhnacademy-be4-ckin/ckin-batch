package store.ckin.batch.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

/**
 * 생일 쿠폰을 받을 회원에 대한 정보를 담은 DTO 입니다.
 *
 * @author : 이가은
 * @version : 2024. 02. 25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BirthMemberDto {
    private Long memberId;
    private Date memberBirth;
}
