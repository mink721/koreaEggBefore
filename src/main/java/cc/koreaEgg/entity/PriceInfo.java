package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class PriceInfo {

    private String area;
    private int size1;
    private int size2;
    private int size3;
    private int size4;
    private int size5;
    private int size6;
    private Date updateDate;
    private String memo;

}
