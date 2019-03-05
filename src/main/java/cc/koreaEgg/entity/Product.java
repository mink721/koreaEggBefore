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
    private String userId;
    private Long priceId;
    private String name;
    private String origin;
    private String shopName;
    private String content;

    private String imagePath;
    private Price price;



}
