package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/**
 * Created by mink721 on 2019-03-22.
 */
@Getter
@Setter
@ToString
public class AuthNum {

    private Integer id;
    @Pattern(regexp = "^01\\d-?\\d{3,4}-?\\d{4}")
    private String phone;
    private String authNum;
    private Timestamp regDate;
}
