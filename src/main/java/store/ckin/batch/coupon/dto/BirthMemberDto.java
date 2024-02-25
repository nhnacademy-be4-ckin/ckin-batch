package store.ckin.batch.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

/**
 * BirthMemberDto
 *
 * @author : gaeun
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
