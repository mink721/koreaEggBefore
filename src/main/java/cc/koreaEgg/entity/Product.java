package cc.koreaEgg.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@ToString
@Alias("product")
public class Product {

    private Long id;
    private Long shopId;
    private String name;
    private String subName;
    private Integer size;

    private String origin;
    private Integer cost;
    private Integer consumer; //소비자사
    private Integer store;
    private Integer retail;
    private Integer wholesale;
    private Integer partner;
    private Integer agent;
    private Integer price;

    private Timestamp regDate;

    private String contents;
    private String badge;

    /*TODO 최대 최저 배송지역 판매기한 있어야하는지?*/

    private String imagePath;

    private String shopName;

}
