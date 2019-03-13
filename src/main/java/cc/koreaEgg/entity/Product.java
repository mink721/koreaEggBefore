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
    private String origin;
    private String shopName;
    private Integer size;
    private Integer cost;
    private Integer consumer;
    private Integer store;
    private Integer retail;
    private Integer wholesale;
    private Integer partner;
    private Integer agent;
    private Timestamp regDate;
    private String contents;

    private String imagePath;




}
