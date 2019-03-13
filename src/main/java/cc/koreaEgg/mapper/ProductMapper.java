package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> selectProductList(@Param("cri") Criteria cri, @Param("shopId") Long shopId, @Param("size") Integer size);
    Product selectProduct(@Param("id") long id);
    long createProduct(Product product);
    long createPrice(Price price);

    void createShop(Shop shop);
    List<Shop> selectShopList(@Param("cri") Criteria cri,
                              @Param("lon") double lon,
                              @Param("lat") double lat,
                              @Param("role") String role);
    User selectUserById(long id);
}
