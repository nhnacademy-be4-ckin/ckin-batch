package store.ckin.batch.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

/**
 * BirthMemberDto
 *
 * @author : 이가은
 * @version : 2024. 02. 25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BirthMemberDto {

    private Long member_id;
    private Date member_birth;


}
