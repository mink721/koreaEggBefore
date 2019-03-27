package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> selectProductList(@Param("cri") Criteria cri, @Param("role") Role role, @Param("shopId") Long shopId, @Param("size") Integer size);
    Product selectProduct(@Param("id") long id, @Param("role") Role role);
    long createProduct(Product product);
    long createPrice(Price price);

    void updateProduct(Product product);
    void updateProductPrice(Price price);

    void deleteProduct(long id);
}
