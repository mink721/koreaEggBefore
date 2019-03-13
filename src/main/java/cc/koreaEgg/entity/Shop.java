package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

/**
 * Created by mink721 on 2019-03-12.
 */

@Getter
@Setter
@ToString
@Alias("shop")
public class Shop {

    private Long id;
    private String name;
    private String postNum;
    private String address;
    private String addressDetail;
    private String tel;
    /* TODO AN shop 이미지는 어떻게 저장함?*/
    private String profileImage;

    private double lon;
    private double lat;

    private Timestamp regDate;

    private String homepage;
}
