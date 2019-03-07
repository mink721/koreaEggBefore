package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Alias("price")
public class Price {

    private Long id;
    private Long productId;
    private int cost;
    private int consumer;
    private int store;
    private int retail;
    private int wholesale;
    private int partner;
    private int agent;
    private Timestamp regDate;


}
