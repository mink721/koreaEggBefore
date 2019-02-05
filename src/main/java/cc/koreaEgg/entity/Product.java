package cc.koreaEgg.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("product")
public class Product {

    private Long id;
    private String name;
    private Price price;


}
