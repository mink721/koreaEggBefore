package cc.koreaEgg.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Getter
@Setter
@ToString
@Alias("price")
public class Price {

    private Long productId;
    private int cost;
    private int consumer;
    private int store;
    private int retail;
    private int wholeSale;
    private int partner;
    private int agent;
    private Date updateDate;


}
