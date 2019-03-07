package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/*
    난가예보
 */
@Getter
@Setter
@ToString
public class PriceCast {

    private Long id;
    private Timestamp regDate;
    private int cast;
    private boolean cast1;      // +2
    private boolean cast2;      // +1
    private boolean cast3;      // +0
    private boolean cast4;      // -1
    private boolean cast5;      // -2

}
