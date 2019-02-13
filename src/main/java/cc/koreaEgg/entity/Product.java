package cc.koreaEgg.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("product")
public class Product {

    private Long id;
    private String name;
    private Price price;
    private String origin;
    private String shopName;
    private String shopId;
    private String imgS;
    private String content;



}
