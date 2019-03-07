package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
@Getter
@Setter
@ToString
@Alias("priceInfo")
public class PriceInfo {

    private Long id;
    private Integer areaId; //서울,대전,영주,부산,대구,광주,전주,충북
    private String areaName;
    private int size1;
    private int size2;
    private int size3;
    private int size4;
    private int size5;
    private int size6;
    private Timestamp regDate;
    private String memo;
    private int diff1;
    private int diff2;
    private int diff3;
    private int diff4;
    private int diff5;
    private int diff6;
}
