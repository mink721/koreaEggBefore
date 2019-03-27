package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by mink721 on 2019-03-12.
 */

@Getter
@Setter
@ToString
@Alias("shop")
public class Shop {

    private Long id;
    @NotBlank
    private String name;
    private String postNum;
    @NotBlank
    private String addr;
    private String addrDetail;
    private String tel;
    /* TODO AN shop 이미지는 어떻게 저장함?*/
    private String profileImage;

    private double lon;
    private double lat;

    private Timestamp regDate;

    private String homepage;

    private List<Product> products;
}
